package com.alain.cursos.inventario.common.utils;

import android.content.Context;
import android.widget.EditText;

import com.alain.cursos.inventario.R;

/**
 * Inventario
 * Created by Alain Nicolás Tello on 06/09/2018 at 05:34pm
 * All rights reserved 2018.
 * Course Specialize in Firebase for Android 2018 with MVP
 * More info: https://www.udemy.com/especialidad-en-firebase-para-android-con-mvp-profesional/
 */

public class CommonUtils {

    public static boolean validateProduct(Context context, EditText etName, EditText etQuantity,
                                          EditText etPhotoUrl){
        boolean isValid = true;

        if (etQuantity.getText().toString().trim().isEmpty()){
            etQuantity.setError(context.getString(R.string.common_validate_field_required));
            etQuantity.requestFocus();
            isValid = false;
        } else if (Integer.valueOf(etQuantity.getText().toString().trim()) <= 0){
            etQuantity.setError(context.getString(R.string.common_validate_min_quantity));
            etQuantity.requestFocus();
            isValid = false;
        }

        if (etPhotoUrl.getText().toString().trim().isEmpty()){
            etPhotoUrl.setError(context.getString(R.string.common_validate_field_required));
            etPhotoUrl.requestFocus();
            isValid = false;
        }

        if (etName.getText().toString().trim().isEmpty()){
            etName.setError(context.getString(R.string.common_validate_field_required));
            etName.requestFocus();
            isValid = false;
        }

        return isValid;
    }
}
