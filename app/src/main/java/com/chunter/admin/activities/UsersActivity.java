package com.chunter.admin.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chunter.admin.adapters.UserAdp;
import com.chunter.admin.databinding.ActivityUsersBinding;
import com.chunter.admin.main_utils.LoadingDialog;
import com.chunter.admin.models.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    private ActivityUsersBinding binding;
    private FirebaseFirestore fireStoreDb;
    private ArrayList<UserModel> users;
    private UserAdp adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    // method for initialize widgets and variables
    private void init() {
        fireStoreDb = FirebaseFirestore.getInstance();

        users = new ArrayList<>();

        binding.usersRecycler.setLayoutManager(new LinearLayoutManager(this));

        fetchUsers();
    }

    // fetch users from the FirebaseFireStore
    private void fetchUsers() {
        LoadingDialog.showLoadingDialog(UsersActivity.this);
        fireStoreDb.collection("users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                users.clear();

               if(error != null)
               {
                   LoadingDialog.hideLoadingDialog();
                   Toast.makeText(UsersActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
               }
               else
               {
                   if (value.isEmpty()) {
                       Toast.makeText(UsersActivity.this, "no data found", Toast.LENGTH_SHORT).show();
                       LoadingDialog.hideLoadingDialog();
                       return;
                   } else {
                       List<UserModel> usersList = value.toObjects(UserModel.class);
                       users.addAll(usersList);
                       setDataToRecycler(users);
                       LoadingDialog.hideLoadingDialog();
                   }
               }
            }
        });
    }

    private void setDataToRecycler(ArrayList<UserModel> users) {
        adp = new UserAdp(UsersActivity.this, users);
        binding.usersRecycler.setAdapter(adp);
    }
}