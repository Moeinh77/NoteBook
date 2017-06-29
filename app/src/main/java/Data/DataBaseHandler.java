package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CheckedOutputStream;

import Model.note;

import static android.content.ContentValues.TAG;

/**
 * Created by Moein on 6/26/2017.
 */

public class DataBaseHandler extends SQLiteOpenHelper {

    private final ArrayList<note> noteArrayList=new ArrayList<>();

    public DataBaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String TABLE_CREATION=
                "create table "+ Constants.TABLE_NAME+"("+Constants.KEY_ID+" INTEGER PRIMARY KEY,"+
                Constants.TITLE_NAME+" TEXT,"+Constants.CONTENT_NAME+" TEXT,"+Constants.DATE_NAME+" LONG);";

        db.execSQL(TABLE_CREATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists "+Constants.TABLE_NAME);
        onCreate(db);

    }

    public void addnote(note new_note){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Constants.TITLE_NAME,new_note.getTitle());
        values.put(Constants.CONTENT_NAME,new_note.getContent());
        values.put(Constants.DATE_NAME,java.lang.System.currentTimeMillis());
        db.insert(Constants.TABLE_NAME,null,values);
        Log.d(TAG, "addnote: successfull");
        db.close();

    }

    public ArrayList<note> getnotes(){

        String select="select * from "+Constants.TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(Constants.TABLE_NAME,new String[]{Constants.KEY_ID,
                Constants.TITLE_NAME,Constants.CONTENT_NAME,Constants.DATE_NAME}
                ,null,null,null,null,Constants.DATE_NAME+" DESC");

        if(cursor.moveToFirst()){
            do{
                note mynote=new note();
                mynote.setTitle(cursor.getString(cursor.getColumnIndex(Constants.TITLE_NAME)));
                mynote.setContent(cursor.getString(cursor.getColumnIndex(Constants.CONTENT_NAME)));
                java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();//new ***
                String date_=dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());
                //Date depricatted
                mynote.setDate(date_);
                noteArrayList.add(mynote);

            }while (cursor.moveToNext());
        }
        return noteArrayList;

    }
}
