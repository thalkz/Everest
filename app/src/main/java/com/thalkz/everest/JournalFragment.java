package com.thalkz.everest;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return inflater.inflate(R.layout.fragment_journal, container, false);
    }
}
