package com.thalkz.everest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Pager extends FragmentPagerAdapter {

    final int PAGE_COUNT = 4;

    public Fragment_Pager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                return new SearchFragment();
            case 1:
                return new JournalFragment();
            case 2:
                return new RankingFragment();
            case 3:
                return new UserProfilFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}