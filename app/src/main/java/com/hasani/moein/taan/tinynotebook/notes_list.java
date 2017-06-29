package com.hasani.moein.taan.tinynotebook;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
                //finish();
            }
        });


    }
          public void refreshData(){
              dbnotes.clear();
              dbh =new DataBaseHandler(getApplicationContext());
              ArrayList<note> notesfromdb=dbh.getnotes();
              for(int i=0;i<notesfromdb.size();i++){
                  note mNote =new note();
                  mNote.setTitle(notesfromdb.get(i).getTitle());
                  mNote.setContent(notesfromdb.get(i).getContent());
                  mNote.setDate(notesfromdb.get(i).getDate());

                  dbnotes.add(mNote);
              }
              dbh.close();
              noteAdapter=new NoteAdapter(notes_list.this,R.layout.wish_row,dbnotes);
              listView.setAdapter(noteAdapter);
          }
    public class NoteAdapter extends ArrayAdapter<note>{
        Activity activity;
        int layoutResource;
        note wish;
        ArrayList<note> mData = new ArrayList<>();

        public NoteAdapter(Activity act, int resource, ArrayList<note> data) {
            super(act, resource, data);
            activity = act;
            layoutResource = resource;
            mData = data;
            notifyDataSetChanged();


        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public note getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getPosition(note item) {
            return super.getPosition(item);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = convertView;
            ViewHolder holder = null;

            if ( row == null || (row.getTag()) == null){

                LayoutInflater inflater = LayoutInflater.from(activity);

                row = inflater.inflate(layoutResource, null);
                holder = new ViewHolder();

                holder.mTitle = (TextView) row.findViewById(R.id.wishtilte);
                holder.mDate = (TextView) row.findViewById(R.id.Date);



                row.setTag(holder);

            }else {

                holder = (ViewHolder) row.getTag();
            }

            holder.myWish = getItem(position);

            holder.mTitle.setText(holder.myWish.getTitle());
            holder.mDate.setText(holder.myWish.getDate());



            final ViewHolder finalHolder = holder;
            holder.mTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String text = finalHolder.myWish.getContent().toString();
                    String dateText = finalHolder.myWish.getDate().toString();
                    String title = finalHolder.myWish.getTitle().toString();

                    //int mid = finalHolder.myWish.getItemId();

                   // Log.v("MyId: " , String.valueOf(mid));


//




                    Intent i = new Intent(notes_list.this, Note.class);
                    i.putExtra("content", text);
                    i.putExtra("date", dateText);
                    i.putExtra("title", title);
                    //i.putExtra("id", mid);


                    startActivity(i);


                }
            });
            return row;

        }
        class ViewHolder{

            note myWish;
            TextView mTitle;
            int mId;
            TextView mContent;
            TextView mDate;

        }

    }
}
