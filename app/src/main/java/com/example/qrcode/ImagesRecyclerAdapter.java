 package com.example.qrcode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ImagesRecyclerAdapter extends RecyclerView.Adapter<ImagesRecyclerAdapter.MyViewHolder> {
    private List<ImagesList> imagesLists;
    Context context;

    public ImagesRecyclerAdapter(List<ImagesList> imagesLists, Context context) {
        this.imagesLists = imagesLists;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ImagesRecyclerAdapter.MyViewHolder holder, int position) {
        Glide.with(context).load(imagesLists.get(position).getImgUrl()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference userReference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
                HashMap<String,Object> hashMap=new HashMap<>();
                hashMap.put("imageUrl",imagesLists.get(position).getImgUrl());
                userReference.updateChildren(hashMap );
            }
        });

    }

    @Override
    public int getItemCount() {
        return imagesLists.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
         CircleImageView imageView;
         MyViewHolder(@NonNull View  itemview){
             super(itemview);
             imageView=itemview.findViewById(R.id.profileImage);
         }
     }

}
