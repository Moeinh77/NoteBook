package com.hasani.moein.taan.tinynotebook;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Data.CustomListViewAdapter;
import Data.DataBaseHandler;
import Model.note;

public class notes_list extends AppCompatActivity {

    private DataBaseHandler dbh;
    private ArrayList<note> dbnotes=new ArrayList<>();
    private ListView listView ;
    private CustomListViewAdapter customListViewAdapter;

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
                finish();
            }
        });


    }
          public void refreshData(){

              dbnotes.clear();
              dbh =new DataBaseHandler(getApplicationContext());
              ArrayList<note> notesfromdb=dbh.getnotes();
              for(int i=0;i<notesfromdb.size();i++){
                  note mNote =new note();
                  if(notesfromdb.get(i).getTitle().equals(""))
                  {
                      mNote.setTitle("No Title...");

                  }else{
                      mNote.setTitle(notesfromdb.get(i).getTitle());
                  }
                  mNote.setContent(notesfromdb.get(i).getContent());
                  mNote.setDate(notesfromdb.get(i).getDate());
                  int mid=notesfromdb.get(i).getId();
                  mNote.setId(mid);

                  dbnotes.add(mNote);
              }
              dbh.close();

              ////////////
              customListViewAdapter=new CustomListViewAdapter
                      (notes_list.this,R.layout.wish_row,dbnotes);
              listView.setAdapter(customListViewAdapter);
              customListViewAdapter.notifyDataSetChanged();
          }
//    public class NoteAdapter extends ArrayAdapter<note>{
//        Activity activity;
//        int layoutResource;
//        note wish;
//        ArrayList<note> mData = new ArrayList<>();
//
//        public NoteAdapter(Activity act, int resource, ArrayList<note> data) {
//            super(act, resource, data);
//            activity = act;
//            layoutResource = resource;
//            mData = data;
//            notifyDataSetChanged();
//
//
//        }
//        @Override
//        public boolean isEnabled(int position) {
//            return false;
//        }
//        @Override
//        public int getCount() {
//            return mData.size();
//        }
//
//        @Override
//        public note getItem(int position) {
//            return mData.get(position);
//        }
//
//        @Override
//        public int getPosition(note item) {
//            return super.getPosition(item);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return super.getItemId(position);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            View row = convertView;
//            ViewHolder holder = null;
//
//            if ( row == null || (row.getTag()) == null){
//
//                LayoutInflater inflater = LayoutInflater.from(activity);
//
//                row = inflater.inflate(layoutResource, null);
//                holder = new ViewHolder();
//
//                holder.mTitle = (TextView) row.findViewById(R.id.wishtilte);
//                holder.mDate = (TextView) row.findViewById(R.id.Date);
//
//
//
//                row.setTag(holder);
//
//            }else {
//
//                holder = (ViewHolder) row.getTag();
//            }
//
//            holder.mynote = getItem(position);
//
//            holder.mTitle.setText(holder.mynote.getTitle());
//            holder.mDate.setText(holder.mynote.getDate());
//
//
//
//            final ViewHolder finalHolder = holder;
//            holder.mTitle.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    String text = finalHolder.mynote.getContent().toString();
//                    String dateText = finalHolder.mynote.getDate().toString();
//                    String title = finalHolder.mynote.getTitle().toString();
//
//                    int mId = finalHolder.mynote.getId();
//
//                    Intent i = new Intent(notes_list.this, Notesdetails_forlist.class);
//                    i.putExtra("content", text);
//                    i.putExtra("date", dateText);
//                    i.putExtra("title", title);
//                    i.putExtra("id", mId);
//                    startActivity(i);
//                    finish();
//
//                }
//            });
//            holder.mDate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    String text = finalHolder.mynote.getContent().toString();
//                    String dateText = finalHolder.mynote.getDate().toString();
//                    String title = finalHolder.mynote.getTitle().toString();
//
//                    int mId = finalHolder.mynote.getId();
//
//                    Intent i = new Intent(notes_list.this, Notesdetails_forlist.class);
//                    i.putExtra("content", text);
//                    i.putExtra("date", dateText);
//                    i.putExtra("title", title);
//                    i.putExtra("id", mId);
//                    startActivity(i);
//                    finish();
//
//                }
//            });
//            return row;
//
//        }
//        class ViewHolder{
//
//            note mynote;
//            TextView mTitle;
//            int mId;
//            TextView mContent;
////            ImageView imageView;
////            imageView=(ImageView)findViewById(R.id.)
//            TextView mDate;
//
//        }
//
//    }
}
