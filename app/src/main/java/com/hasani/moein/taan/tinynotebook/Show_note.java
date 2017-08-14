package com.hasani.moein.taan.tinynotebook;

import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import Data.DataBaseHandler;
import Data.DbBitmapUtility;
import Model.note;

public class Show_note extends AppCompatActivity {

    private static final String TAG = "a";
    TextView content, date, title, time;
    Button delete;
    private ImageView imageView;
    private AlertDialog.Builder alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_note);

        imageView = (ImageView) findViewById(R.id.image);
        time = (TextView) findViewById(R.id.time);
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.wish);
        date = (TextView) findViewById(R.id.date);
        delete = (Button) findViewById(R.id.delete);

        final note note1 = (note) getIntent().getSerializableExtra("My object");

        title.setText(note1.getTitle());
        content.setText(note1.getContent());
        date.setText(note1.getDate());
        time.setText(note1.getTime());

        Log.v(TAG, "*****Bitmap = " + note1.getBitmap());


        if (note1.getBitmap() != null) {

            imageView.setImageBitmap(DbBitmapUtility.getImage(note1.getBitmap()));
           ////////////////////////////////////////////////////
        }

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alert = new AlertDialog.Builder(Show_note.this);
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
                            DataBaseHandler dbh = new DataBaseHandler(getApplicationContext());
                            dbh.removenote(note1.getId());
                            finish();

                        }
                    });

                    AlertDialog alert_example = alert.create();
                    alert_example.show();


                }
            });



    }
}
