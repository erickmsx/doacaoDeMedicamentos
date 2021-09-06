package com.erickmxav.doacaodemedicamentos.helper;

import com.erickmxav.doacaodemedicamentos.config.FirebaseConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserFirebase {

    public static String getUserId() {

        FirebaseAuth user = FirebaseConfig.getAuthenticationFirebase();
        String email = user.getCurrentUser().getEmail();
        String userId = Base64Custom.codifyBase64(email);

        return userId;
    }

    public static FirebaseUser getActualUser() {
        FirebaseAuth user = FirebaseConfig.getAuthenticationFirebase();
        return user.getCurrentUser();
    }
}
