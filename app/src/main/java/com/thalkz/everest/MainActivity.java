package com.thalkz.everest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.query.Query;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncTable;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public static int INITIAL_POINTS = 1000;
    public static JournalAdapter journalAdapter;
    public static RankingAdapter rankingAdapter;
    Context context;
    private MobileServiceClient client;
    private Event[] eList;
    private Player[] pList;
    private MobileServiceSyncTable<Event> eTable;
    private MobileServiceSyncTable<Player> pTable;
    private Query ePullQuery;
    private Query pPullQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        /** Setting the action bar */
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /** Initilize rankingAdapter and journalAdapter */
        journalAdapter = new JournalAdapter(new Event[0], this);
        rankingAdapter = new RankingAdapter(new Player[0], this);

        /** Setting the PageViewer */
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        Fragment_Pager pagerAdapter = new Fragment_Pager(fm);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(pager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_profil);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_journal);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_ranking);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_search);

        pager.setCurrentItem(1);

        /** Creating a fab */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ouvre GameActivity", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        /** Connecting to DataBase */
        try {
            client = new MobileServiceClient(
                    "https://elocaps.azure-mobile.net",
                    "jlgUVfjzqDcVBUfeqJYIlBBnhJfTdP97",
                    context);

            /** Pull Queries */
            pPullQuery = client.getTable(Player.class).where().orderBy("pPoints", QueryOrder.Descending);
            ePullQuery = client.getTable(Event.class).where().orderBy("__createdAt", QueryOrder.Ascending);


            /** initializing Local Stores */
            setLocalStore();

            /** setting syncTables */
            eTable = client.getSyncTable(Event.class);
            pTable = client.getSyncTable(Player.class);

            /** getting Tables from local store (fast) */
            pRefreshLocalTable();
            eRefreshLocalTable();

            /** syncing local and cloud if network is available */
            pSyncAsync();
            eSyncAsync();


            /** getting Tables from cloud (slow) */
            pRefreshTable();
            eRefreshTable();


        } catch (MalformedURLException e) {
            Log.w("Connection to Client", e.toString());
        } catch (Exception e) {
            Throwable t = e;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            Log.w("onCreate: ", t.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            try {
                Event testEvent = new Event(1, "Shouby (+9) fait frotter Kaboo (-2)", 2015, 12, 29, 4, 03, 00, 0, "Fortune");
                eTable.insert(testEvent).get();

                Player testPlayer = new Player("Kaboo","U0","2A");
                pTable.insert(testPlayer).get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void eRefreshTable() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<Event> result = eTable.read(ePullQuery).get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            List<Event> arrayList = new ArrayList<>();

                            for (Event e : result) {
                                arrayList.add(e);
                            }

                            try {
                                eList = new Event[arrayList.size()];
                                arrayList.toArray(eList);

                                journalAdapter.updateData(eList);

                            } catch (Exception e) {
                                Log.e("Error", e.toString());
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.e("eRefreshTable", e.toString());
                }
                return null;
            }
        }.execute();
    }

    public void pRefreshTable() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<Player> result = pTable.read(pPullQuery).get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            List<Player> arrayList = new ArrayList<>();

                            for (Player p : result) {
                                arrayList.add(p);
                            }

                            try {
                                pList = new Player[arrayList.size()];
                                arrayList.toArray(pList);

                                rankingAdapter.updateData(pList);

                            } catch (Exception e) {
                                Log.e("Error", e.toString());
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.e("pRefreshTable", e.toString());
                }
                return null;
            }
        }.execute();
    }

    public void eRefreshLocalTable() {

        try {
            final MobileServiceList<Event> result = eTable.read(ePullQuery).get();
            List<Event> arrayList = new ArrayList<>();

            for (Event e : result) {
                arrayList.add(e);
            }

            eList = new Event[arrayList.size()];
            arrayList.toArray(eList);

            journalAdapter.updateData(eList);

        } catch (Exception e) {
            Log.e("eRefreshLocalTable", e.getMessage());
        }

    }

    public void pRefreshLocalTable() {

        try {
            final MobileServiceList<Player> result = pTable.read(pPullQuery).get();
            List<Player> arrayList = new ArrayList<>();

            for (Player p : result) {
                arrayList.add(p);
            }

            pList = new Player[arrayList.size()];
            arrayList.toArray(pList);

            rankingAdapter.updateData(pList);

        } catch (Exception e) {
            Log.e("pRefreshLocalTable", e.toString());
        }

    }

    public void setLocalStore() {
        //Set up the local Store
        SQLiteLocalStore localStore = new SQLiteLocalStore(client.getContext(), "LocalStore", null, 1);
        SimpleSyncHandler handler = new SimpleSyncHandler();
        MobileServiceSyncContext syncContext = client.getSyncContext();

        //Set up the table definition of the local store
        Map<String, ColumnDataType> eTableDefinition = new HashMap<>();
        eTableDefinition.put("Id", ColumnDataType.String);
        eTableDefinition.put("eType", ColumnDataType.Integer);
        eTableDefinition.put("eMessage", ColumnDataType.String);
        eTableDefinition.put("ePlayer1", ColumnDataType.String);
        eTableDefinition.put("ePlayer2", ColumnDataType.String);
        eTableDefinition.put("eGain1", ColumnDataType.Integer);
        eTableDefinition.put("eGain2", ColumnDataType.Integer);
        eTableDefinition.put("eYear", ColumnDataType.Integer);
        eTableDefinition.put("eMonth", ColumnDataType.Integer);
        eTableDefinition.put("eDayOfMonth", ColumnDataType.Integer);
        eTableDefinition.put("eDayOfWeek", ColumnDataType.Integer);
        eTableDefinition.put("eHour", ColumnDataType.Integer);
        eTableDefinition.put("eMin", ColumnDataType.Integer);
        eTableDefinition.put("eSeason", ColumnDataType.Integer);
        eTableDefinition.put("ePoster", ColumnDataType.String);

        Map<String, ColumnDataType> pTableDefinition = new HashMap<>();
        pTableDefinition.put("Id", ColumnDataType.String);
        pTableDefinition.put("pName", ColumnDataType.String);
        pTableDefinition.put("pPoints", ColumnDataType.Integer);
        pTableDefinition.put("pVictories", ColumnDataType.Integer);
        pTableDefinition.put("pDefeats", ColumnDataType.Integer);
        pTableDefinition.put("pIndicator", ColumnDataType.Integer);
        pTableDefinition.put("pFloor", ColumnDataType.String);
        pTableDefinition.put("pPromo", ColumnDataType.String);

        //Initialize the local store
        try {
            localStore.defineTable("Event", eTableDefinition);
            localStore.defineTable("Player", pTableDefinition);

            syncContext.initialize(localStore, handler).get();
        } catch (MobileServiceLocalStoreException e) {
            Log.e("MSLocalStoreException", e.getMessage());
        } catch (InterruptedException e) {
            Log.e("InterrException", e.getMessage());
        } catch (ExecutionException e) {
            Log.e("ExecException", e.getMessage());
        }
    }

    /*public void pSetLocalStore() {
        //Set up the local Store
        SQLiteLocalStore localStore = new SQLiteLocalStore(client.getContext(), "Player", null, 1);
        SimpleSyncHandler handler = new SimpleSyncHandler();
        MobileServiceSyncContext syncContext = client.getSyncContext();

        //Set up the table definition of the local store
        Map<String, ColumnDataType> pTableDefinition = new HashMap<>();
        pTableDefinition.put("Id", ColumnDataType.String);
        pTableDefinition.put("pName", ColumnDataType.String);
        pTableDefinition.put("pPoints", ColumnDataType.Integer);
        pTableDefinition.put("pVictories", ColumnDataType.Integer);
        pTableDefinition.put("pDefeats", ColumnDataType.Integer);
        pTableDefinition.put("pIndicator", ColumnDataType.Integer);
        pTableDefinition.put("pFloor", ColumnDataType.String);
        pTableDefinition.put("pPromo", ColumnDataType.String);

        //Initialize the local store
        try {

            localStore.defineTable("Player", pTableDefinition);
            syncContext.initialize(pLocalStore, handler).get();

        } catch (MobileServiceLocalStoreException e) {
            Log.e("MSLocalStoreException", e.getMessage());
        } catch (InterruptedException e) {
            Log.e("InterrException", e.getMessage());
        } catch (ExecutionException e) {
            Log.e("ExecException", e.getMessage());
        }
    }*/

    public void eSyncAsync() {
        if (isNetworkAvailable()) {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        client.getSyncContext().push().get();
                        eTable.pull(ePullQuery).get();

                    } catch (Exception exception) {
                        Log.e("esyncAsync", exception.toString());
                    }
                    return null;
                }
            }.execute();
        }
    }

    public void pSyncAsync() {
        if (isNetworkAvailable()) {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        client.getSyncContext().push().get();
                        pTable.pull(pPullQuery).get();

                    } catch (Exception exception) {
                        Log.e("psyncAsync", exception.toString());
                    }
                    return null;
                }
            }.execute();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
