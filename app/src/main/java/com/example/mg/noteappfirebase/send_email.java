package com.example.mg.noteappfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class send_email extends AppCompatActivity {

    EditText email;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        email = (EditText)findViewById(R.id.email);
        Button reset = (Button)findViewById(R.id.reset_psw);
        auth = FirebaseAuth.getInstance();

//        auth.sendPasswordResetEmail(String.valueOf(emailAddress))
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "Email sent.");
//                        }
//                    }
//                });
        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(send_email.this, "email typed:"+email.getText(), Toast.LENGTH_SHORT).show();

                auth.getInstance().sendPasswordResetEmail(String.valueOf(email.getText()))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(send_email.this, "password reset email sent!", Toast.LENGTH_SHORT).show();
                                    Log.d("send_email:", "Email sent.");
                                }
                                else
                                    Toast.makeText(send_email.this, "some error encountered!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }
}
