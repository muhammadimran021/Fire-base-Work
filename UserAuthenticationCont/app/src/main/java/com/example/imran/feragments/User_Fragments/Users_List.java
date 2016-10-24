package com.example.imran.feragments.User_Fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.imran.feragments.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class Users_List extends Fragment {
    ListView Userlist;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    UserInfoAdapter adapter;
    ArrayList<UserInfoModules> userArraylist;
    private HashMap<String, String> hashMap = new HashMap<>();
    UserInfoModules usersss;

    public Users_List() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users__list, container, false);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

//        final UserInfoModules users = new UserInfoModules();

        Userlist = (ListView) view.findViewById(R.id.ListUsers);
        userArraylist = new ArrayList<>();
        adapter = new UserInfoAdapter(getActivity(), userArraylist);
        Userlist.setAdapter(adapter);


        mDatabase.child("User-info").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("TAg", "asdfasdf : " + dataSnapshot.getValue());

                usersss = dataSnapshot.getValue(UserInfoModules.class);
                Log.d("valueObject", usersss.getFirstname() + "");
                userArraylist.add(new UserInfoModules(usersss.getFirstname(), usersss.getLastname(), usersss.getUserEmail(), usersss.getUserPassword()));
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Send notification to postion # " + position);
                final View view1 = LayoutInflater.from(getContext()).inflate(R.layout.notifcation_layout, null);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = (EditText) view1.findViewById(R.id.notification_text);
                        String getTexts = editText.getText().toString();
                        hashMap.put("Message", getTexts);
                        hashMap.put("PushId", mDatabase.push().getKey());
                        hashMap.put("CurrentId", mAuth.getCurrentUser().getUid());
                        mDatabase.child("User-notificaton").child(mAuth.getCurrentUser().getUid()).child(FirebaseInstanceId.getInstance().toString()).setValue(hashMap);
                    }
                });
                builder.setView(view1);
                builder.create().show();

            }
        });


        return view;
    }


}
