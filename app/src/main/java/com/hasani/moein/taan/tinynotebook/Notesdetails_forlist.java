package com.hasani.moein.taan.tinynotebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import Data.DataBaseHandler;
import Model.note;

import static android.R.attr.id;

public class Notesdetails_forlist extends AppCompatActivity {

    TextView title;
    TextView content;
    TextView date;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notesdetails_forlist);

        title=(TextView)findViewById(R.id.title);
        content=(TextView)findViewById(R.id.wish);
        date=(TextView)findViewById(R.id.date);
        delete=(Button)findViewById(R.id.delete);

        final note Note=(note)getIntent().getSerializableExtra("My object");

            title.setText(Note.getTitle());
            content.setText(Note.getContent());
            date.setText(Note.getDate());


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHandler dbh=new DataBaseHandler(getApplicationContext());
                dbh.removenote(Note.getId());
                finish();

            }
        });
    }

    @Override
    protected void onDestroy() {
        Intent i=new Intent(Notesdetails_forlist.this,notes_list.class);
        startActivity(i);

        super.onDestroy();
    }
}
