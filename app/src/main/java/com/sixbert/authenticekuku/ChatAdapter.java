package com.sixbert.authenticekuku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private final String TAG = "ChatAdapter";
    Context context;
    List<ChatModel> chatModel;
    String userId;
    String postId;

    public ChatAdapter (Context context, List<ChatModel> chatModel, String  userID, String postId) {
        this.context = context;
        this.chatModel = chatModel;


    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //holder.chatId.setText(chatModel.get(position).getChatId());
        holder.commentText.setText(chatModel.get(position).getComment());
        holder.userName.setText(chatModel.get(position).getUserName());
        String postTime = chatModel.get(position).getPostTime();
        //holder.postTime.setText(chatModel.get(position).getPostTime());

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(postTime));

        String timeDate = DateFormat.getDateTimeInstance().format("dd/MM/yyyy hh:mm aa");

        holder.postTime.setText(timeDate);
        try {

            Glide.with(holder.itemView.getContext()).load(chatModel.get(position).getImageUrl()).into(holder.userProfile);


        }catch (Exception e){

        }
    }




    @Override
    public int getItemCount() {
        return chatModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView chatID, userName, postTime, commentText, likesCounter;
        ImageView userProfile;
        //Button likeButton;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentText = itemView.findViewById(R.id.commentText);
            userName = itemView.findViewById(R.id.userName);
            postTime=itemView.findViewById(R.id.commenttime);
            likesCounter = itemView.findViewById(R.id.likeCounter);
            //userProfile =itemView.findViewById(R.id.userPic);
            //likeButton = itemView.findViewById(R.id.likeBtn);
        }
    }
}


