package com.thalkz.everest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Fragment_Pager extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    Context context;
    JournalAdapter journalAdapter;

    public Fragment_Pager(FragmentManager fm, Context context, JournalAdapter journalAdapter) {
        super(fm);
        this.context = context;
        this.journalAdapter = journalAdapter;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new JournalFragment();
            case 1:
                return new RankingFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}