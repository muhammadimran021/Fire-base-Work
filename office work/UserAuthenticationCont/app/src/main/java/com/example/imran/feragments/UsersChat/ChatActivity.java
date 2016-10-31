package com.example.imran.feragments.UsersChat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.imran.feragments.R;
import com.example.imran.feragments.Services.ServicesModules;
import com.example.imran.feragments.User_Fragments.UserInfoModules;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {
    Button sendButon;
    EditText text;
    DatabaseReference mDatabase;
    HashMap<String, String> hashMap = new HashMap<>();
    ArrayList<ChatModules> chatModuless = new ArrayList<>();
    ChatModules chatModules = new ChatModules();
    ArrayList<UserInfoModules> userIn = new ArrayList<>();
    UserInfoModules userInfoModules = new UserInfoModules();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sendButon = (Button) findViewById(R.id.SentButton);

        text = (EditText) findViewById(R.id.UserChatEditText);
        sendButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                int i = 0;
                int position = (intent.getIntExtra("currentId", i));

                userInfoModules = userIn.get(position);


                String Text = text.getText().toString();
                String uuid = mDatabase.push().getKey();

                ServicesModules pushKey = new ServicesModules(userInfoModules.getUUID(), Text, uuid);
                Log.d("TAG", "values: " + pushKey.getCurrentId() + " " + pushKey.getMessage() + " " + pushKey.getPushId());


                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("currentId", pushKey.getCurrentId());
                hashMap.put("message", pushKey.getMessage());
                hashMap.put("pushId", pushKey.getPushId());
                mDatabase.child("Conversation").child(pushKey.getCurrentId()).push().setValue(hashMap);
            }
        });

    }
}
