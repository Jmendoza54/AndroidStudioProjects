package com.jmendoza.inventario.common.utils;

import android.content.Context;
import android.widget.EditText;

import com.jmendoza.inventario.R;

public class CommonUtils {

    public static boolean validateProduct(Context context, EditText etName, EditText etQuantity, EditText etPhotoUrl){

        boolean isValid = true;

        if(etQuantity.getText().toString().trim().isEmpty()){
            etQuantity.setError(context.getString(R.string.common_validate_field_requiered));
            etQuantity.requestFocus();
            isValid = false;
        }else if(Integer.valueOf(etQuantity.getText().toString().trim()) <= 0){
            etQuantity.setError(context.getString(R.string.common_validate_min_quantity));
            etQuantity.requestFocus();
            isValid = false;
        }

        if(etPhotoUrl.getText().toString().trim().isEmpty()){
            etPhotoUrl.setError(context.getString(R.string.common_validate_field_requiered));
            etPhotoUrl.requestFocus();
            isValid = false;
        }

        if(etName.getText().toString().trim().isEmpty()){
            etName.setError(context.getString(R.string.common_validate_field_requiered));
            etName.requestFocus();
            isValid = false;
        }

        return isValid;
    }
}
