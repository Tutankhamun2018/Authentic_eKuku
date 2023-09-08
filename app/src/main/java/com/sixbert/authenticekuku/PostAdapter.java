package com.sixbert.authenticekuku;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import android.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Context context;
    List<PostModel> postModel;
    String userId;
    boolean mprocesslike;
    String myuid;
    private final DatabaseReference likeRef;
    private final DatabaseReference postRef;



    public PostAdapter (Context context, List<PostModel> postModel){
        this.postModel = postModel;
        this.context = context;
        //firebaseUser = firebaseAuth.getCurrentUser();
        //userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        List<PostModel> postModelList;


        //userId = firebaseAuth.getUid();
        myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
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
        String image = postModel.get(position).getImageUrl(); //holder.name.setText(postModel.get(position).getName());
        final String ptime = postModel.get(position).getNow();
        String pLike = postModel.get(position).getLikeCounter();
        String comm = postModel.get(position).getCommentCounter();
        //holder.profileOrCoverPhoto.setText(postModel.get(position).getImageUrl());
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


        final String pid = String.valueOf(postModel.get(position).getNow());


        Glide.with(holder.itemView.getContext()).load(postModel.get(position).getImageUrl()).into(holder.imageView);

        Glide.with(holder.itemView.getContext()).load(postModel.get(position).getUdp()).into(holder.udp);//}

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();




       /* holder.likeCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), PostLikedByActivity.class);
                intent.putExtra("pid", pid);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.itemView.getContext().startActivity(intent);
            }
        });*/


        holder.likePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int plike = Integer.parseInt(postModel.get(position).getLikeCounter());
                mprocesslike = true;
                final String postid = postModel.get(position).getUid();
                likeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (mprocesslike) {
                            if (snapshot.child(postid).hasChild(myuid)) {//replaced ptime variable with myud.. crashes, name and dp fail
                                postRef.child(postid).child("likeCounter").setValue("" + (plike-1));
                                likeRef.child(postid).child(myuid).removeValue();
                                mprocesslike = false;
                            } else{
                                postRef.child(postid).child("likeCounter").setValue(""+ (plike +1));
                                likeRef.child(postid).child(myuid).setValue("Liked");
                                mprocesslike = false;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Posts");
        Query query = databaseReference.orderByChild("now").equalTo(pid);
        query.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                String commentcount = dataSnapshot1.child("commentCounter").getValue().toString();
                                                holder.commentCounter.setText(commentcount);
                                            }

                                        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showMoreOptions(holder.more, uid, myuid, ptime, image);
            }
        });

        holder.commentPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("pid", pid);//serverTme);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent postIntent = new Intent(context, PostDetailsActivity.class);
                postIntent.putExtra("pid", pid);
                postIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(postIntent);
            }
        });

    }



    private void  showMoreOptions(ImageView more, String uid, String myuid, final String pid,
                                  final String image){
        PopupMenu popupMenu = new PopupMenu(context, more, Gravity.END);
        if (uid.equals(myuid)){
            popupMenu.getMenu().add(Menu.NONE, 0,0, "FUTA");
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() ==0){
                    deleteWithImage(pid, image);
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void deleteWithImage(final String pid, String image){
        final ProgressBar progressBar = new ProgressBar(context);
        progressBar.setProgress(100);
        StorageReference picRef = FirebaseStorage.getInstance().getReferenceFromUrl(image);
        picRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Query query = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("now").equalTo(pid);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                            dataSnapshot.getRef().removeValue();
                        }
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "Delete successful", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

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
        ImageView imageView, udp, more;
        TextView  now, likeCounter, uname, commentCounter, post;
        Button likePostBtn, commentPostBtn;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.post_imageView);
            udp = itemView.findViewById(R.id.profileImage);
            more = itemView.findViewById(R.id.imbtnMore);
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


/*getting current user data
        firebaseUser = firebaseAuth.getCurrentUser();
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Users");

                // Initialising the text view and imageview
                avatartv = view.findViewById(R.id.avatartv);
                name = view.findViewById(R.id.nametv);
                email = view.findViewById(R.id.emailtv);
                fab = view.findViewById(R.id.fab);
                pd = new ProgressDialog(getActivity());
                pd.setCanceledOnTouchOutside(false);
                Query query = databaseReference.orderByChild("email").equalTo(firebaseUser.getEmail());

                query.addValueEventListener(new ValueEventListener() {
@Override
public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
        // Retrieving Data from firebase
        String name = "" + dataSnapshot1.child("name").getValue();
        String emaill = "" + dataSnapshot1.child("email").getValue();
        String image = "" + dataSnapshot1.child("image").getValue();
        // setting data to our text view
        nam.setText(name);
        email.setText(emaill);
        try {
        Glide.with(getActivity()).load(image).into(avatartv);
        } catch (Exception e) {

        }
        }
        }

@Override
public void onCancelled(@NonNull DatabaseError databaseError) {

        }
        });*/

