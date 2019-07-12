package com.alain.cursos.inventario.common.model.dataAccess;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Inventario
 * Created by Alain Nicolás Tello on 06/09/2018 at 05:34pm
 * All rights reserved 2019.
 * Course Specialize in Firebase for Android 2019 with MVP
 * More info: https://www.udemy.com/especialidad-en-firebase-para-android-con-mvp-profesional/
 */

public class FirebaseFirestoreAPI {
    private static final String COLL_PRODUCTS = "products";

    private FirebaseFirestore mFirestore;

    private static FirebaseFirestoreAPI INSTANCE = null;

    private FirebaseFirestoreAPI() {
        mFirestore = FirebaseFirestore.getInstance();
    }

    public static FirebaseFirestoreAPI getInstance(){
        if (INSTANCE == null){
            INSTANCE = new FirebaseFirestoreAPI();
        }
        return INSTANCE;
    }

    public FirebaseFirestore getmFirestore() {
        return mFirestore;
    }

    // Referencias
    public CollectionReference getProductsReference(){
        return mFirestore.collection(COLL_PRODUCTS);
    }
}
