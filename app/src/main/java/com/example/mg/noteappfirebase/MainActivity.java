package com.example.mg.noteappfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mg.noteappfirebase.Adapters.mainActAdapter;
import com.example.mg.noteappfirebase.CRUD.create_note;
import com.example.mg.noteappfirebase.Models.noteModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recView;
    DatabaseReference rootRef;
    FirebaseAuth auth;
    ArrayList<noteModel> noteModels;
    mainActAdapter adapObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recView = (RecyclerView) findViewById(R.id.recView);
        recView.setLayoutManager(new LinearLayoutManager(this));
        auth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference().child("notes");

        noteModels = new ArrayList<>();
        adapObj = new mainActAdapter(noteModels);
        recView.setAdapter(adapObj);


        notesReader();
    }


    public void notesReader() {

        Log.i("123232", "called " + auth.getCurrentUser().getUid() + "");

        rootRef.child(auth.getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                noteModel noteModelObj = dataSnapshot.getValue(noteModel.class);
                noteModels.add(noteModelObj);
                adapObj.notifyDataSetChanged();
                Log.i("123232 - child added", dataSnapshot + "");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                noteModel updatedNoteModel = dataSnapshot.getValue(noteModel.class);
                for (int i = 0; i < noteModels.size(); i++) {
                    if (noteModels.get(i).getNodeAddress().trim().equals(dataSnapshot.getKey())) {
                        noteModels.remove(noteModels.get(i));
                        noteModels.add(i, updatedNoteModel);
                        adapObj.notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                for (int i = 0; i < noteModels.size(); i++) {
                    if (noteModels.get(i).getNodeAddress().trim().equals(dataSnapshot.getKey())) {
                        noteModels.remove(noteModels.get(i));
                        adapObj.notifyDataSetChanged();
                        break;
                    }
                }


                Log.i("123232 - child removed", dataSnapshot + "");
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selectedID = item.getItemId();
        if (selectedID == R.id.newNote) {
            startActivity(new Intent(getApplicationContext(), create_note.class));
        } else if (selectedID == R.id.log_out) {
            auth.signOut();
            startActivity(new Intent(getApplicationContext(), create_account.class));
            finish();
        }
        else if (selectedID == R.id.change_password){
            startActivity(new Intent(getApplicationContext(), change_password.class));
        }
        return super.onOptionsItemSelected(item);
    }
}