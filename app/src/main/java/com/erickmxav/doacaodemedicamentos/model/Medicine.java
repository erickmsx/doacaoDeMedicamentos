package com.erickmxav.doacaodemedicamentos.model;

import com.erickmxav.doacaodemedicamentos.config.FirebaseConfig;
import com.erickmxav.doacaodemedicamentos.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class Medicine implements Serializable {

    private String name;
    private String category;
    private String validity;
    private String photo;

    public Medicine() {
    }

    public void registerMedicine(){

        DatabaseReference firebase = FirebaseConfig.getFirebaseDatabase();
        firebase.child("medicines")
                .push()
                .setValue( this );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }
}
