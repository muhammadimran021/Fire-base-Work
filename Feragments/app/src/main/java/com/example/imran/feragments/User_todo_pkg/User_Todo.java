package com.example.imran.feragments.User_todo_pkg;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.imran.feragments.MainActivity;
import com.example.imran.feragments.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

public class User_Todo extends AppCompatActivity {
    private EditText Dname, Demail;
    private CheckBox Disch;
    private FloatingActionButton fab1;
    ArrayList<Modules> modulesArrayList = new ArrayList<>();
    Todo_Adapter adapter;
    ListView listview;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    TextView UserName;
    Modules modules;
    private FloatingActionButton fab;
    private ImageButton image;
    public static final int REQUEST = 1;
    private Uri mImageUri1 = null;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__todo);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseStorage.getInstance().getReference();

        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        listview = (ListView) findViewById(R.id.Listview);
        adapter = new Todo_Adapter(modulesArrayList, this);
        listview.setAdapter(adapter);

        UserName = (TextView) findViewById(R.id.Username);
        UserName.setText(mAuth.getCurrentUser().getEmail());


        mDatabase.child("User Todo").child(mAuth.getCurrentUser().getUid().toString()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Modules moduless = dataSnapshot.getValue(Modules.class);
                modulesArrayList.add(moduless);
                adapter.notifyDataSetChanged();

                Log.d("TAG", "Values " + dataSnapshot.getValue());
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

//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                intent.setType("image/*");
//                startActivityForResult(intent, REQUEST);
//
//
//            }
//        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(User_Todo.this);
                builder.setTitle("Add some ToDo!");

                LayoutInflater inflater = User_Todo.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.inflate_layout, null);
                image = (ImageButton) findViewById(R.id.imageupload);
                Dname = (EditText) view.findViewById(R.id.Dialog_UserText);
                Demail = (EditText) view.findViewById(R.id.Dialog_EmailText);
                Disch = (CheckBox) view.findViewById(R.id.Dialogcheck);

                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String Name = Dname.getText().toString();
                        String Email = Demail.getText().toString();
                        boolean isR = Disch.isChecked();

                        modules = new Modules(mDatabase.child("User Todo").push().getKey().toString(), Name, Email, isR);
                        // modulesArrayList.add(modules);
                        mDatabase.child("User Todo").child(mAuth.getCurrentUser().getUid().toString()).child(modules.getUid()).setValue(modules);
                        // firebaseStorage.getReference().


                        Log.d("TAG", "Values  " + Name + " " + Email + " " + isR);

                    }
                });

                builder.setView(view);
                builder.setNegativeButton("No", null);
                builder.create().show();
                adapter.notifyDataSetChanged();

            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(User_Todo.this);
                builder.setTitle("Do u wan to delete ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabase.child("User Todo").child(mAuth.getCurrentUser().getUid().toString())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        int i = 0;
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                                            if (i == position) {
                                                DatabaseReference fer = data.getRef();
                                                fer.removeValue();
                                                modulesArrayList.remove(position);
                                                adapter.notifyDataSetChanged();
                                            }
                                            i++;
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                    }


                });
                builder.setNegativeButton("no", null);
                builder.create().show();
            }
        });
        SignOut();
    }

    public void SignOut() {
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(getApplication(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void ImageLoader() {

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST && resultCode == RESULT_OK) {
//
//            Uri ImageUri1 = data.getData();
//
//            CropImage.activity(ImageUri1)
//                    .setGuidelines(CropImageView.Guidelines.ON)
//                    .setAspectRatio(1, 1)
//                    .start(this);
//
//        }
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//
//                mImageUri1 = result.getUri();
//
//                image.setImageURI(mImageUri1);
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
//    }
}
