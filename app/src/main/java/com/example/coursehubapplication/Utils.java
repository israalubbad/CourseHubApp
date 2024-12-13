package com.example.coursehubapplication;

import android.text.InputType;
import android.widget.EditText;

public class Utils {
    public static boolean showPassword(boolean isPasswordViseble, EditText et){
        if(isPasswordViseble) {
            et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            isPasswordViseble=false;
        }else{
            et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            isPasswordViseble=true;
        }

        et.setSelection(et.length());
        return isPasswordViseble;
    }
}
