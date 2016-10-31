package com.example.imran.feragments.User_SignIn_Up_fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.imran.feragments.R;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp extends Fragment {
    private EditText fname, lname, email, password;
    private Button signup;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private HashMap<String, String> hashMap = new HashMap<>();
    private CircularImageView sighupImage;
    private int REQUEST = 1;
    private Uri ImageUri;
    private StorageReference firebaseStorage;
    private String signup_url;
    //    private ImageView Signimage;
    private ProgressDialog pd;


    public SignUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance().getReference();
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        fname = (EditText) view.findViewById(R.id.fname_text);
        lname = (EditText) view.findViewById(R.id.lname_text);
        email = (EditText) view.findViewById(R.id.email_text);
        password = (EditText) view.findViewById(R.id.password_text);
        signup = (Button) view.findViewById(R.id.SignUp);
        sighupImage = (CircularImageView) view.findViewById(R.id.signupimage);
        //Picasso.with(getContext()).load(ImageUri).into(sighupImage);
        sighupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST);
            }
        });


//                Intent intent = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                intent.setType("image/*");
//                startActivityForResult(intent, REQUEST);
        // Signimage = (ImageView) view.findViewById(R.id.SignUpImage);
//        Signimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                intent.setType("image/*");
//                startActivityForResult(intent, REQUEST);
//
//            }
//        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String userfname = fname.getText().toString();
                final String userlname = lname.getText().toString();
                final String user_email = email.getText().toString();
                final String user_password = password.getText().toString();

//                if (TextUtils.isEmpty(userfname)) {
//                    fname.setError("Required");
//
//                } else if (TextUtils.isEmpty(userlname)) {
//                    lname.setError("Required");
//
//                } else if (TextUtils.isEmpty(user_email)) {
//                    email.setError("Required");
//
//                } else if (TextUtils.isEmpty(user_password)) {
//                    password.setError("Required");
//
//                } else {
//                    pd = new ProgressDialog(getContext());
//                    pd.setMessage("Loging in");
//                    pd.show();

                StorageReference reference = firebaseStorage.child("SignUp-images").child(ImageUri.getLastPathSegment().toString());
                reference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                        signup_url = String.valueOf(taskSnapshot.getDownloadUrl());
                    }
                });
                mAuth.createUserWithEmailAndPassword(user_email, user_password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    String current_key = task.getResult().getUser().getUid().toString();

                                    hashMap.put("UUID", current_key);
                                    hashMap.put("firstname", userfname);
                                    hashMap.put("lastname", userlname);
                                    hashMap.put("userEmail", user_email);
                                    hashMap.put("userPassword", user_password);
                                    hashMap.put("userImage", signup_url);

                                    mDatabase.child("User-info").child(current_key).setValue(hashMap);
                                    Toast.makeText(getContext(), "User Created", Toast.LENGTH_SHORT).show();

                                    //if task is successfull then empty edit text!
                                    fname.setText("");
                                    lname.setText("");
                                    email.setText("");
                                    password.setText("");
//                                        pd.dismiss();
                                } else if (!task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Sorry SignUp failed!", Toast.LENGTH_SHORT).show();
                                }

                            }

                        });
                // }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            ImageUri = data.getData();
            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(getActivity());
            sighupImage.setImageURI(ImageUri);
            Log.d("TAG", "Image: " + ImageUri);
            //
        }
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//
//                ImageUri = result.getUri();
//                //sighupImage.setImageURI(ImageUri);
//
//                Log.d("RAF","ad"+ImageUri);
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }

        // }
    }
}
