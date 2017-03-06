package com.example.mitosis.salemanager;

/**
 * Created by mitosis on 18/2/17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
/**
 * Created by akhil on 12/1/2016.
 */
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
                FragmentB com = new FragmentB();
                return com.newInstance(tabName.get(position));

            case 1:
                Completed tab2 = new Completed();
                return tab2;
            case 2:
                Pending tab3 = new Pending();
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