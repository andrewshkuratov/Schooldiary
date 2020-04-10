package it_school.sumdu.edu.schooldiary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter {

    ArrayList list = new ArrayList();

    public CustomAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class DataHandler{
        TextView subject;
        TextView task;
    }

    @Override
    public void add(Object object){
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount(){
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) { View row; DataHandler handler;
        row = convertView;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.homework_list_row,parent,false);
            handler = new DataHandler();
            handler.subject = (TextView)row.findViewById(R.id.textView7);
            handler.task = (TextView)row.findViewById(R.id.textView9);
            row.setTag(handler);
        }else{
            handler = (DataHandler)row.getTag();
        }
        DiaryProvider diaryProvider;
        diaryProvider = (DiaryProvider)getItem(position);
        handler.subject.setText(diaryProvider.getSubject());
        handler.task.setText(diaryProvider.getTask());
        return row;
    }
}
