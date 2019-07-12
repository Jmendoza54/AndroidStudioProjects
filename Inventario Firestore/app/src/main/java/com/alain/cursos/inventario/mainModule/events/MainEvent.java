package com.alain.cursos.inventario.mainModule.events;

import com.alain.cursos.inventario.common.pojo.Product;

/* *
 * Inventario
 * Created by Alain Nicolás Tello on 06/09/2018 at 05:34pm
 * All rights reserved 2018.
 * Course Specialize in Firebase for Android 2018 with MVP
 * More info: https://www.udemy.com/especialidad-en-firebase-para-android-con-mvp-profesional/
 */

public class MainEvent {
    public static final  int SUCCESS_ADD = 0;
    public static final  int SUCCESS_UPDATE = 1;
    public static final  int SUCCESS_REMOVE = 2;
    public static final  int ERROR_SERVER = 100;
    public static final  int ERROR_TO_REMOVE = 101;

    private Product product;
    private int typeEvent;
    private int resMsg;

    public MainEvent() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }

    public int getResMsg() {
        return resMsg;
    }

    public void setResMsg(int resMsg) {
        this.resMsg = resMsg;
    }
}
