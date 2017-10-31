package com.hasani.moein.taan.tinynotebook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import Data.DataBaseHandler;
import Model.note;

public class Get_note extends AppCompatActivity {
    private static final String TAG = "TAg";
    private EditText Title_edit;
    private EditText Text_edit;
    private ImageView preview;
    private FloatingActionButton choose;
    private int request_code = 1;
    note mynote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_note);

        preview = (ImageView) findViewById(R.id.pre1);
        choose = (FloatingActionButton) findViewById(R.id.choose);
        Title_edit = (EditText) findViewById(R.id.title);
        Text_edit = (EditText) findViewById(R.id.text);
        Button save = (Button) findViewById(R.id.submit);
        mynote = new note();

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

//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                finish();
//            }
//        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataBaseHandler dbh = new DataBaseHandler(getApplicationContext());

                if (Text_edit.getText().toString().equals("") &&
                        Title_edit.getText().toString().equals("")) {

                    Toast.makeText(Get_note.this, "Please enter some text...",
                            Toast.LENGTH_SHORT).show();

                } else {

                    mynote.setTitle(Title_edit.getText().toString().trim());
                    mynote.setContent(Text_edit.getText().toString().trim());

                    dbh.addnote(mynote);
                    dbh.close();
                    finish();

                    Log.v(TAG, "Addedto Db************");

                }
            }
        });
    }


    //baraye gereftan address e akse entekhab shode ast
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == request_code) {

                Uri photoFileUri = data.getData();


                preview.setImageURI(photoFileUri);
                BitmapDrawable drawable = (BitmapDrawable) preview.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                //changing the quality
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, os);
                //////////////////////////////////////////////////////////////////////////////////////////


                mynote.setBitmap(os.toByteArray());
                mynote.setUri(photoFileUri.toString());
                Log.v("ss", "################# bitmap recived  " + os.toByteArray().toString());


                //////////////////////////////////////////////////////////////////////////////////////////

                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


}
