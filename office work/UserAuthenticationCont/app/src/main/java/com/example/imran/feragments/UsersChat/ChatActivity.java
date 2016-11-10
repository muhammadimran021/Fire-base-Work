package com.example.imran.feragments.UsersChat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.imran.feragments.R;
import com.example.imran.feragments.User_Fragments.UserInfoModules;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.key;

public class ChatActivity extends AppCompatActivity {
    private Button sendButon;
    private EditText text;
    private DatabaseReference mDatabase;
    private HashMap<String, String> hashMap = new HashMap<>();
//    private ArrayList<PushKey> chatModuless = new ArrayList<>();
    ArrayList<UserInfoModules> userIn = new ArrayList<>();
    ArrayList<PushKey> UserMessages = new ArrayList<>();
    UserInfoModules userInfoModules = new UserInfoModules();
    ChatAdapter adapter;
    private ListView listView;
    String uuid, UserUID, userId;
    FirebaseAuth mAuth;
    private ChatModules chatModules;
    HashMap<String, String> hashMap1 = new HashMap<String, String>();
    HashMap<String, String> hashMap2 = new HashMap<String, String>();
    PushKey p = new PushKey();
    String Object;
    String FirndId;
    String ConversatonRef;
    private boolean isConversationOld = false;
    private FirebaseUser User;
    private String TExteget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        sendButon = (Button) findViewById(R.id.SentButton);

        listView = (ListView) findViewById(R.id.UserchatList1);
        adapter = new ChatAdapter(UserMessages,this);
        listView.setAdapter(adapter);



        text = (EditText) findViewById(R.id.UserChateditText1);
        User = mAuth.getInstance().getCurrentUser();
        UserUID = mAuth.getCurrentUser().getUid();
        uuid = getIntent().getStringExtra("currentId");
        checkConversationNewOROLD();


    }

    private void checkConversationNewOROLD() {
        mDatabase.child("User-conversation").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {


                    for (DataSnapshot Data : dataSnapshot.getChildren()) {
                        ChatModules chat = Data.getValue(ChatModules.class);
                        Log.d("TAG", "values: " + Data.getValue());
                        if (chat.getUserId().equals(uuid)) {
                            chatModules = chat;
                            isConversationOld = true;
                            ConversatonRef = chat.getConversationId();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    getConvoDataOrCreateNew();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getConvoDataOrCreateNew() {
        if (isConversationOld) {
            getConversationData();
        } else {
            creatNewConversation();
        }
    }


    private void creatNewConversation() {
        DatabaseReference conversatonRef = mDatabase.child("conversation").push();
        ConversatonRef = conversatonRef.getKey();

        final ChatModules chat = new ChatModules(ConversatonRef, uuid);
        mDatabase.child("User-conversation").child(UserUID).push().setValue(chat, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    chat.setUserId(uuid);
                    mDatabase.child("User-conversation").child(uuid).push().setValue(chat, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                getConversationData();
                            }
                        }
                    });
                }
            }
        });
    }

    private void getConversationData() {

        mDatabase.child("conversation").child(ConversatonRef).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserMessages.clear();
                for (DataSnapshot D : dataSnapshot.getChildren()) {
                    PushKey p = D.getValue(PushKey.class);
                    Log.d("DATA", "CHATMESSAGE: " + D.getValue());

                    UserMessages.add(new PushKey(p.getCurrentId(), p.getMessage(), p.getPushId()));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ButtonClick();
    }

    private void ButtonClick() {
        sendButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Push", "PUSH" + uuid);
                TExteget = text.getText().toString();
                String push = mDatabase.push().getKey();

                if (TExteget.length() > 1) {
                    PushKey push12 = new PushKey();
                    push12.setCurrentId(UserUID);
                    push12.setMessage(TExteget);
                    push12.setPushId(ConversatonRef);
                    mDatabase.child("conversation").child(ConversatonRef).push().setValue(push12);
                    text.setText("");
                }
            }
        });
    }

}



