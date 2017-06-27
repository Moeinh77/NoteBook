package com.hasani.moein.taan.tinynotebook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import Data.DataBaseHandler;
import Model.note;

public class notes_list extends AppCompatActivity {

    private DataBaseHandler dbh;
    private ArrayList<note> dbnotes=new ArrayList<>();
    private ListView listView ;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView=(ListView)findViewById(R.id.listview);
        refreshData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(notes_list.this, Note.class);
                startActivity(intent);
            }
        });




    }

          public void refreshData(){

              dbnotes.clear();
              dbh =new DataBaseHandler(getApplicationContext());
              ArrayList<note> notesfromdb=dbh.getnotes();

          }
}
