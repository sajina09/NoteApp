package com.example.mg.noteappfirebase.CRUD;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mg.noteappfirebase.R;
import com.example.mg.noteappfirebase.storeRoom.store;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class view_note extends AppCompatActivity {

    TextView noteText,noteTittle,noteDate;
    Bundle unpacker;
    LinearLayout full_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        noteDate = (TextView) findViewById(R.id.noteDate);
        noteText = (TextView) findViewById(R.id.noteText);
        noteTittle=(TextView) findViewById(R.id.noteTittle);
        unpacker = getIntent().getExtras();

        noteText.setText(unpacker.getString("note"));
        noteTittle.setText(unpacker.getString("tittle"));
        noteDate.setText(store.getReadAbleDate(unpacker.getLong("date")));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int selectedID = item.getItemId();
        if (selectedID == R.id.delete) {

            AlertDialog.Builder box = new AlertDialog.Builder(getApplicationContext());
            box.setMessage("Delete this note!");
            box.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseDatabase.getInstance().getReference().child("notes").child(auth.getCurrentUser().getUid()).child(unpacker.getString("nodeAddress"))
                            .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "note delete", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            box.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = box.create();
            dialog.show();


        } else if (selectedID == R.id.update) {
            Bundle pack = new Bundle();
            pack.putString("note", unpacker.getString("note"));
            pack.putString("tittle", unpacker.getString("tittle"));
            pack.putString("nodeAddress", unpacker.getString("nodeAddress"));
            Intent intent = new Intent(getApplicationContext(), update_note.class);
            intent.putExtras(pack);
            startActivity(intent);
            finish();
        }


        return super.onOptionsItemSelected(item);
    }
}
