package com.example.imran.feragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp extends Fragment {
    private EditText fname, lname, email, password;
    private Button signup;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private HashMap<String, String> hashMap = new HashMap<>();
    private int REQUEST = 1;
    private ImageView Signimage;

    public SignUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        fname = (EditText) view.findViewById(R.id.fname_text);
        lname = (EditText) view.findViewById(R.id.lname_text);
        email = (EditText) view.findViewById(R.id.email_text);
        password = (EditText) view.findViewById(R.id.password_text);
        signup = (Button) view.findViewById(R.id.SignUp);
        Signimage = (ImageView) view.findViewById(R.id.SignUpImage);
        Signimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST);

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userfname = fname.getText().toString();
                String userlname = lname.getText().toString();
                final String user_email = email.getText().toString();
                final String user_password = password.getText().toString();

                hashMap.put("First name: ", userfname);
                hashMap.put("Last name: ", userlname);
                hashMap.put("User Email: ", user_email);
                hashMap.put("User Password: ", user_password);

                mAuth.createUserWithEmailAndPassword(user_email, user_password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    String current_key = task.getResult().getUser().getUid().toString();
                                    mDatabase.child("User").child(current_key).setValue(hashMap);
                                    Toast.makeText(getContext(), "User Created", Toast.LENGTH_SHORT).show();
                                } else if (!task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Sorry SignUp failed!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST && resultCode == 0) {
            Uri ImageUri = data.getData();
            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(getActivity());

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == 0) {

                Uri mImageUri = result.getUri();
                Signimage.setImageURI(mImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }
    }
}
