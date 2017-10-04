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
import com.androidcurso.jefferson.whatsjeff.helper.UserSharedPreferences;
import com.androidcurso.jefferson.whatsjeff.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginEmailActivity extends AppCompatActivity {

    private DatabaseReference firebase;
    private FirebaseAuth firebaseAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        editTextEmail = (EditText) findViewById(R.id.editTextEmailLogin);
        editTextPassword = (EditText) findViewById(R.id.editTextPasswordLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        firebase = FirebaseConfig.getFirebaseInstance();
        firebaseAuth = FirebaseConfig.getFirebaseAuth();

        isLogged();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setEmail(editTextEmail.getText().toString());
                user.setPassword(editTextPassword.getText().toString());
                authenticateUser(user);
            }
        });


    }

    public void openSignUp(View view) {
        Intent intent = new Intent(LoginEmailActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void authenticateUser(final User user) {
        firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginEmailActivity.this, "Sucesso Login", Toast.LENGTH_LONG).show();
                    UserSharedPreferences preferences = new UserSharedPreferences(LoginEmailActivity.this);
                    preferences.saveCurrentUserPreference(Base64Custom.encodeBase64(user.getEmail()));
                    goToMainActivity();
                    finish();
                } else {
                    Toast.makeText(LoginEmailActivity.this, "Erro Login", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void goToMainActivity() {
        startActivity(new Intent(LoginEmailActivity.this, MainActivity.class));
    }
    private void isLogged(){
        if (firebaseAuth.getCurrentUser() != null)
            goToMainActivity();
    }
}
