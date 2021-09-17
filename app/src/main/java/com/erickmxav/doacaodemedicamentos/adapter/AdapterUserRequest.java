package com.erickmxav.doacaodemedicamentos.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.erickmxav.doacaodemedicamentos.R;
import com.erickmxav.doacaodemedicamentos.model.UserRequest;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterUserRequest extends RecyclerView.Adapter<AdapterUserRequest.MyViewHolder> {

    private List<UserRequest> userRequests;
    Context context;

    public AdapterUserRequest(List<UserRequest> userRequests, Context context) {
        this.userRequests = userRequests;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterUserRequest.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_user_request, parent, false);
        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(AdapterUserRequest.MyViewHolder holder, int position) {
        UserRequest userRequest = userRequests.get(position);
        holder.name.setText(userRequest.getName());
        holder.medicine.setText(userRequest.getMedicine());

        if(userRequest.getImageProfile() != null){
            Uri uri = Uri.parse(userRequest.getImageProfile());
            Glide.with( context ).load( uri ).into( holder.photo );

        }else {
            holder.photo.setImageResource(R.drawable.profile);
        }
    }

    @Override
    public int getItemCount() {
        return userRequests.size();
    }

    public interface ItemClickListener{
        void onItemClick(UserRequest userRequest);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, medicine;
        CircleImageView photo;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textAdapterNameReq);
            medicine = itemView.findViewById(R.id.textAdapterMedicineReq);
            photo = itemView.findViewById(R.id.photoUserProfileAdapterReq);
        }
    }
}
