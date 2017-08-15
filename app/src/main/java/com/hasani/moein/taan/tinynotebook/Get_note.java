package com.hasani.moein.taan.tinynotebook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
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
    private Button save;
    private ImageView preview;
     private FloatingActionButton choose;
    private int request_code = 1;
    note mynote = new note();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_note);

        preview=(ImageView)findViewById(R.id.pre1);
        choose = (FloatingActionButton) findViewById(R.id.choose);
        Title_edit = (EditText) findViewById(R.id.title);
        Text_edit = (EditText) findViewById(R.id.text);
        save = (Button) findViewById(R.id.submit);
        //cancel = (Button) findViewById(R.id.cancel);

      //  mynote.setUri(null);
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
                DataBaseHandler dbh = new DataBaseHandler(Get_note.this);
                if (Text_edit.getText().toString().equals("") &&
                        Title_edit.getText().toString().equals("")) {

                    Toast.makeText(Get_note.this, "Please enter some content...", Toast.LENGTH_SHORT).show();

                } else {
                    mynote.setTitle(Title_edit.getText().toString().trim());
                    mynote.setContent(Text_edit.getText().toString().trim());

                    dbh.addnote(mynote);
                    dbh.close();
                    Log.v(TAG, "Addedto Db************");
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


                preview.setImageURI(photoFileUri);
                BitmapDrawable drawable = (BitmapDrawable) preview.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                //changing the quality
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,70,os);
                //////////////////////////////////////////////////////////////////////////////////////////


                mynote.setBitmap(os.toByteArray());
                mynote.setUri(photoFileUri.toString());
                Log.v("ss","################# bitmap recived  "+os.toByteArray().toString());

//
// try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoFileUri);
//                    Log.v(TAG,"***************Uri recieved= "+photoFileUri.toString());
//                    //mynote.setBytes(DbBitmapUtility.getBytes(bitmap));
//                  //  mynote.setBitmap(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                //////////////////////////////////////////////////////////////////////////////////////////

                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


}
