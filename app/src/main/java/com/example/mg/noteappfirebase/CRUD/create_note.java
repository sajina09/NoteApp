package com.example.mg.noteappfirebase.CRUD;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.mg.noteappfirebase.Models.noteModel;
import com.example.mg.noteappfirebase.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class create_note extends AppCompatActivity {

    EditText noteTittle;
    EditText noteText;
    DatabaseReference rootRef;
    FirebaseAuth auth;

    boolean success = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        noteTittle = (EditText) findViewById(R.id.noteTittle);
        noteText = (EditText) findViewById(R.id.noteText);
        rootRef = FirebaseDatabase.getInstance().getReference().child("notes");
        auth = FirebaseAuth.getInstance();
    }

    public void add_picture(){
        startActivity(new Intent(this, view_note.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_note, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.saveNote) {
            String tittle = noteTittle.getText().toString();
            String noteStr = noteText.getText().toString();
            if (TextUtils.isEmpty(noteStr)) {
                Toast.makeText(getApplicationContext(), "Please enter some thing", Toast.LENGTH_SHORT).show();
            } else {

                String key = rootRef.push().getKey();
                noteModel noteModelObj = new noteModel(tittle,noteStr, key);
                rootRef.child(auth.getCurrentUser().getUid()).child(key).setValue(noteModelObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Note saved", Toast.LENGTH_SHORT).show();
                        finish();
                        success = true;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "error\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(getApplicationContext(), "Note saved", Toast.LENGTH_SHORT).show();
                finish();
                if (success == false){
                    Toast.makeText(getApplicationContext(), "Note will automatically be synced when online", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }


}