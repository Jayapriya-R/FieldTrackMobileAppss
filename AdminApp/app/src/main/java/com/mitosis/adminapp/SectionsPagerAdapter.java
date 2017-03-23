package com.mitosis.adminapp;

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
                EmployeeListActivity com = new EmployeeListActivity();
                return com.newInstance(tabName.get(position));

            case 1:
                EmployeeListActivity tab2 = new EmployeeListActivity();
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