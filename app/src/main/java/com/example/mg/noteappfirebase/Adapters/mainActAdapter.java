package com.example.mg.noteappfirebase.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mg.noteappfirebase.CRUD.view_note;
import com.example.mg.noteappfirebase.Models.noteModel;
import com.example.mg.noteappfirebase.R;
import com.example.mg.noteappfirebase.storeRoom.store;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class mainActAdapter extends RecyclerView.Adapter<mainActAdapter.myViewHolderClass> {

    ArrayList<noteModel> noteModels;
    Context context;

    public mainActAdapter(ArrayList<noteModel> noteModels) {
        this.noteModels = noteModels;
    }

    public class myViewHolderClass extends RecyclerView.ViewHolder {
        TextView noteText, noteDate , noteTittle;

        public myViewHolderClass(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            noteTittle = (TextView) itemView.findViewById(R.id.noteTittle);
            noteText = (TextView) itemView.findViewById(R.id.noteText);
            noteDate = (TextView) itemView.findViewById(R.id.noteDate);
        }
    }

    @NonNull
    @Override
    public mainActAdapter.myViewHolderClass onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_single_row, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new myViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mainActAdapter.myViewHolderClass holder, final int i) {
        holder.noteText.setText(noteModels.get(i).getNoteTitle());
        holder.noteDate.setText(store.getReadAbleDate(noteModels.get(i).getTimeStamp()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle pack = new Bundle();
                pack.putString("note", noteModels.get(i).getNoteText());
                pack.putString("tittle", noteModels.get(i).getNoteTitle());
                pack.putLong("date", noteModels.get(i).getTimeStamp());
                pack.putString("nodeAddress", noteModels.get(i).getNodeAddress());
                Intent intent = new Intent(context, view_note.class);
                intent.putExtras(pack);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder box = new AlertDialog.Builder(context);
                box.setMessage("Delete this note!");
                box.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        FirebaseDatabase.getInstance().getReference().child("notes").child(auth.getCurrentUser().getUid()).child(noteModels.get(i).getNodeAddress())
                                .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "note delete", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Error\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return noteModels.size();
    }
}
