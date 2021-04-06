package com.example.partymaker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class PrincipalMenuActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    Button googleSignOut;
    TextView username;
    ImageView profilePicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricipal_menu);

        username = findViewById(R.id.username);
        profilePicture = findViewById(R.id.profilePicture);
        googleSignOut = findViewById(R.id.button);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(PrincipalMenuActivity.this);

        if(acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            Uri personPicture = acct.getPhotoUrl();

            username.setText("Name: " + personName);
            Glide.with(this).load(personPicture).into(profilePicture);
        }


        googleSignOut.setOnClickListener((View -> {signOut(); }));
    }

    @SuppressLint("ShowToast")
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, (task) -> {
                    Toast.makeText(PrincipalMenuActivity.this, "Successfully signed out!", Toast.LENGTH_SHORT);
                    startActivity(new Intent(PrincipalMenuActivity.this, MainActivity.class));
                    finish();
        });
    }
}
