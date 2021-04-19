package com.example.partymaker;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PrincipalMenuActivity extends AppCompatActivity {

    private Button googleSignOut;
    private TextView username;
    private ImageView profilePicture;

    private GoogleSignInAccount googleSignInAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricipal_menu);

        googleSignInAccount = CustomGoogleSignIn.getGoogleSignInAccount(PrincipalMenuActivity.this);
        username = findViewById(R.id.username);
        profilePicture = findViewById(R.id.profilePicture);

        if (googleSignInAccount != null) {
            String personName = googleSignInAccount.getDisplayName();
            String personGivenName = googleSignInAccount.getGivenName();
            String personFamilyName = googleSignInAccount.getFamilyName();
            Uri personPicture = googleSignInAccount.getPhotoUrl();

            username.setText("Name: " + personName);
            Glide.with(this).load(personPicture).into(profilePicture);
        }

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
}
