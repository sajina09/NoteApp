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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class log_in extends AppCompatActivity {

    EditText password, email;
    FirebaseAuth auth;
    ProgressDialog dialog;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        password = (EditText) findViewById(R.id.user_password);
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

    public void open_sign_act(View view) {
        startActivity(new Intent(this, create_account.class));
        finish();
    }

    public void change_psw(View view) {
        startActivity(new Intent(this, send_email.class));
        finish();



    }

    public void access_account(View view) {
        final String passwordStr = password.getText().toString().trim();
        final String emailStr = email.getText().toString().trim();

        dialog.setMessage("Accessing account");
        dialog.setCancelable(false);
        dialog.show();

        if (TextUtils.isEmpty(passwordStr) || TextUtils.isEmpty(emailStr)) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }else{
            auth.signInWithEmailAndPassword(emailStr,passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(getApplicationContext(), "Log in successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Error\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        }

    }

}
