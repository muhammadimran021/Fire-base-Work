package com.example.imran.feragments.UsersChat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.imran.feragments.R;
import com.example.imran.feragments.User_Fragments.UserInfoModules;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Muhammad imran on 10/31/2016.
 */

public class ChatAdapter extends BaseAdapter {
    ArrayList<PushKey> chatModulesArrayList;
    Context context;
    UserInfoModules userInfoModules = new UserInfoModules();

    public ChatAdapter(ArrayList<PushKey> chatModulesArrayList, Context context) {
        this.chatModulesArrayList = chatModulesArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return chatModulesArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatModulesArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view1 = LayoutInflater.from(context).inflate(R.layout.chat_layout, null);
        CircularImageView circularImageView = (CircularImageView) view1.findViewById(R.id.UserchatImagess);
        TextView Username = (TextView) view1.findViewById(R.id.UserchatName);
        TextView UserMessage = (TextView) view1.findViewById(R.id.UserchatMessage);
//
        PushKey chatModules = chatModulesArrayList.get(position);
//
        Picasso.with(context).load(userInfoModules.getUserImage()).into(circularImageView);
        Log.d("TAG", "Image: " + userInfoModules.getUserImage());
        Username.setText("name: " + chatModules.getCurrentId());
        UserMessage.setText("Message: " + chatModules.getMessage());


        return view1;
    }
}
