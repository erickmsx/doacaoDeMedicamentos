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
import com.erickmxav.doacaodemedicamentos.model.Medicine;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterMedicine extends RecyclerView.Adapter<AdapterMedicine.MyViewHolder> {

    private List<Medicine> medicines;
    Context context;

    public AdapterMedicine(List<Medicine> medicines, Context context) {
        this.medicines = medicines;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_medicine, parent, false);
        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Medicine medicine = medicines.get(position);
        holder.name.setText(medicine.getName());
        holder.category.setText(medicine.getCategory());
        holder.validity.setText(medicine.getValidity());

        if(medicine.getPhoto() != null){
            Uri uri = Uri.parse(medicine.getPhoto());
            Glide.with( context ).load( uri ).into( holder.photo );

        }else {
            holder.photo.setImageResource(R.drawable.medicine);
        }
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public interface ItemClickListener{
        void onItemClick(Medicine medicine);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, category, validity;
        CircleImageView photo;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textAdapterName);
            category = itemView.findViewById(R.id.textAdapterCategory);
            validity = itemView.findViewById(R.id.textAdapterValidity);
            photo = itemView.findViewById(R.id.photoMedicineAdapter);
        }
    }
}
