package com.chunter.admin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chunter.admin.R;
import com.chunter.admin.databinding.ActivityAdminLoginBinding;
import com.chunter.admin.main_utils.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAdminLoginBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    // method for initialize widgets and variables
    private void init()
    {
        binding.loginBtn.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                if (isValid()) {
                    LoadingDialog.showLoadingDialog(AdminLoginActivity.this);
                    signIn(binding.email.getText().toString().trim(), binding.password.getText().toString().trim());
                }
                break;
        }
    }

    // method for validation of textFields
    private boolean isValid() {
        boolean valid = true;

        if (!Patterns.EMAIL_ADDRESS.matcher(binding.email.getText()).matches()) {
            binding.email.setError("Please enter valid email");
            valid = false;
        }
        if (binding.password.getText().length() < 6) {
            binding.password.setError("Please enter valid password");
            valid = false;
        }

        return valid;
    }

    // method for login to Firebase
    private void signIn(String emailText, String passwordText) {
        firebaseAuth.signInWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    goToMainActivity();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                    LoadingDialog.hideLoadingDialog();
                    Toast.makeText(AdminLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // method for go to main activity after successful login
    private void goToMainActivity() {
        Intent intent = new Intent(AdminLoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}