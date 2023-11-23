package com.chunter.admin.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chunter.admin.R;
import com.chunter.admin.databinding.ActivityMainBinding;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    // method for initialize widgets and variables
    private void init() {

        binding.logoutBtn.setOnClickListener(this);

        setCurrentDateToTextView();

        binding.users.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logoutBtn:
                createLogoutDialog();
                break;
            case R.id.users:
                goToUsersActivity();
        }
    }

    // method for go to users activity
    private void goToUsersActivity() {
        startActivity(new Intent(MainActivity.this, UsersActivity.class));
    }

    // method for create logout dialog
    private void createLogoutDialog() {
        new MaterialAlertDialogBuilder(MainActivity.this)
                .setTitle("Confirm Logout")
                .setMessage("Are you sure you want to log out?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Cancel button clicked, do nothing or handle accordingly
                        dialogInterface.dismiss(); // Close the dialog
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Ok button clicked, call your logout function here
                        logout();
                    }
                })
                .show();
    }

    // method for logout
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, AdminLoginActivity.class));
        finish();
    }

    // method for get current date and set on textView
    private void setCurrentDateToTextView() {
        Date currentDate = new Date();

        // Define the desired date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");

        // Format the date and store it in a String
        String formattedDate = dateFormat.format(currentDate);

        binding.date.setText(formattedDate);
    }
}