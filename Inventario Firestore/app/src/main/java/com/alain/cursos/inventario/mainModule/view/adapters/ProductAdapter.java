package com.alain.cursos.inventario.mainModule.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alain.cursos.inventario.common.pojo.Product;
import com.alain.cursos.inventario.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/* *
 * Inventario
 * Created by Alain Nicolás Tello on 06/09/2018 at 05:34pm
 * All rights reserved 2018.
 * Course Specialize in Firebase for Android 2018 with MVP
 * More info: https://www.udemy.com/especialidad-en-firebase-para-android-con-mvp-profesional/
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> products;
    private OnItemClickListener listener;
    private Context context;

    public ProductAdapter(List<Product> products, OnItemClickListener listener) {
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        context = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);

        holder.setOnClickListener(product, listener);

        holder.tvData.setText(context.getString(R.string.item_product_data, product.getName(),
                product.getQuantity()));

        holder.tvScore.setText(String.format(Locale.ROOT, "%.2f", product.getScore()));

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

        Glide.with(context)
                .load(product.getPhotoUrl())
                .apply(options)
                .into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void add(Product product){
        if (!products.contains(product)){
            products.add(product);
            notifyItemInserted(products.size()-1);
        } else {
            update(product);
        }
    }

    public void update(Product product) {
        if (products.contains(product)){
            final int index = products.indexOf(product);
            products.set(index, product);
            notifyItemChanged(index);
        }
    }

    public void remove(Product product){
        if (products.contains(product)){
            final int index = products.indexOf(product);
            products.remove(index);
            notifyItemRemoved(index);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imgPhoto)
        AppCompatImageView imgPhoto;
        @BindView(R.id.tvData)
        AppCompatTextView tvData;
        @BindView(R.id.tvScore)
        AppCompatTextView tvScore;

        private View view;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }

        void setOnClickListener(final Product product, final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(product);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onLongItemClick(product);
                    return true;
                }
            });
        }
    }
}
