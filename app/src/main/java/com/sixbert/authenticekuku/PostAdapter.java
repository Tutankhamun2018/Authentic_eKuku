package com.sixbert.authenticekuku;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Context context;
    List<PostModel> postModel;
    String userId;
    boolean mprocesslike;
    String myuid;
    private DatabaseReference likeRef, postRef, userRef;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    public PostAdapter (Context context, List<PostModel> postModel){
        this.postModel = postModel;
        this.context = context;
        //firebaseUser = firebaseAuth.getCurrentUser();
        userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        List<PostModel> postModelList;


        //userId = firebaseAuth.getUid();
        myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        likeRef = FirebaseDatabase.getInstance().getReference().child("Likes");
        postRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        String image = postModel.get(position).getImageUrl(); //holder.name.setText(postModel.get(position).getName());
        final String ptime = postModel.get(position).getNow();//holder.profileOrCoverPhoto.setText(postModel.get(position).getImageUrl());
        //holder.now.setText(postModel.get(position).getNow());
        holder.post.setText(postModel.get(position).getPost());
        holder.likeCounter.setText(postModel.get(position).getLikeCounter());
        holder.commentCounter.setText(postModel.get(position).getCommentCounter());

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


        String pid = postModel.get(position).getNow();

        //setLikes(holder, ptime);

        // Glide.with(holder.itemView.getContext()).load(postModel.get(position).getImageUrl()).into(holder.image);

        //try {
        Glide.with(holder.itemView.getContext()).load(postModel.get(position).getImageUrl()).into(holder.imageView);
        //} catch (Exception e) {
        //    e.printStackTrace();

        //Glide.with(holder.itemView.getContext()).load(postModel.get(position).getProfileOrCoverPhoto()).into(holder.profileOrCoverPhoto);//}
        // holder.image.setVisibility(View.VISIBLE);
        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getCurrentUser().getUid();
        StorageReference profileRef = FirebaseStorage.getInstance().getReference().child("users/"
                +firebaseAuth.getCurrentUser().getUid()+"/profile_photo.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    Glide.with(context).load(uri.toString()).into(holder.profileOrCoverPhoto);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });//

        //get the name of the user

        firebaseAuth = FirebaseAuth.getInstance();
        //String uid = firebaseAuth.getCurrentUser().getUid();
        FirebaseDatabase dbNameRef = FirebaseDatabase.getInstance();
        userRef = dbNameRef.getReference();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("Users/"+firebaseAuth.getCurrentUser().getUid()+
                        "/name").getValue(String.class);

                //Log.d("UserName", "realname "+ name);

                //holder.name.setText(name);

                holder.name.setText(postModel.get(position).setName(name));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.likeCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), PostLikedByActivity.class);
                intent.putExtra("pid", pid);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.itemView.getContext().startActivity(intent);
            }
        });


        holder.likePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int plike = Integer.parseInt(postModel.get(position).getLikeCounter());
                mprocesslike = true;
                final String postid = postModel.get(position).getNow();
                likeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (mprocesslike) {
                            if (snapshot.child(postid).hasChild(userId)) {
                                postRef.child(postid).child("plike").setValue("" + (plike - 1));
                                likeRef.child(postid).child(userId).setValue("Liked");
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

       holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showMoreOptions(holder.more, uid, myuid, ptime, image);
            }
        });

        holder.post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostDetailsActivity.class);
                intent.putExtra("pid", serverTme);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        /*Query query = userRef.orderByChild("name").equalTo(userId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    // Retrieving Data from firebase
                    String name = "" + dataSnapshot1.child("name").getValue();
                    //String emaill = "" + dataSnapshot1.child("email").getValue();
                    String image = "" + dataSnapshot1.child("profileOrCoverPhoto").getValue();
                    // setting data to our text view
                    holder.name.setText(name);
                    //email.setText(emaill);

                    //Glide.with(holder.itemView.getContext()).load(postModel.get(position).getProfileOrCoverPhoto()).into(holder.image);//}
                    holder.image.setVisibility(View.VISIBLE);
                    try {
                        Glide.with(getActivity()).load(image).into(image);
                    } catch (Exception e) {

                        //}
                    }
                }

                @Override
                public void onCancelled (@NonNull DatabaseError error){

                }
            }
        });
    }*/
    }


    private void  showMoreOptions(ImageButton more, String uid, String myuid, final String pid,
                                  final String image){
        PopupMenu popupMenu = new PopupMenu(context, more, Gravity.END);
        if (uid.equals(myuid)){
            popupMenu.getMenu().add(Menu.NONE, 0,0, "DELETE");
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
            public void onSuccess(Void unused) {
                Query query = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("ptime").equalTo(pid);
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
                    holder.likePostBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.like_up, 0,0,0);
                    holder.likePostBtn.setText(R.string.liked);
                } else{
                    holder.likePostBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.like_up, 0,0,0);
                    holder.likePostBtn.setText(R.string.like);

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
        ImageView imageView, profileOrCoverPhoto;
        TextView  now, likeCounter, name, commentCounter, post;
        Button likePostBtn, commentPostBtn;
        ImageButton more;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.post_imageView);
            profileOrCoverPhoto = itemView.findViewById(R.id.profileImage);
            more = itemView.findViewById(R.id.imbtnMore);
            name = itemView.findViewById(R.id.userIdTv);
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

