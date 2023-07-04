/*package com.sixbert.authenticekuku;

import android.content.Context;
import android.os.Message;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.fido.fido2.api.common.AuthenticationExtensions;
import com.google.android.gms.fido.fido2.api.common.RequestOptions;
import com.google.android.gms.fido.fido2.api.common.TokenBinding;
import com.google.firebase.firestore.Query;

import java.text.DateFormat;
import java.util.Date;

public class ChatAdapter extends FirestoreRecyclerAdapter<InAppChat, ChatAdapter.MessageHolder> {

    private final String TAG = "ChatAdapter";
    Context context;
    String userID;

    public ChatAdapter (Context context, Query query, String  userID){
        super(new FirestoreRecyclerOptions.Builder<InAppChat>()
                .setQuery(query, InAppChat.class)
                .build());

    }
    //StorageReference storageReference;
    private RequestOptions requestOptions = new RequestOptions();

    /*{
        @Override
        public void writeToParcel(@NonNull Parcel parcel, int i) {

        }

        @Nullable
        @Override
        public AuthenticationExtensions getAuthenticationExtensions() {
            return null;
        }

        @Nullable
        @Override
        public TokenBinding getTokenBinding() {
            return null;
        }

        @Nullable
        @Override
        public Double getTimeoutSeconds() {
            return null;
        }

        @Nullable
        @Override
        public Integer getRequestId() {
            return null;
        }

        @NonNull
        @Override
        public byte[] getChallenge() {
            return new byte[0];
        }
    };
    private final int MESSAGE_IN_VIEW_TYPE = 1;
    private final int MESSAGE_OUT_VIEW_TYPE = 2;

    /**
     //* Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     //* FirestoreRecyclerOptions} for configuration options.
     //*
     //* @param options
     //*/
    /*public ChatAdapter(@NonNull FirestoreRecyclerOptions<InAppChat> options) {
        super(options);
    }


    public int getItemViewTyipe(int position){
            if (getItem(position).getMessageUserID().equals(userID)){
                return MESSAGE_OUT_VIEW_TYPE;
            }
                return MESSAGE_IN_VIEW_TYPE;
            }
    @NonNull
    @Override
    public ChatAdapter.MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;

        if(viewType ==MESSAGE_IN_VIEW_TYPE ){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_in, parent,false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_out, parent,false);
        }
        return new MessageHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatAdapter.MessageHolder holder, int position, @NonNull InAppChat model) {
        final TextView mText = holder.mText;
        final TextView userName = holder.userName;
        final TextView timeStamp = holder.timeStamp;
        final TextView likesCounter = holder.likesCounter;
        final ImageView userProfile = holder.userProfile;
        final Button likeButton = holder.likeButton;

        mText.setText(model.getMessageText());
        userName.setText(model.getMessageUser());
        timeStamp.setText(DateFormat.format("dd MMM(h:mm a)"), model.getTimeStamp());
        likesCounter.setText(String.valueOf(model.getLikesCounter()));

        if(model.getLikesCounter()!=null){

        }



    }
    public static class MessageHolder extends RecyclerView.ViewHolder{

        TextView mText, userName, timeStamp, likesCounter;
        ImageView userProfile;
        Button likeButton;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);

            mText = itemView.findViewById(R.id.chatText);
            userName = itemView.findViewById(R.id.userName);
            timeStamp=itemView.findViewById(R.id.timeStamp);
            likesCounter = itemView.findViewById(R.id.likeCounter);
            userProfile =itemView.findViewById(R.id.userPic);
            likeButton = itemView.findViewById(R.id.likeBtn);
        }
    }
}*/
