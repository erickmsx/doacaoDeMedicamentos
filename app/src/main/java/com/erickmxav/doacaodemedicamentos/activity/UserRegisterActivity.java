package com.erickmxav.doacaodemedicamentos.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.erickmxav.doacaodemedicamentos.R;
import com.erickmxav.doacaodemedicamentos.config.FirebaseConfig;
import com.erickmxav.doacaodemedicamentos.helper.Base64Custom;
import com.erickmxav.doacaodemedicamentos.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class UserRegisterActivity extends AppCompatActivity {

    private EditText inputNameReg, inputEmailReg, inputPasswordReg;
    private AppCompatButton buttonRegister;
    private FirebaseAuth authentication;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        inputNameReg = findViewById(R.id.fieldNameReg);
        inputEmailReg = findViewById(R.id.fieldEmailReg);
        inputPasswordReg = findViewById(R.id.fieldPasswordReg);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textName = inputNameReg.getText().toString();
                String textEmail = inputEmailReg.getText().toString();
                String textPassword = inputPasswordReg.getText().toString();

                //Validate field are completely filled
                if (!textName.isEmpty()) {
                    if (!textEmail.isEmpty()) {
                        if (!textPassword.isEmpty()) {

                            user = new User();
                            user.setName(textName);
                            user.setEmail(textEmail);
                            user.setPassword(textPassword);
                            registerUser( user );

                        } else {
                            Toast.makeText(UserRegisterActivity.this,
                                    "Preencha a senha!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UserRegisterActivity.this,
                                "Preencha o email!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UserRegisterActivity.this,
                            "Preencha o nome!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registerUser(final User user) {

        authentication = FirebaseConfig.getAuthenticationFirebase();
        authentication.createUserWithEmailAndPassword(
                user.getEmail(), user.getPassword()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    try {

                        String idUser = Base64Custom.codifyBase64(user.getEmail());
                        user.setIdUser(idUser);
                        user.save();
                        Toast.makeText(UserRegisterActivity.this,
                                "Cadastro efetuado com sucesso!",
                                Toast.LENGTH_SHORT).show();
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    String exception = "";

                    try {
                        throw task.getException();

                    } catch (FirebaseAuthWeakPasswordException e) {
                        exception = "Por favor, digite uma senha mais forte!";

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "Por favor, digite um e-mail válido";

                    } catch (FirebaseAuthUserCollisionException e) {
                        exception = "Esta conta já foi cadastrada";

                    } catch (Exception e) {
                        exception = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(UserRegisterActivity.this,
                            exception,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}