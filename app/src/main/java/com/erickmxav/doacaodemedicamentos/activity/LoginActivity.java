package com.erickmxav.doacaodemedicamentos.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.erickmxav.doacaodemedicamentos.R;
import com.erickmxav.doacaodemedicamentos.config.FirebaseConfig;
import com.erickmxav.doacaodemedicamentos.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmailLogin, inputPasswordLogin;
    private Button buttonLogin;
    private User user;
    private FirebaseAuth authentication;
    private String adminLogin = "admin@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmailLogin = findViewById(R.id.fieldEmailLogin);
        inputPasswordLogin = findViewById(R.id.fieldPasswordLogin);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Validation if the fields have been filled in
                String textEmail = inputEmailLogin.getText().toString();
                String textPassword = inputPasswordLogin.getText().toString();

                if (!textEmail.isEmpty()) {
                    if (!textPassword.isEmpty()) {
                        user = new User();
                        user.setEmail(textEmail);
                        user.setPassword(textPassword);

                        validateLogin();

                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Preencha a senha",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Preencha o e-mail",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void validateLogin() {
        authentication = FirebaseConfig.getAuthenticationFirebase();
        authentication.signInWithEmailAndPassword(
                user.getEmail(),
                user.getPassword()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(LoginActivity.this,
                            "Sucesso ao fazer login",
                            Toast.LENGTH_SHORT).show();

                    if ( user.getEmail().contains(adminLogin)) {

                        openAdminHome();

                    } else{

                        openHome();
                    }

                } else {
                    String exception = "";
                    try {
                        throw task.getException();

                    } catch (FirebaseAuthInvalidUserException e) {
                        exception = "Usuário não está cadastrado";

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "E-mail ou senha não correspondem a um usuário cadastrado";

                    } catch (Exception e) {
                        exception = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(LoginActivity.this,
                            exception,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void verifyUserLogged() {

        authentication = FirebaseConfig.getAuthenticationFirebase();

        if (authentication.getCurrentUser() != null) {
            if (authentication.getCurrentUser().getEmail().contains(adminLogin)) {

                openAdminHome();

            }else {
                openHome();
            }
        }
    }
    public void openHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void openHome(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void openUserRegister(View view) {
        startActivity(new Intent(this, UserRegisterActivity.class));
    }

    public void openAdminHome() {
        startActivity(new Intent(this, AdminActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        verifyUserLogged();
    }
}
