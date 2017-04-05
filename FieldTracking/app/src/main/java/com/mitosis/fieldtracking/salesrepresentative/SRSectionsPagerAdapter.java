package com.mitosis.fieldtracking.salesrepresentative;

/**
 * Created by mitosis on 18/2/17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SRSectionsPagerAdapter extends FragmentPagerAdapter {

    int mNumOfTabs;
    ArrayList<String> tabName;

    public SRSectionsPagerAdapter(FragmentManager fm, int NumOfTabs, ArrayList<String> tabName) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.tabName=tabName;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                SRTotalLeadFragment com = new SRTotalLeadFragment();
                return com.newInstance(tabName.get(position));

            case 1:
                SRCompletedLeadFragment tab2 = new SRCompletedLeadFragment();
                return tab2;
            case 2:
                SRPendingLeadFragment tab3 = new SRPendingLeadFragment();
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