package com.jmendoza.inventario.common.model.dataAccess;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseFirestoreAPI {

    private static final String COLL_PRODUCTS = "products";
    private FirebaseFirestore mFirestore;

    private static FirebaseFirestoreAPI INSTANCE = null;

    private FirebaseFirestoreAPI() {
        mFirestore = FirebaseFirestore.getInstance();
    }

    public static FirebaseFirestoreAPI getInstance(){
        if(INSTANCE == null){
            INSTANCE = new FirebaseFirestoreAPI();
        }
        return INSTANCE;
    }

    //Referencias

    public CollectionReference getProductsReference(){
        return mFirestore.collection(COLL_PRODUCTS);
    }
}
