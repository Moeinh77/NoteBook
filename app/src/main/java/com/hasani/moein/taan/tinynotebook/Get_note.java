package com.hasani.moein.taan.tinynotebook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import Data.DataBaseHandler;
import Model.note;

public class Get_note extends AppCompatActivity {
    private static final String TAG = "TAg";
    private EditText Title_edit;
    private EditText Text_edit;
    private Button save;
    private Button cancel,choose;
    private int request_code=1;
    note mynote=new note();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_note);

        choose=(Button)findViewById(R.id.choose);
        Title_edit=(EditText)findViewById(R.id.title);
        Text_edit=(EditText)findViewById(R.id.text);
        save=(Button)findViewById(R.id.submit);
        cancel=(Button)findViewById(R.id.cancel);

       // mynote.setUri(null);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting readable Uri
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, request_code);
                /////////////////////////
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHandler dbh=new DataBaseHandler(Get_note.this);
                if(Text_edit.getText().toString().equals("")&&
                        Title_edit.getText().toString().equals("") )
                {

                    finish();
                }else{
                    mynote.setTitle(Title_edit.getText().toString().trim());
                    mynote.setContent(Text_edit.getText().toString().trim());

                    dbh.addnote(mynote);
                    dbh.close();
Log.v(TAG,"Addedto Db************");
                    finish();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == request_code) {

                Uri photoFileUri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoFileUri);
                    Log.v(TAG,"***************Uri recieved= "+photoFileUri.toString());
                    mynote.setBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //////////////////////////////////////////////////////////////////////////////////////////

                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

}
