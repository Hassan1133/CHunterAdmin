package com.chunter.admin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.chunter.admin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        goToNextActivity();

    }

    // method for go To next activity after splash activity has finished
    //  if user already login then the MainActivity will shown to user and if not then the login Activity will shown to user
    private void goToNextActivity() {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;

                if (firebaseUser != null) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, AdminLoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}