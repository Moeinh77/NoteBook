package com.hasani.moein.taan.tinynotebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import Data.CustomListViewAdapter;
import Data.DataBaseHandler;
import Model.note;

public class notes_list extends AppCompatActivity {

    private DataBaseHandler dbh;
    //private ArrayList<note> dbnotes=new ArrayList<>();
    private ListView listView ;
    private CustomListViewAdapter customListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView=(ListView)findViewById(R.id.listview);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(notes_list.this, Get_note.class);
                startActivity(intent);
            }
        });


    }
          public void refreshData(){
              dbh =new DataBaseHandler(getApplicationContext());
              ArrayList<note> notesfromdb=dbh.getnotes();
              customListViewAdapter=new CustomListViewAdapter
                      (notes_list.this,R.layout.wish_row,notesfromdb);
              listView.setAdapter(customListViewAdapter);
              customListViewAdapter.notifyDataSetChanged();
          }

    @Override
    protected void onResume() {
        refreshData();

        super.onResume();
    }
}
