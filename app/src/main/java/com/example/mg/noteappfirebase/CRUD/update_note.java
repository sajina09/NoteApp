package com.example.mg.noteappfirebase.CRUD;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mg.noteappfirebase.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class update_note extends AppCompatActivity {

    EditText noteText, noteTittle;
    Bundle unpacker;

    boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        noteText = (EditText) findViewById(R.id.noteText);
        noteTittle=(EditText) findViewById(R.id.noteTittle);
        unpacker = getIntent().getExtras();

        noteText.setText(unpacker.getString("note"));
        noteTittle.setText(unpacker.getString("tittle"));
        noteText.setSelection(unpacker.getString("note").length());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.saveNote) {

            String tittleStr = noteTittle.getText().toString().trim();
            String noteStr = noteText.getText().toString().trim();
            if (TextUtils.isEmpty(noteStr)) {
                Toast.makeText(getApplicationContext(), "Please enter some thing", Toast.LENGTH_SHORT).show();
            } else {

                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseDatabase.getInstance().getReference().child("notes").child(auth.getCurrentUser().getUid()).child(unpacker.getString("nodeAddress"))
                        .child("noteText").setValue(noteStr).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        success = true;
                        Toast.makeText(getApplicationContext(), "note updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "error\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                FirebaseDatabase.getInstance().getReference().child("notes").child(auth.getCurrentUser().getUid()).child(unpacker.getString("nodeAddress"))
                        .child("noteTittle").setValue(tittleStr).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        success = true;
                        Toast.makeText(getApplicationContext(), "tittle updated", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "error\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                if (!success){
                    Toast.makeText(getApplicationContext(), "note will automatically be updated when device is online", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
