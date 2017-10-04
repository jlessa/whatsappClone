package com.androidcurso.jefferson.whatsjeff.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidcurso.jefferson.whatsjeff.R;
import com.androidcurso.jefferson.whatsjeff.config.FirebaseConfig;
import com.androidcurso.jefferson.whatsjeff.helper.Base64Custom;
import com.androidcurso.jefferson.whatsjeff.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;


public class SignUpActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button btnSignUp;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseConfig.getFirebaseAuth();
        firebaseDatabase = FirebaseConfig.getFirebaseInstance();

        editTextName = (EditText) findViewById(R.id.editTextNomeSignIn);
        editTextEmail = (EditText) findViewById(R.id.editTextEmailSignIn);
        editTextPassword = (EditText) findViewById(R.id.editTextPasswordSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignIn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(editTextName.getText().toString(), editTextEmail.getText().toString(), editTextPassword.getText().toString());
                signUpUser(user);
            }
        });
    }

    private void signUpUser(final User user) {

        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(
                SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Usuário Cadastrado com Sucesso", Toast.LENGTH_SHORT).show();
                            user.setId(Base64Custom.encodeBase64(user.getEmail()));
                            user.saveUser();
                            startActivity(new Intent(SignUpActivity.this,LoginEmailActivity.class));
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException ex) {
                                Toast.makeText(SignUpActivity.this, "Senha fraca, Digite uma nova senha com mais caracteres, letras e números", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthInvalidCredentialsException ex) {
                                Toast.makeText(SignUpActivity.this, "E-mail Digitado Inválido", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthUserCollisionException ex) {
                                Toast.makeText(SignUpActivity.this, "E-mail está sendo utilizado", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(SignUpActivity.this, "Erro ao Cadastrar", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            } finally {
                                finish();
                            }

                        }
                    }
                });

    }


}
