package Data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hasani.moein.taan.tinynotebook.Notesdetails_forlist;
import com.hasani.moein.taan.tinynotebook.R;

import java.util.ArrayList;

import Model.note;

public class CustomListViewAdapter extends ArrayAdapter<note> {

    private int Layoutresource;
    private Activity activity;
    private ArrayList<note> set_list=new ArrayList<>();

    public CustomListViewAdapter(Activity act,int resource,
                                 ArrayList<note> data)
    {
        super(act,resource,data);
        Layoutresource =resource;
        activity = act;
        set_list = data;
        notifyDataSetChanged();

    }


    @Override
    public int getCount() {
        return set_list.size();
    }

    @Override
    public note getItem(int position) {
        return set_list.get(position);
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

        View row=convertView;
        Viewholder holder=null;

        if (row == null||(row.getTag()==null)) {
            LayoutInflater inflater= LayoutInflater.from(activity);
            row=inflater.inflate(Layoutresource,null);

            holder=new Viewholder();

            holder.date=(TextView)row.findViewById(R.id.Date);
            holder.title=(TextView)row.findViewById(R.id.wishtilte);

            row.setTag(holder);

        }else{
            holder=(Viewholder)row.getTag();
        }

        holder.Note=getItem(position);

        holder.date.setText(holder.Note.getDate());
        holder.title.setText(String.valueOf(holder.Note.getTitle()));

        final Viewholder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(activity,Notesdetails_forlist.class);

                Bundle mBundle=new Bundle();
                mBundle.putSerializable("My object", finalHolder.Note);
                i.putExtras(mBundle);
                activity.startActivity(i);
                activity.finish();

            }
        });
        return row;
    }


    public class Viewholder{
        note Note;
        TextView title;
        TextView date;
    }
}
