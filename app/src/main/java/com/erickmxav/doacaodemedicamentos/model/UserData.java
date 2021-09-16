package com.erickmxav.doacaodemedicamentos.model;

import com.erickmxav.doacaodemedicamentos.config.FirebaseConfig;
import com.erickmxav.doacaodemedicamentos.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class UserData implements Serializable {

    private String name;
    private String birthDate;
    private String cpf;
    private String adress;
    private String imageProfile;

    public UserData() {
    }

    public void registerUserData(){

        //método pra pegar o email do usuário que está logado e codificar pra ser usado
        //no idUsuario do firebase
        FirebaseAuth authentication = FirebaseConfig.getAuthenticationFirebase();
        String idUser = Base64Custom.codifyBase64( authentication.getCurrentUser().getEmail() );

        DatabaseReference firebase = FirebaseConfig.getFirebaseDatabase();
        firebase.child("userData")
                .child( idUser )
                .push()
                .setValue( this );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }
}