package com.example.partymaker;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class CustomGoogleSignIn {
    public static GoogleSignInAccount getGoogleSignInAccount(Context context) {
        return GoogleSignIn.getLastSignedInAccount(context);
    }

    public static GoogleSignInClient getGoogleSignClient(Context context) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        return GoogleSignIn.getClient(context, gso);
    }
}

