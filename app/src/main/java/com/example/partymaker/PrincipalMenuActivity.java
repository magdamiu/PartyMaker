package com.example.partymaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        //de aici incepe implementarea fragmentelor pentru navigation menu
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        openFragment(new HomeFragment());

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    openFragment(new HomeFragment());
                    return true;

                case R.id.chat:
                    openFragment(new ChatFragment());
                    return true;

                case R.id.setting:
                    openFragment(new SettingsFragment());
                    return true;
            }
            return false;
        });
    }

    void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
