package com.sixbert.authenticekuku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class BlogUsersAdapter extends RecyclerView.Adapter<BlogUsersAdapter.MyHolder> {

    Context context;
    FirebaseAuth firebaseAuth;
    String uid;
    List<BlogUsersModel> blogUsersModel;

    public BlogUsersAdapter(Context context, List<BlogUsersModel> blogUsersModel) {
        this.context = context;
        this.blogUsersModel = blogUsersModel;
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getUid();
    }



    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_blog_users, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final String hisuid = blogUsersModel.get(position).getUid();
        String userImage = blogUsersModel.get(position).getProfileImage();
        String username = blogUsersModel.get(position).getName();
        String usermail = blogUsersModel.get(position).getEmail();
        holder.name.setText(username);
        holder.email.setText(usermail);
        try {
            Glide.with(context).load(userImage).into(holder.profileImage);
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return blogUsersModel.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        ShapeableImageView profileImage;
        TextView name, email;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            name = itemView.findViewById(R.id.namep);
            email = itemView.findViewById(R.id.emailp);
        }
    }
}
