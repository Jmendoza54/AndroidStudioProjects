package com.example.reciclerview;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.reciclerview.RestaurantFragment.OnListFragmentInteractionListener;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyRestaurantRecyclerViewAdapter extends RecyclerView.Adapter<MyRestaurantRecyclerViewAdapter.ViewHolder> {

    private final List<Restaurant> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyRestaurantRecyclerViewAdapter(List<Restaurant> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //REscatamos los datos del elemento que ocupa la posicion de position
        holder.mItem = mValues.get(position);
        holder.name.setText(holder.mItem.getName());
        holder.address.setText(holder.mItem.getAddress());
        Picasso.get().load(holder.mItem.getUrlPhoto()).resize(450,200).centerCrop().into(holder.photo);
        holder.ratingBar.setRating(holder.mItem.getRate());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name;
        public final TextView address;
        public final ImageView photo;
        public final RatingBar ratingBar;
        public Restaurant mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = view.findViewById(R.id.textViewName);
            address = view.findViewById(R.id.textViewAddress);
            photo = view.findViewById(R.id.imageViewPhoto);
            ratingBar = view.findViewById(R.id.ratingBar);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + name.getText() + "'";
        }
    }
}
