package com.thalkz.everest.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thalkz.everest.fragments.JournalFragment;
import com.thalkz.everest.fragments.RankingFragment;
import com.thalkz.everest.fragments.SearchFragment;
import com.thalkz.everest.fragments.UserProfilFragment;

public class Fragment_Pager extends FragmentPagerAdapter {

    final int PAGE_COUNT = 4;

    public Fragment_Pager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                return new UserProfilFragment();
            case 1:
                return new JournalFragment();
            case 2:
                return new RankingFragment();
            case 3:
                return new SearchFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}