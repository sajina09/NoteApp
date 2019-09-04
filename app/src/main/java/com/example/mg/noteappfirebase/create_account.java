package com.example.mg.noteappfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mg.noteappfirebase.Models.userModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class create_account extends AppCompatActivity {

    EditText password, username, email;
    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        password = (EditText) findViewById(R.id.user_password);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.user_email);
        dialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }

    public void create_account(View view) {
        final String passwordStr = password.getText().toString().trim();
        final String usernameStr = username.getText().toString().trim();
        final String emailStr = email.getText().toString().trim();

        dialog.setMessage("Creating account");
        dialog.setCancelable(false);
        dialog.show();

        if (TextUtils.isEmpty(passwordStr) || TextUtils.isEmpty(usernameStr) || TextUtils.isEmpty(emailStr)) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        } else {
            auth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("users");
                    String UID = auth.getCurrentUser().getUid();
                    userModel userModelObj = new userModel(UID, usernameStr, emailStr, passwordStr);
                    dialog.dismiss();
                    rootRef.child(UID).setValue(userModelObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("12323323",e.getMessage());
                            Toast.makeText(getApplicationContext(), "Error\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void open_login_act(View view) {
        startActivity(new Intent(this, log_in.class));
        finish();
    }
}
