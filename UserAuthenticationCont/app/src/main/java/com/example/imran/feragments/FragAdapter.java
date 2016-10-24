package com.example.imran.feragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by imran on 10/7/2016.
 */

public class FragAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mFragmentArrayList;

    public FragAdapter(FragmentManager fm, ArrayList<Fragment> mFragmentArrayList) {
        super(fm);
        this.mFragmentArrayList = mFragmentArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String name = "";
        switch (position){
            case 0:
                name = "Sign In";
                break;
            case 1:
                name = "Sign Up";
                break;
        }
        return name;
    }
}
