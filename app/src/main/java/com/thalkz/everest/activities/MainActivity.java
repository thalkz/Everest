package com.thalkz.everest.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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
import com.thalkz.everest.R;
import com.thalkz.everest.adapters.Fragment_Pager;
import com.thalkz.everest.adapters.JournalAdapter;
import com.thalkz.everest.adapters.RankingAdapter;
import com.thalkz.everest.lists.PlayerList;
import com.thalkz.everest.objects.Event;
import com.thalkz.everest.objects.Player;

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
    public static RankingAdapter searchAdapter;
    public static FloatingActionButton fab;
    public static MobileServiceSyncTable<Event> eTable;
    public Event[] eList;
    public Context context;
    public String userName;
    private MobileServiceClient client;
    private Player[] pList;
    private MobileServiceSyncTable<Player> pTable;
    private Query ePullQuery;
    private Query pPullQuery;

    public static void insertEvent(Event e) {
        try {

            eTable.insert(e).get();
        } catch (InterruptedException e1) {
            Log.v("Interr", e1.getMessage());
        } catch (ExecutionException e2) {
            Log.v("Exec", e2.getMessage());
        }


    }

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
        searchAdapter = new RankingAdapter(new Player[0], this);

        /** Creating a fab */
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent matchIntent = new Intent(context, MatchActivity.class);
                startActivity(matchIntent);
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
            ePullQuery = client.getTable(Event.class).where().orderBy("__createdAt", QueryOrder.Descending);


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

            /** Setting the PageViewer */
            ViewPager pager = (ViewPager) findViewById(R.id.pager);
            FragmentManager fm = getSupportFragmentManager();
            Fragment_Pager pagerAdapter = new Fragment_Pager(fm);
            pager.setAdapter(pagerAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
            tabLayout.setupWithViewPager(pager);
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_profil);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_whatshot);
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_ranking);
            tabLayout.getTabAt(3).setIcon(R.drawable.ic_search);

            pager.setCurrentItem(1);

            //load userName
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            userName = sharedPref.getString("sharedName", "");

            if (userName.equals("")) {
                createAccount();
            }


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
    protected void onResume() {
        super.onResume();

        eRefreshLocalTable();
        pRefreshLocalTable();

        pSyncAsync();
        eSyncAsync();


        eRefreshTable();
        pRefreshTable();

        Log.v("onResume", "Refresh all tables");
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

                                PlayerList.remplace(pList);
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

            PlayerList.remplace(pList);
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
        eTableDefinition.put("eCaps1", ColumnDataType.Integer);
        eTableDefinition.put("eCaps2", ColumnDataType.Integer);
        eTableDefinition.put("eRev1", ColumnDataType.Integer);
        eTableDefinition.put("eRev2", ColumnDataType.Integer);
        eTableDefinition.put("eBel1", ColumnDataType.Integer);
        eTableDefinition.put("eBel2", ColumnDataType.Integer);

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

    public void createAccount() {

        final View aView = getLayoutInflater().inflate(R.layout.create_account_dialog, null);
        final EditText aName = (EditText) aView.findViewById(R.id.new_name);

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("S'inscrire")
                .setView(aView)
                .setPositiveButton("Ça Part", null)
                .setNegativeButton("Non merci", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nSurnom = aName.getText().toString();

                if (nSurnom.length() < 3) {
                    aName.setError("trop court");
                } else if (nSurnom.length() > 10) {
                    aName.setError("10 lettres max");
                } else if (isNameUsed(nSurnom)) {
                    aName.setError("Déjà utilisé");
                } else if (!isAlpha(nSurnom)) {
                    aName.setError("Lettres uniquement (sans accents)");
                } else {

                    Player newPlayer = new Player(nSurnom, "U0", "0A");
                    try {
                        pTable.insert(newPlayer).get();
                    } catch (InterruptedException e) {
                        Log.v("pTable insert Interr", e.getMessage());
                    } catch (ExecutionException e) {
                        Log.v("pTable insert Exec", e.getMessage());
                    }

                    pRefreshTable();
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
                    sharedPref.edit().putString("sharedName", nSurnom).apply();

                }
            }
        });

    }

    public boolean isNameUsed(String newName) {
        for (Player p : pList) {
            if (p.getName().equals(newName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }


}
