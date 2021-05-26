package com.example.demoroomdb.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demoroomdb.model.Common.Logger.LoggerManager;
import com.example.demoroomdb.model.Common.SharePreference.ConfigSharedPref;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class ReceiveMessageService extends FirebaseMessagingService {
    ConfigSharedPref sharedPref;
    private static final String TAG = ReceiveMessageService.class.getSimpleName();
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);

    }
}
