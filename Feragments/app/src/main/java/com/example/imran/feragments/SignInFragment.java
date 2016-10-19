package com.example.imran.feragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imran.feragments.User_todo_pkg.User_Todo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {
    private EditText useremail, userpassword;
    private Button sign_in;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

//        mAuth = new FirebaseAuth.AuthStateListener() {
//
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//            }
//        }
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (user != null) {
//            Intent i = new Intent(getActivity(), User_Todo.class);
//            startActivity(i);
//            getActivity().finish();
//        } else {
//
//        }


        useremail = (EditText) view.findViewById(R.id.Useremail_text);
        userpassword = (EditText) view.findViewById(R.id.Userpassword_text);

        sign_in = (Button) view.findViewById(R.id.User_SigninButton);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String user_Email = useremail.getText().toString();
                String user_Password = userpassword.getText().toString();


                mAuth.signInWithEmailAndPassword(user_Email, user_Password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());

                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getContext(), User_Todo.class);
                                    startActivity(i);
                                    getActivity().finish();
                                } else if (!task.isSuccessful()) {
                                    Log.w("TAG", "signInWithEmail:failed", task.getException());
                                    Toast.makeText(getActivity(), "Denied", Toast.LENGTH_SHORT).show();
                                }
                                // ...
                            }
                        });

            }
        });

        return view;
    }

}
