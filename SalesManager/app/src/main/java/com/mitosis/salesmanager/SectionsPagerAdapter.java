package com.mitosis.salesmanager;

/**
 * Created by mitosis on 18/2/17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    int mNumOfTabs;
    ArrayList<String> tabName;

    public SectionsPagerAdapter(FragmentManager fm, int NumOfTabs, ArrayList<String> tabName) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.tabName=tabName;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TotalLeadFragment tab1 = new TotalLeadFragment();
                return tab1;

            case 1:
                CompletedLeadFragment tab2 = new CompletedLeadFragment();
                return tab2;
            case 2:
                PendingLeadFragment tab3 = new PendingLeadFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}