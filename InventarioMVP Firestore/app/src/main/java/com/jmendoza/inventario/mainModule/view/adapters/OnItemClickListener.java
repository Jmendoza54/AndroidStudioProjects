package com.jmendoza.inventario.mainModule.view.adapters;

import com.jmendoza.inventario.common.pojo.Product;

public interface OnItemClickListener {
    void onItemClick(Product product);
    void onLongItemClick(Product product);
}
