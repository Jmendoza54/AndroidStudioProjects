package com.example.realm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.realm.R;
import com.example.realm.models.Board;
import com.example.realm.models.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private List<Note> list;
    private int layout;

    public NoteAdapter(Context context, List<Note> notes, int layout){
        this.context = context;
        this.list = notes;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout, null);
            vh = new ViewHolder();
            vh.descripcion = convertView.findViewById(R.id.textViewNoteDes);
            vh.createdAt = convertView.findViewById(R.id.textViewNoteCreatedAt);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        Note note = list.get(position);
        vh.descripcion.setText(note.getDescription());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String createdAt = df.format(note.getCreatedAt());
        vh.createdAt.setText(createdAt);

        return convertView;
    }

    public class ViewHolder{
        TextView descripcion;
        TextView createdAt;
    }
}
