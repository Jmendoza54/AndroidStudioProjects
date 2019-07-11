package com.jmendoza.inventario.common;

public interface BasicErrorEventCallback {
    void onSuccess();
    void onError(int typeEvent, int resMsg);
}
