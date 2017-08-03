package com.hasani.moein.taan.tinynotebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import Data.DataBaseHandler;
import Model.note;

public class Get_note extends AppCompatActivity {
    private EditText Title_edit;
    private EditText Text_edit;
    private Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Title_edit=(EditText)findViewById(R.id.title);
        Text_edit=(EditText)findViewById(R.id.text);
        Submit=(Button)findViewById(R.id.submit);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        DataBaseHandler dbh=new DataBaseHandler(Get_note.this);
        note mynote=new note();
        if(Text_edit.getText().toString().equals("")&&
                Title_edit.getText().toString().equals("") )
        {
            Intent intent=new Intent(Get_note.this,notes_list.class);
            startActivity(intent);
            finish();
        }else{
        mynote.setTitle(Title_edit.getText().toString().trim());
        mynote.setContent(Text_edit.getText().toString().trim());
        dbh.addnote(mynote);
        dbh.close();
        Intent intent=new Intent(Get_note.this,notes_list.class);
        startActivity(intent);
        finish();
        }
        super.onDestroy();
    }
}
