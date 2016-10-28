package com.example.imran.feragments.User_todo_pkg;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imran.feragments.MainActivity;
import com.example.imran.feragments.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class User_Todo extends Fragment {
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
    private CircularImageView image;
    public static final int REQUEST = 1;
    private Uri mImageUri1 = null;
    private StorageReference firebaseStorage;
    private TextView imageupload;
    private String url;
    private ImageView imagesss;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_user__todo, null);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance().getReference();

        fab1 = (FloatingActionButton) view.findViewById(R.id.fab1);
        listview = (ListView) view.findViewById(R.id.Listview);
        adapter = new Todo_Adapter(modulesArrayList, getContext());
        listview.setAdapter(adapter);


        UserName = (TextView) view.findViewById(R.id.Username);
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
                adapter.notifyDataSetChanged();
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


        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                builder.setNegativeButton("View Image", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                        builder1.setTitle("Image");
//                        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.userimageview, null);
                        LayoutInflater inflater = getActivity().getLayoutInflater();
                        View view1 = getActivity().getLayoutInflater().inflate(R.layout.userimageview, null);
                        ImageView im = (ImageView) view1.findViewById(R.id.userimage_view);
                        Modules modules1 = modulesArrayList.get(position);
                        Log.d("TAG", "name: " + modules1.getName());
                        Log.d("TAG", "image: " + modules1.getImage());
                        Picasso.with(getContext()).load(modules1.getImage()).into(im);
                        builder.setPositiveButton("Ok", null);
                        builder1.create().show();
                        builder1.setView(view1);
//                        Intent i = new Intent(getActivity(), ImageViewer.class);
//                        startActivity(i);
                    }
                });

                builder.create().show();
                builder.setView(view);
            }
        });

        clickonfab();
        SignOut();
        return view;
    }

    public void clickonfab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add some ToDo!");

                LayoutInflater inflater = User_Todo.this.getLayoutInflater(getArguments());
                View view = inflater.inflate(R.layout.inflate_layout, null);

                image = (CircularImageView) view.findViewById(R.id.profile_image);

                Dname = (EditText) view.findViewById(R.id.Dialog_UserText);
                Demail = (EditText) view.findViewById(R.id.Dialog_EmailText);
                Disch = (CheckBox) view.findViewById(R.id.Dialogcheck);
                imageupload = (TextView) view.findViewById(R.id.Imageupload);
                imageupload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(intent, REQUEST);


                    }
                });


                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String Name = Dname.getText().toString();
                        final String Email = Demail.getText().toString();
                        final boolean isR = Disch.isChecked();


                        // modulesArrayList.add(modules);
                        // firebaseStorage.getReference().
                        StorageReference storage = firebaseStorage.child("Images").child(mImageUri1.getLastPathSegment().toString());
                        storage.putFile(mImageUri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                url = taskSnapshot.getDownloadUrl().toString();
                                modules = new Modules(url, mDatabase.child("User Todo").push().getKey().toString(), Name, Email, isR);
                                mDatabase.child("User Todo").child(mAuth.getCurrentUser().getUid().toString()).child(modules.getUid()).setValue(modules);
                                Toast.makeText(getActivity(), "Image upload Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });


                        Log.d("TAG", "Values  " + Name + " " + Email + " " + isR);

                    }
                });

                builder.setView(view);
                builder.setNegativeButton("No", null);
                builder.create().show();
                adapter.notifyDataSetChanged();

            }
        });
    }


    public void SignOut() {
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            mImageUri1 = data.getData();
            Log.d("TAG", "croppath: " + mImageUri1);
            CropImage.activity(mImageUri1)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(getActivity());
            image.setImageURI(mImageUri1);
        }
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            Log.d("TAG", "Path: " + result.getUri());
//            if (resultCode == RESULT_OK) {
//                ImageUri1 = result.getUri();
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }

        //}

    }
}