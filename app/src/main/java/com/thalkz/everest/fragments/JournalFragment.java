package com.thalkz.everest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thalkz.everest.R;
import com.thalkz.everest.activities.MainActivity;

/**
 * JournalFragment displays the journal.
 * All Events are published here.
 */

public class JournalFragment extends Fragment {

    public JournalFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journal, container, false);

        RecyclerView eRecyclerView = (RecyclerView) view.findViewById(R.id.event_rv);
        eRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eRecyclerView.setAdapter(MainActivity.journalAdapter);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        eRecyclerView.setItemAnimator(itemAnimator);

        return view;
    }
}
