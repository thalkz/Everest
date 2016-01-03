package com.thalkz.everest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thalkz.everest.R;

/**
 * JournalFragment displays the journal.
 * All Events are published here.
 */

public class SearchFragment extends Fragment {

    public SearchFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_profil, container, false);

        return view;
    }
}
