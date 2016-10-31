package com.example.imran.feragments.User_SignIn_Up_fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.imran.feragments.R;
import com.example.imran.feragments.Services.MyService;
import com.example.imran.feragments.User_todo_pkg.User_Todo;
import com.example.imran.feragments.UserinfoAndTodo;
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
    //ProgressBar pbar = new ProgressBar(getActivity());
    private ProgressDialog pd;

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

        useremail = (EditText) view.findViewById(R.id.Useremail_text);
        userpassword = (EditText) view.findViewById(R.id.Userpassword_text);


//        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//                    Intent it = new Intent(getActivity(), UserinfoAndTodo.class);
//                    startActivity(it);
//                    getActivity().finish();
//
//                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d("TAG", "onAuthStateChanged:signed_out");
//                }
//            }
//        });


        sign_in = (Button) view.findViewById(R.id.User_SigninButton);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String user_Email = useremail.getText().toString();
                String user_Password = userpassword.getText().toString();

                if (TextUtils.isEmpty(user_Email)) {
                    useremail.setError("Required");

                } else if (TextUtils.isEmpty(user_Password)) {
                    userpassword.setError("Required");
                } else {
                    pd = new ProgressDialog(getContext());
                    pd.setMessage("Loging in");
                    pd.show();
                    mAuth.signInWithEmailAndPassword(user_Email, user_Password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());

                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getActivity(), UserinfoAndTodo.class);
                                        startActivity(i);
                                        getActivity().finish();
                                        pd.dismiss();
//                                    new MyService().onStartCommand(i,0, 0);
                                    } else if (!task.isSuccessful()) {
                                        Log.w("TAG", "signInWithEmail:failed", task.getException());
                                        Toast.makeText(getActivity(), "Denied", Toast.LENGTH_SHORT).show();
                                    }
                                    // ...
                                }
                            });

                }

            }
        });

        return view;
    }

}
