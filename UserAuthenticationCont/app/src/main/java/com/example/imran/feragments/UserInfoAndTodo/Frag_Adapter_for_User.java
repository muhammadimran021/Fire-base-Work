package com.example.imran.feragments.UserInfoAndTodo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Muhammad imran on 10/20/2016.
 */

public class Frag_Adapter_for_User extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mFragmentArrayList;

    public Frag_Adapter_for_User(FragmentManager fs, ArrayList<Fragment> mFragmentArrayList) {
        super(fs);
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

}
