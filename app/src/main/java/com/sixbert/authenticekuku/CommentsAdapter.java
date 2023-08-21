package com.sixbert.authenticekuku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    Context context;
    List<CommentsModel> commentsModel, postId, uId;


    private DatabaseReference likeRef, postRef;

    public CommentsAdapter (Context context, List<CommentsModel> commentsModel) {
        this.commentsModel = commentsModel;
        this.context = context;
        //uId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        likeRef = FirebaseDatabase.getInstance().getReference().child("Likes");
        postRef = FirebaseDatabase.getInstance().getReference().child("Posts");
    }

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_comments, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int position) {
        holder.commentUid.setText(commentsModel.get(position).getUid());
        //holder.imageUrl.setText(postModel.get(position).getImageUrl());
        //holder.now.setText(postModel.get(position).getNow());
        holder.actualCommentTxt.setText(commentsModel.get(position).getActualComment());
        //holder.likeCounter.setText(postModel.get(position).getLikeCounter());
        //holder.commentCounter.setText(postModel.get(position).getCommentCounter());

        long currentTime = System.currentTimeMillis();
        long serverTme = Long.parseLong(commentsModel.get(position).getCommentTime());

        long elapsedTime = currentTime - serverTme;
        long seconds = elapsedTime / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        String timeElapsed;
        if (days > 0) {
            timeElapsed = days + " days ago";
        } else if (hours > 0) {
            timeElapsed = hours + " hrs ago";
        } else if (minutes > 0) {
            timeElapsed = minutes + " mins ago";
        } else {
            timeElapsed = seconds + " secs ago";
        }
        holder.commentTime.setText(timeElapsed);


        String pid = commentsModel.get(position).getCommentTime();


    }

    @Override
    public int getItemCount() {
        return commentsModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView commentUid, commentTime, actualCommentTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            commentUid = itemView.findViewById(R.id.commentUid);
            commentTime = itemView.findViewById(R.id.commentTime);
            actualCommentTxt = itemView.findViewById(R.id.actualCommentTxt);

        }
    }
}
