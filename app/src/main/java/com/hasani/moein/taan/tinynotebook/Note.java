package com.hasani.moein.taan.tinynotebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import Data.DataBaseHandler;
import Model.note;

public class Note extends AppCompatActivity {
    private EditText Title_edit;
    private EditText Text_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Title_edit=(EditText)findViewById(R.id.title);
        Text_edit=(EditText)findViewById(R.id.text);



    }

    @Override
    protected void onDestroy() {
        DataBaseHandler dbh=new DataBaseHandler(Note.this);
        note mynote=new note();
        mynote.setTitle(Title_edit.getText().toString().trim());
        mynote.setContent(Text_edit.getText().toString().trim());
        dbh.addnote(mynote);
        dbh.close();
        Intent intent=new Intent(Note.this,notes_list.class);
        startActivity(intent);
        super.onDestroy();

    }
}
