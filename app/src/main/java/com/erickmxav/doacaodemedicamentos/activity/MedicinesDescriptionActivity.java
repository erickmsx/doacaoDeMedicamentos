package com.erickmxav.doacaodemedicamentos.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.erickmxav.doacaodemedicamentos.R;
import com.erickmxav.doacaodemedicamentos.model.Medicine;

import de.hdodenhof.circleimageview.CircleImageView;

public class MedicinesDescriptionActivity extends AppCompatActivity {

    private TextView textNameDescription;
    private TextView textCategoryDescription;
    private TextView textValidityDescription;
    private CircleImageView imageMedicineDescription;
    private Medicine medicineDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines_description);

        textNameDescription = findViewById(R.id.textNameURDescription);
        textCategoryDescription = findViewById(R.id.textCategoryDesc);
        textValidityDescription = findViewById(R.id.textBirthDateURDesc);
        imageMedicineDescription = findViewById(R.id.imageProfileURDesc);

        //Recover data pets
        Bundle bundle = getIntent().getExtras();
        if ( bundle != null ){

            medicineDescription = (Medicine) bundle.getSerializable("medicinesDescription");
            textNameDescription.setText( medicineDescription.getName() );
            textCategoryDescription.setText( medicineDescription.getCategory() );
            textValidityDescription.setText( medicineDescription.getValidity() );

            String photo = medicineDescription.getPhoto();
            if( photo != null ){

                Uri url = Uri.parse( medicineDescription.getPhoto() );
                Glide.with(MedicinesDescriptionActivity.this)
                        .load( url )
                        .into(imageMedicineDescription);

            }else {
                imageMedicineDescription.setImageResource(R.drawable.medicine);
            }
        }
    }
}