package com.example.responsiveui.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.responsiveui.NewNoteDialogViewModel;
import com.example.responsiveui.db.entity.NoteEntity;
import com.example.responsiveui.R;

import java.util.List;


public class MyNoteRecyclerViewAdapter extends RecyclerView.Adapter<MyNoteRecyclerViewAdapter.ViewHolder> {

    private List<NoteEntity> mValues;
    private Context ctx;
    private NewNoteDialogViewModel viewModel;

    public MyNoteRecyclerViewAdapter(List<NoteEntity> items, Context ctx) {
        mValues = items;
        this.ctx = ctx;
        viewModel = ViewModelProviders.of((AppCompatActivity) ctx).get(NewNoteDialogViewModel.class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvTitle.setText(holder.mItem.getTitle());
        holder.tvCont.setText(holder.mItem.getContenido());

        if(holder.mItem.isFavorite()){
            holder.ivFav.setImageResource(R.drawable.ic_star_black_24dp);
        }

        holder.ivFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.mItem.isFavorite()){
                    holder.mItem.setFavorite(false);
                    holder.ivFav.setImageResource(R.drawable.ic_star_border_black_24dp);
                }else{
                    holder.mItem.setFavorite(true);
                    holder.ivFav.setImageResource(R.drawable.ic_star_black_24dp);

                }

                viewModel.updateNote(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setNewNotes(List<NoteEntity> newNotes){
        this.mValues = newNotes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvTitle;
        public final TextView tvCont;
        public final ImageView ivFav;
        public NoteEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvTitle = view.findViewById(R.id.textViewTitle);
            tvCont = view.findViewById(R.id.textViewCont);
            ivFav = view.findViewById(R.id.imageViewFavorite);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvTitle.getText() + "'";
        }
    }
}
