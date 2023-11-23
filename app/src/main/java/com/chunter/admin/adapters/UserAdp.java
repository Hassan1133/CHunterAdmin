package com.chunter.admin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chunter.admin.activities.UserDetailsActivity;
import com.chunter.admin.databinding.UsersRecyclerDesignBinding;
import com.chunter.admin.models.UserModel;

import java.util.List;

public class UserAdp extends RecyclerView.Adapter<UserAdp.Holder> {
    private UsersRecyclerDesignBinding usersRecyclerDesignBinding;
    private Context context;

    private List<UserModel> usersList;

    public UserAdp(Context context, List<UserModel> users) {
        this.context = context;
        usersList = users;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        usersRecyclerDesignBinding = UsersRecyclerDesignBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Holder(usersRecyclerDesignBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        UserModel userModel = usersList.get(position);
        holder.binding.userName.setText(userModel.getName());
        holder.binding.userEmail.setText(userModel.getEmail());
        Glide.with(context.getApplicationContext()).load(userModel.getPhoto()).into(holder.binding.userImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserDetailsActivity.class);
                intent.putExtra("userModel",userModel);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private UsersRecyclerDesignBinding binding;

        public Holder(UsersRecyclerDesignBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
