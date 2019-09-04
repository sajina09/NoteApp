package com.example.mg.noteappfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class change_password extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        EditText current_password=(EditText)findViewById(R.id.current_password);
        EditText new_password=(EditText)findViewById(R.id.new_password);
        EditText confirm_password=(EditText)findViewById(R.id.confirm_password);
        Button btn_change = (Button)findViewById(R.id.btnChange);
        EditText old_psw = (EditText)findViewById(R.id.user_password);

        



    }


}
