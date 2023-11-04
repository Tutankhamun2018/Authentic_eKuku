package com.sixbert.authenticekuku;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Context context;
    List<PostModel> postModel;
    final String uid;
    boolean mprocesslike;
    String myuid;
    private final DatabaseReference likeRef;
    private final DatabaseReference postRef;
    final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


    {
        assert currentUser != null;
        uid = currentUser.getUid();

    }


    public PostAdapter (Context context, List<PostModel> postModel){
        this.postModel = postModel;
        this.context = context;

        myuid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        likeRef = FirebaseDatabase.getInstance().getReference().child("Likes");
        postRef = FirebaseDatabase.getInstance().getReference().child("Posts");

    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostAdapter.ViewHolder holder, int position) {
        final String ptime = postModel.get(position).getNow();
        String pLike = postModel.get(position).getLikeCounter();
        String comm = postModel.get(position).getCommentCounter();
        holder.uname.setText(postModel.get(position).getUname());
        holder.post.setText(postModel.get(position).getPost());
        holder.likeCounter.setText(String.valueOf(pLike));
        holder.commentCounter.setText(String.valueOf(comm));

        setLikes(holder, ptime);
      long currentTime = System.currentTimeMillis();
      long serverTme = Long.parseLong(postModel.get(position).getNow());
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



        final String pid = postModel.get(position).getNow();
        String image = postModel.get(position).getImageUrl();
        if (image!=null ) {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.imageView.requestLayout();
            Glide.with(holder.itemView.getContext()).load(postModel.get(position).getImageUrl()).into(holder.imageView);
        } else {
            holder.imageView.setVisibility(View.GONE);
            holder.imageView.requestLayout();
        }


        Glide.with(holder.itemView.getContext()).load(postModel.get(position).getUdp()).into(holder.udp);//}

        holder.likePostBtn.setOnClickListener(v -> {
            final int plike = Integer.parseInt(postModel.get(position).getLikeCounter());
            mprocesslike = true;
            final String postid = postModel.get(position).getNow();

            Log.d("POSTID", "PostID" +postid);
            likeRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (mprocesslike) {
                        if (snapshot.child(postid).hasChild(myuid)) {//replaced ptime variable with myud.. crashes, name and dp fail
                            postRef.child(myuid).child(postid).child("likeCounter").setValue("" + (plike-1));
                            likeRef.child(postid).child(myuid).removeValue();
                            mprocesslike = false;
                        } else{
                            postRef.child(myuid).child(postid).child("likeCounter").setValue(""+ (plike +1));
                            likeRef.child(postid).child(myuid).setValue("Liked");
                            mprocesslike = false;
                        }
                    }
                    Log.d("POSTID", "PostID" +postid);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("Posts").child(myuid);
        Query query = dbReference.orderByChild("now").equalTo(pid);//.orderByChild("postUid").equalTo(postUid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        String commentcount = Objects.requireNonNull(dataSnapshot1.child("commentCounter").getValue()).toString();
                        holder.commentCounter.setText(commentcount);

                    }
                }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       //holder.more.setOnClickListener(v -> showMoreOptions(holder.more, myuid, ptime, image));

        holder.commentPostBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, CommentsActivity.class);
            intent.putExtra("pid", pid);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        holder.post.setOnClickListener(v -> {
            Intent postIntent = new Intent(context, PostDetailsActivity.class);
            postIntent.putExtra("pid", pid);
            postIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(postIntent);
        });

    }
/*
    private void  showMoreOptions(ImageView more, String postUid, final String pid,
                                  final String image){
        //FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();
        PopupMenu popupMenu = new PopupMenu(context, more, Gravity.END);
        if (uid.equals(myuid)){
            popupMenu.getMenu().add(Menu.NONE, 0,0, "FUTA");
        }
        popupMenu.setOnMenuItemClickListener(item -> {
            if(item.getItemId() ==0){

            }
            return false;
        });
        popupMenu.show();
    }*/

    private void setLikes(final  ViewHolder holder, final String pid){
        likeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(pid).hasChild(myuid)) {
                    holder.likePostBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.like_outlined, 0,0,0);
                    //holder.likePostBtn.setText(R.string.liked);
                } else{
                    holder.likePostBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.like_outlined_orange, 0,0,0);
                    //holder.likePostBtn.setText(R.string.like);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return postModel.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final ImageView udp;
        final TextView  now;
        final TextView likeCounter;
        final TextView uname;
        final TextView commentCounter;
        final TextView post;
        final Button likePostBtn;
        final Button commentPostBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.post_imageView);
            udp = itemView.findViewById(R.id.profileImage);
            uname = itemView.findViewById(R.id.userIdTv);
            now = itemView.findViewById(R.id.timeStampTv);
            post = itemView.findViewById(R.id.postTV);
            likePostBtn  = itemView.findViewById(R.id.likePostBtn);
            commentPostBtn = itemView.findViewById(R.id.commentPostBtn);
            likeCounter = itemView.findViewById(R.id.likeCounterTV);
            commentCounter = itemView.findViewById(R.id.commentCounterTv);


        }
    }
}




