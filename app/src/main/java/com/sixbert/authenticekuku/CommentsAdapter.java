package com.sixbert.authenticekuku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    Context context;
    List<CommentsModel> commentsModel;

    String myuid;

    private final DatabaseReference commentRef;



    public CommentsAdapter (Context context, List<CommentsModel> commentsModel) {
        this.commentsModel = commentsModel;
        this.context = context;

        commentRef = FirebaseDatabase.getInstance().getReference().child("Comments");
           }

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_comments, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsAdapter.ViewHolder holder, int position) {
        holder.uname.setText(commentsModel.get(position).getUname());
        holder.comment.setText(commentsModel.get(position).getComment());
        //holder.cid.setText(commentsModel.get(position).getCId());
        long currentTime = System.currentTimeMillis();
        long serverTme = Long.parseLong(commentsModel.get(position).getNow());

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
        holder.now.setText(timeElapsed);


        Glide.with(holder.itemView.getContext()).load(commentsModel.get(position).getUdp()).into(holder.udp);


    }

    @Override
    public int getItemCount() {
        return commentsModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView uname, now, comment;

        ImageView udp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            uname = itemView.findViewById(R.id.commenterName);
            now = itemView.findViewById(R.id.commentTime);
            comment = itemView.findViewById(R.id.actualCommentTxt);
            udp = itemView.findViewById(R.id.commprofileImage);
            //cid = itemView.findViewById(R.id.actualCommentTxt);

        }
    }
}
