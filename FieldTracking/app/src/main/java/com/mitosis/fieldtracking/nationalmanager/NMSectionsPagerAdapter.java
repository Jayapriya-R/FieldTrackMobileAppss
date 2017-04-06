package com.mitosis.fieldtracking.nationalmanager;

/**
 * Created by mitosis on 18/2/17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class NMSectionsPagerAdapter extends FragmentPagerAdapter {

    int mNumOfTabs;
    ArrayList<String> tabName;

    public NMSectionsPagerAdapter(FragmentManager fm, int NumOfTabs, ArrayList<String> tabName) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.tabName=tabName;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                NMEmployeeListActivity com = new NMEmployeeListActivity();
                return com.newInstance(tabName.get(position));

            case 1:
                NMEmployeeListActivity tab2 = new NMEmployeeListActivity();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}