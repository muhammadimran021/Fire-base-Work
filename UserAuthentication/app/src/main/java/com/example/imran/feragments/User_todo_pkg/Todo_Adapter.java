package com.example.imran.feragments.User_todo_pkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.imran.feragments.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad imran on 10/10/2016.
 */

public class Todo_Adapter extends BaseAdapter {
    private List<Modules> modulesArrayList;
    private Context context;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    public Todo_Adapter(List<Modules> modulesArrayList, Context context) {
        this.modulesArrayList = modulesArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return modulesArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return modulesArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        View view = LayoutInflater.from(context).inflate(R.layout.list_inflator, null);

        TextView dialogname = (TextView) view.findViewById(R.id.User_text_View);
        TextView dialogemail = (TextView) view.findViewById(R.id.Email_text_view);
        final CheckBox dialogcheck = (CheckBox) view.findViewById(R.id.ListViewBox);

        final Modules modules = modulesArrayList.get(position);

        dialogname.setText("Name: " + modules.getName());
        dialogemail.setText("City: " + modules.getCity());
        dialogcheck.setChecked(modules.isCheck());


        dialogcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final String AuthId = mAuth.getCurrentUser().getUid().toString();
                mDatabase.child(AuthId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dialogcheck.isChecked()) {
                            mDatabase.child("User Todo").child(AuthId).child(modules.getUid()).child("check").setValue(true);
                        }else if (!dialogcheck.isChecked()){
                            mDatabase.child("User Todo").child(AuthId).child(modules.getUid()).child("check").setValue(false);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        return view;
    }


}
