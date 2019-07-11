package com.example.section_03_recyclerview.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.section_03_recyclerview.R;
import com.example.section_03_recyclerview.models.Development;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Development> develops;
    private int layout;
    private OnItemClickListener itemClickListener;

    private Context context;

    public MyAdapter(List<Development> develops, int layout, OnItemClickListener listener){
        this.develops = develops;
        this.layout = layout;
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        context = viewGroup.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(develops.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return develops.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewName;
        public ImageView imageViewBanner;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewTitle);
            imageViewBanner = (ImageView) itemView.findViewById(R.id.imageViewBanner);

        }

        public void bind(final Development develop, final OnItemClickListener listener){

            textViewName.setText(develop.getName());
            Picasso.get().load(develop.getBanner()).fit().into(imageViewBanner);
            //imageViewBanner.setImageResource(develop.getBanner());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(develop, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(Development develop, int position);
    }

}
