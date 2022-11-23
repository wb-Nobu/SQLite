package com.example.sqlite;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NoteAdapter extends ArrayAdapter<Note> {
    private Context context; //trả về activity mà listview hiển thị - this
    private int resource;

    public NoteAdapter(Context context, int resource){
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View customView = View.inflate(context,resource,null);

        TextView tvTitle = customView.findViewById(R.id.tv_title);
        TextView tvContent = customView.findViewById(R.id.tv_content);

        Note note = getItem(position);//lấy đối tượng

        //gán dl từ database vào view
        tvTitle.setText(note.getTitle());
        tvContent.setText(note.getContent());

        return customView;
    }
}
