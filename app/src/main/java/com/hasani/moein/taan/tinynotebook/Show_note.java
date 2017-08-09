package com.hasani.moein.taan.tinynotebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import Data.DataBaseHandler;
import Model.note;

public class Show_note extends AppCompatActivity {

    TextView content,date,title,time;
    Button delete;
    private AlertDialog.Builder alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shpw_note);

        time=(TextView)findViewById(R.id.time);
        title=(TextView)findViewById(R.id.title);
        content=(TextView)findViewById(R.id.wish);
        date=(TextView)findViewById(R.id.date);
        delete=(Button)findViewById(R.id.delete);

        final note Note=(note)getIntent().getSerializableExtra("My object");

            title.setText(Note.getTitle());
            content.setText(Note.getContent());
            date.setText(Note.getDate());
            time.setText(Note.getTime());


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert=new AlertDialog.Builder(Show_note.this);
                alert.setTitle("Delete");
                alert.setMessage("Do you want to delete this note ?");
                alert.setCancelable(false);
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataBaseHandler dbh=new DataBaseHandler(getApplicationContext());
                        dbh.removenote(Note.getId());
                        finish();

                    }
                });

                AlertDialog alert_example=alert.create();
                alert_example.show();


            }
        });
    }

    @Override
    protected void onDestroy() {


        super.onDestroy();
    }
}
