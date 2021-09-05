package com.example.demoroomdb.utils;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Utils {
    private Context mContext;

    private static Utils utils;

    DatabaseReference reference;

    FirebaseUser firebaseUser;

    public Utils(Context context) {
        this.mContext = context;
    }

    public static Utils getInstance(Context context) {
        if (utils == null) {
            utils = new Utils(context);
        }
        return utils;
    }
    public void getHeaderName ( String name) {

    }
}
