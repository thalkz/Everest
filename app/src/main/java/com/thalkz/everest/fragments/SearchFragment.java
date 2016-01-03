package com.thalkz.everest.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.thalkz.everest.R;
import com.thalkz.everest.activities.MainActivity;
import com.thalkz.everest.lists.PlayerList;
import com.thalkz.everest.objects.Player;

/**
 * SearchFragment lets the user search a profil
 */

public class SearchFragment extends Fragment {

    View view;
    boolean keyboardShown = false;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView pRecyclerView = (RecyclerView) view.findViewById(R.id.search_rv);
        pRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pRecyclerView.setAdapter(MainActivity.searchAdapter);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        pRecyclerView.setItemAnimator(itemAnimator);

        final EditText sv = (EditText) view.findViewById(R.id.search_view);

        sv.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String txt = sv.getText().toString();
                Player[] searchedList = PlayerList.search(txt);
                MainActivity.searchAdapter.updateData(searchedList);

            }

        });

        ImageView clear = (ImageView) view.findViewById(R.id.search_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv.setText("");
            }
        });

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                keyboardShown = true;
            }


            MainActivity.fab.setVisibility(View.INVISIBLE);

        } else {
            if (keyboardShown) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }

            MainActivity.fab.setVisibility(View.VISIBLE);
        }
    }

}
