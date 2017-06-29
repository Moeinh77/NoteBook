package com.hasani.moein.taan.tinynotebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import Data.DataBaseHandler;
import Model.note;

public class Note extends AppCompatActivity {
    private EditText Title_edit;
    private EditText Text_edit;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        delete=(Button)findViewById(R.id.deletebutt);
        Title_edit=(EditText)findViewById(R.id.title);
        Text_edit=(EditText)findViewById(R.id.text);

        Bundle reciever=getIntent().getExtras();
        if(reciever==null){

        }else{

            final int id=reciever.getInt("id");
            Title_edit.setText(reciever.getString("title"));
            Text_edit.setText(reciever.getString("content"));

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataBaseHandler dbh=new DataBaseHandler(getApplicationContext());
                    dbh.removenote(id);
                    Intent i=new Intent(Note.this,notes_list.class);
                    startActivity(i);
                }
            });
        }


    }

    @Override
    protected void onDestroy() {
        DataBaseHandler dbh=new DataBaseHandler(Note.this);
        note mynote=new note();
        mynote.setTitle(Title_edit.getText().toString().trim());
        mynote.setContent(Text_edit.getText().toString().trim());
        if(){
            dbh.removenote(mynote.getId());
        }
//        ArrayList<note> helper=dbh.getnotes();
//        for(int i=0;i<helper.size();i++) {
//            title_string=helper.get(i).getTitle();
//        }
//        if(helper.contains(mynote.getTitle())){
//            dbh.
//        }
//        else{
            dbh.addnote(mynote);
        //}
        dbh.close();
        Intent intent=new Intent(Note.this,notes_list.class);
        startActivity(intent);
        super.onDestroy();

    }
}
