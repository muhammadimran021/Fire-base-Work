package com.example.imran.feragments.User_todo_pkg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.imran.feragments.R;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

public class ImageViewer extends AppCompatActivity {
    ImageView imageView;
    Modules userInfo = new Modules();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        imageView = (ImageView) findViewById(R.id.ImageViewer);
        Log.d("TAG", "IamgeUrl: " + userInfo.getImage());
        Picasso.with(ImageViewer.this).load(userInfo.getImage()).into(imageView);

    }
}
