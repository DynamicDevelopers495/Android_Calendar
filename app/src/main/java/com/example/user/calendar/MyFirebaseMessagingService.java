package com.example.user.calendar;

import android.app.Service;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;


public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    public void onNewToken(String token) {
        Log.d("NEW_TOKEN", "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
    }
}
