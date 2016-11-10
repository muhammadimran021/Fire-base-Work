package com.example.imran.feragments;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.imran.feragments.UserInfoAndTodo.Frag_Adapter_for_User;
import com.example.imran.feragments.User_Fragments.Users_List;
import com.example.imran.feragments.User_todo_pkg.User_Todo;
//import com.example.imran.feragments.UsersChat.UserChat;

import java.util.ArrayList;

public class UserinfoAndTodo extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    Frag_Adapter_for_User adapter;
    ArrayList<Fragment> mmfragmentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo_and_todo);

        tabLayout = (TabLayout) findViewById(R.id.User_tablayout);
        viewPager = (ViewPager) findViewById(R.id.User_viewpager);
        mmfragmentArrayList = new ArrayList<>();

        mmfragmentArrayList.add(0, new Users_List());
        mmfragmentArrayList.add(1, new User_Todo());
       // mmfragmentArrayList.add(2, new UserChat());

        tabLayout.addTab(tabLayout.newTab().setText("User Info List"));
        tabLayout.addTab(tabLayout.newTab().setText("User Todo"));
        tabLayout.addTab(tabLayout.newTab().setText("Chat"));

        adapter = new Frag_Adapter_for_User(getSupportFragmentManager(), mmfragmentArrayList);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(0);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        new Users_List();
                        break;
                    case 1:
                        new User_Todo();
                        break;
                    case 2:
                       // new UserChat();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


}
