package com.erickmxav.doacaodemedicamentos.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.erickmxav.doacaodemedicamentos.R;
import com.erickmxav.doacaodemedicamentos.model.Medicine;
import com.erickmxav.doacaodemedicamentos.model.UserRequest;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersRequestDescriptionActivity extends AppCompatActivity {

    private TextView textNameDesc;
    private TextView textBirthDateDesc;
    private TextView textCpfDesc;
    private TextView textAddressDesc;
    private CircleImageView imageProfDesc;
    private UserRequest userRequestDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_request_description);

        textNameDesc = findViewById(R.id.textNameURDescription);
        textBirthDateDesc = findViewById(R.id.textBirthDateURDesc);
        textCpfDesc = findViewById(R.id.textCpfURDescription);
        textAddressDesc = findViewById(R.id.textAdressURDescription);
        imageProfDesc = findViewById(R.id.imageProfileURDesc);

        //Recover data pets
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            userRequestDescription = (UserRequest) bundle.getSerializable("usersRequestDescription");
            textNameDesc.setText(userRequestDescription.getName());
            textBirthDateDesc.setText(userRequestDescription.getBirthDate());
            textCpfDesc.setText(userRequestDescription.getCpf());
            textAddressDesc.setText(userRequestDescription.getAdress());

            String photo = userRequestDescription.getImageProfile();
            if (photo != null) {

                Uri url = Uri.parse(userRequestDescription.getImageProfile());
                Glide.with(UsersRequestDescriptionActivity.this)
                        .load(url)
                        .into(imageProfDesc);

            } else {
                imageProfDesc.setImageResource(R.drawable.profile);
            }
        }
    }
}
