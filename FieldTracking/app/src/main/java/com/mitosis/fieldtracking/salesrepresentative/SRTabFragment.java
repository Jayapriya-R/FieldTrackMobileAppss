package com.mitosis.fieldtracking.salesrepresentative;

/**
 * Created by mitosis on 18/2/17.
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mitosis.fieldtracking.R;

import java.util.ArrayList;

public class SRTabFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private SRSectionsPagerAdapter mSectionsPagerAdapter;
    ArrayList<String> tabName;

    public SRTabFragment() {

    }

    public static SRTabFragment newInstance(String navigation) {
        SRTabFragment fragment = new SRTabFragment();
        Bundle args = new Bundle();
        args.putString(SRConstants.FRAG_A, navigation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.srfragment_a, container, false);


        tabLayout = (TabLayout)view.findViewById(R.id.tabs);
        mViewPager = (ViewPager)view.findViewById(R.id.container);
        tabName=new ArrayList<String>();

        tabLayout.addTab(tabLayout.newTab().setText("Today"));
        tabLayout.addTab(tabLayout.newTab().setText("Completed"));
        tabLayout.addTab(tabLayout.newTab().setText("Pending"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        for(int i=0;i<3;i++){
            tabName.add(getArguments().getString(SRConstants.FRAG_A)+" "+String.valueOf(i));

        }
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        mSectionsPagerAdapter = new SRSectionsPagerAdapter(getChildFragmentManager(),tabLayout.getTabCount(),tabName);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                getChildFragmentManager().beginTransaction().addToBackStack(null).commit();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSectionsPagerAdapter = new SRSectionsPagerAdapter(getChildFragmentManager(),tabLayout.getTabCount(),tabName);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }
}