package com.example.snake;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.fingerprint.FingerprintManager;
import android.media.Image;
import android.os.Build;
import android.os.CancellationSignal;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;
    private CancellationSignal cancellationSignal;
    private boolean wasAuthenticated = false;

    private static FingerprintHandler single_instance = null;
    public static FingerprintHandler getInstance(Context context)
    {
        if (single_instance == null)
        {
            single_instance = new FingerprintHandler(context);
        }
        return single_instance;
    }

    public FingerprintHandler(Context context){
        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
        cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        System.exit(0);
    }

    @Override
    public void onAuthenticationFailed() {
        Log.d(TAG, "onAuthenticationFailed: ");
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        Log.d(TAG, "onAuthenticationHelp: ");
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        Log.d(TAG, "onAuthenticationSucceeded: ");
        wasAuthenticated = true;
    }

    public boolean getAuthenticated() {
        Log.d(TAG, "getAuthenticated: " + wasAuthenticated);
        return this.wasAuthenticated;
    }
}
