package com.erickmxav.doacaodemedicamentos.model;

import com.erickmxav.doacaodemedicamentos.config.FirebaseConfig;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class User {

    private String idUser;
    private String name;
    private String email;
    private String password;

    public User() {
    }

    public void save(){
        DatabaseReference firebase = FirebaseConfig.getFirebaseDatabase();
        firebase.child("usuarios")
                .child( this.idUser )
                .setValue( this );
    }

    public String getName() {
        return name;
    }

    @Exclude
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
