package com.example.imran.feragments.UsersChat;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.imran.feragments.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserChat extends Fragment {

    private DatabaseReference database;
    private Button sendButton;
    private EditText ChatText;
    private FirebaseAuth mAuth;
    private ChatModules chatModules;
    private ListView userchat;
    private ChatAdapter chatAdapter;
    private ArrayList<ChatModules> modulesArrayList;
    private String Text;
    private String CurrentUser;
    HashMap<String, String> hashMap = new HashMap<>();
//    private

    public UserChat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank2, container, false);
        database = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        CurrentUser = mAuth.getCurrentUser().getUid().toString();
//
//        userchat = (ListView) view.findViewById(R.id.UserchatList);
//        modulesArrayList = new ArrayList<>();
//        chatAdapter = new ChatAdapter(modulesArrayList, getContext());
//        userchat.setAdapter(chatAdapter);
//
//
//        ChatText = (EditText) view.findViewById(R.id.UserChatEditText);
//        sendButton = (Button) view.findViewById(R.id.SentButton);
//        sendButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Text = ChatText.getText().toString();
//                String push = database.push().getKey();
////                pushModules pushModules = new pushModules(push);
//                ChatModules sdf = new ChatModules(mAuth.getCurrentUser().getUid(), Text, push);
//
//
//                hashMap.put("UUID", CurrentUser);
//                hashMap.put("Text", Text);
//                 hashMap.put("PushId", push);
//
//                database.child("User-Con").child(mAuth.getCurrentUser().getUid()).push().setValue(hashMap);
//
//
////                modulesArrayList.add(sdf);
////                chatAdapter.notifyDataSetChanged();
//            }
//        });

//
//        database.child("User-Chat").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                ChatModules chatModules = dataSnapshot.getValue(ChatModules.class);
//                Log.d("TAG", "UserValues: " + dataSnapshot.getValue());
//                modulesArrayList.add(new ChatModules(chatModules.getUUID(), chatModules.getText()));
//                chatAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
        return view;
    }

}
