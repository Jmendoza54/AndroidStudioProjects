package com.jmendoza.inventario.mainModule;

import com.jmendoza.inventario.common.pojo.Product;
import com.jmendoza.inventario.mainModule.events.MainEvent;

public interface MainPresenter {

    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();

    void remove(Product product);

    void onEventListener(MainEvent event);

}
