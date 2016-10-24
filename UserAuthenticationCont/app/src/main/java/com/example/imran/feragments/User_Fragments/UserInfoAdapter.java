package com.example.imran.feragments.User_Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.imran.feragments.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad imran on 10/20/2016.
 */

public class UserInfoAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<UserInfoModules> modulesArrayList;
    private TextView user_name, User_email;

    public UserInfoAdapter(Context context, ArrayList<UserInfoModules> modulesArrayList) {
        this.context = context;
        this.modulesArrayList = modulesArrayList;
    }

    @Override
    public int getCount() {
        return modulesArrayList.size();
    }

    @Override
    public UserInfoModules getItem(int position) {
        return modulesArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view1 = LayoutInflater.from(context).inflate(R.layout.user_info_layout, null);
        user_name = (TextView) view1.findViewById(R.id.User_Info_name);
        User_email = (TextView) view1.findViewById(R.id.User_info_email);


       UserInfoModules us = modulesArrayList.get(position);


        user_name.setText("User name : " + us.getFirstname());
        //user_name.setText();
        User_email.setText("User Email : " + us.getUserEmail());
        return view1;
    }
}
