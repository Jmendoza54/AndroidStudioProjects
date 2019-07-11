package com.alain.cursos.ofertas;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/* *
 * Inventario
 * Created by Alain Nicolás Tello on 07/09/2018 at 05:34pm
 * All rights reserved 2018.
 * Course Specialize in Firebase for Android 2018 with MVP
 * More info: https://www.udemy.com/especialidad-en-firebase-para-android-con-mvp-profesional/
 */

public class FcmIdService extends FirebaseInstanceIdService {
    public FcmIdService() {
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        Log.d("Token", token);
    }
}
