package com.mitosis.fieldtracking.regionalmanager;

/**
 * Created by mitosis on 18/2/17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class RMSectionsPagerAdapter extends FragmentPagerAdapter {

    int mNumOfTabs;
    ArrayList<String> tabName;

    public RMSectionsPagerAdapter(FragmentManager fm, int NumOfTabs, ArrayList<String> tabName) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.tabName=tabName;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                RMTotalLeadFragment tab1 = new RMTotalLeadFragment();
                return tab1;

            case 1:
                RMCompletedLeadFragment tab2 = new RMCompletedLeadFragment();
                return tab2;
            case 2:
                RMPendingLeadFragment tab3 = new RMPendingLeadFragment();
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