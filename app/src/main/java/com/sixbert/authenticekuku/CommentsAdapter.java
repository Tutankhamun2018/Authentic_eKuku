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
import java.util.List;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

     final String postID;
    final Context context;
    final List<CommentsModel> commentsModel;

    final String myuid;


    public CommentsAdapter (Context context, List<CommentsModel> commentsModel, String myuid, String postID) {
        this.commentsModel = commentsModel;
        this.context = context;
        this.myuid = myuid;
        this.postID = postID;

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
            timeElapsed = minutes + " mns ago";
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
        final TextView uname;
        final TextView now;
        final TextView comment;

        final ImageView udp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            uname = itemView.findViewById(R.id.commenterName);
            now = itemView.findViewById(R.id.commentTime);
            comment = itemView.findViewById(R.id.actualCommentTxt);
            udp = itemView.findViewById(R.id.commprofileImage);

        }
    }
}
