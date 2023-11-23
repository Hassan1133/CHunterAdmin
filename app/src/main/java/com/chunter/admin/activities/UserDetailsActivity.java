package com.chunter.admin.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.chunter.admin.R;
import com.chunter.admin.databinding.ActivityUserDetailsBinding;
import com.chunter.admin.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityUserDetailsBinding binding;
    private UserModel userModel;

    private FirebaseFirestore fireStoreDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    // method for initialization of widgets and variables
    private void init() {
        getDataFromIntent();
        fireStoreDb = FirebaseFirestore.getInstance();
        binding.blockBtn.setOnClickListener(this);
    }

    // method for get model data and to the fields
    private void getDataFromIntent() {
        userModel = (UserModel) getIntent().getSerializableExtra("userModel");
        Glide.with(getApplicationContext()).load(userModel.getPhoto()).into(binding.userProfileImg);
        binding.userName.setText(userModel.getName());
        binding.userEmail.setText(userModel.getEmail());

        if (userModel.isBlock()) {
            binding.blockBtn.setText("Unblock");
        } else {
            binding.blockBtn.setText("Block");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.blockBtn:
                blockOrUnblockUser();
                break;
        }
    }

    private void blockOrUnblockUser() {
        if (userModel.isBlock()) {
            userModel.setBlock(false);
            fireStoreDb.collection("users").document(userModel.getUid()).update("block", userModel.isBlock()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(UserDetailsActivity.this, "User Unblocked", Toast.LENGTH_SHORT).show();
                        binding.blockBtn.setText("Block");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UserDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            userModel.setBlock(true);
            fireStoreDb.collection("users").document(userModel.getUid()).update("block", userModel.isBlock()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(UserDetailsActivity.this, "User Blocked", Toast.LENGTH_SHORT).show();
                        binding.blockBtn.setText("Unblock");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UserDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}