package com.alain.cursos.inventario.detailModule.model.dataAccess;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alain.cursos.inventario.common.BasicEventCallback;
import com.alain.cursos.inventario.common.model.dataAccess.FirebaseFirestoreAPI;
import com.alain.cursos.inventario.common.pojo.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.Map;

/**
 * Inventario
 * Created by Alain Nicolás Tello on 06/09/2018 at 05:34pm
 * All rights reserved 2018.
 * Course Specialize in Firebase for Android 2018 with MVP
 * More info: https://www.udemy.com/especialidad-en-firebase-para-android-con-mvp-profesional/
 */

public class Firestore {
    private FirebaseFirestoreAPI mFirestoreAPI;

    public Firestore() {
        mFirestoreAPI = FirebaseFirestoreAPI.getInstance();
    }

    public void updateProduct(final Product product, final BasicEventCallback callback){
        final DocumentReference productReference = mFirestoreAPI.getProductsReference().document(product.getId());
        mFirestoreAPI.getmFirestore().runTransaction(new Transaction.Function<Void>() {
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(productReference);

                double currentScore = 0.0;
                long currentTotalVotes = 0;
                Product currentProduct = snapshot.toObject(Product.class);
                if (currentProduct != null){
                    currentScore = currentProduct.getScore();
                    currentTotalVotes = currentProduct.getTotalVotes();
                }

                long newTotalVotes = currentTotalVotes + 1;
                double newScore = ( (currentScore * currentTotalVotes) + product.getScore() ) /
                        newTotalVotes;

                Map<String, Object> updates = new HashMap<>();
                updates.put(Product.NAME, product.getName());
                updates.put(Product.QUANTITY, product.getQuantity());
                updates.put(Product.PHOTO_URL, product.getPhotoUrl());
                updates.put(Product.SCORE, newScore);
                updates.put(Product.TOTAL_VOTES, newTotalVotes);

                transaction.update(productReference, updates);

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.onSuccess();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError();
            }
        });;
        /*mFirestoreAPI.getProductsReference().document(product.getId())
                .set(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onError();
                    }
                });*/
    }
}
