package com.androidcurso.jefferson.whatsjeff.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.androidcurso.jefferson.whatsjeff.R;
import com.androidcurso.jefferson.whatsjeff.adapter.TabAdapter;
import com.androidcurso.jefferson.whatsjeff.config.FirebaseConfig;
import com.androidcurso.jefferson.whatsjeff.helper.Base64Custom;
import com.androidcurso.jefferson.whatsjeff.helper.SlidingTabLayout;
import com.androidcurso.jefferson.whatsjeff.helper.UserSharedPreferences;
import com.androidcurso.jefferson.whatsjeff.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebase;
    private Toolbar toolbar;

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private TabAdapter tabAdapter;
    private String newContactId;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseConfig.getFirebaseAuth();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //TODO: Use Strings
        toolbar.setTitle("WhatsJeff");
        setSupportActionBar(toolbar);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.slideTabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorAccent));

        loadCurrentUser();

        tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        slidingTabLayout.setViewPager(viewPager);

    }

    //TODO: Create Class to handle this method
    private void loadCurrentUser() {
        try {
            UserSharedPreferences sharedPreferences = new UserSharedPreferences(MainActivity.this);
            firebase = FirebaseConfig.getFirebaseInstance().child("users").child(sharedPreferences.getCurrentUserId());
            firebase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        currentUser = dataSnapshot.getValue(User.class);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    private void logOut() {
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivity.this, LoginEmailActivity.class));
        finish();
    }

    private void openAddContact() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Novo Contato");
        alertDialog.setMessage("E-mail do Usuário");
        alertDialog.setCancelable(false);

        final EditText editText = new EditText(MainActivity.this);

        alertDialog.setView(editText);

        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Preencha um E-mail", Toast.LENGTH_SHORT).show();
                } else {
                    newContactId = Base64Custom.encodeBase64(editText.getText().toString().replace(" ", "").toLowerCase());
                    firebase = FirebaseConfig.getFirebaseInstance().child("users").child(newContactId);
                    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                UserSharedPreferences userSharedPreferences = new UserSharedPreferences(MainActivity.this);
                                firebase = FirebaseConfig.getFirebaseInstance().child("users").child(userSharedPreferences.getCurrentUserId());
                                User newcontact = dataSnapshot.getValue(User.class);
                                //TODO: Use list of contacts
                                currentUser.addContacts(newcontact);
                                firebase.setValue(currentUser);

                            } else {
                                Toast.makeText(MainActivity.this, "Usuário não encontrado", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialog.create();
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sair_menu:
                logOut();
                break;
            case R.id.add_menu:
                openAddContact();
                break;
            case R.id.pesquisa_menu:
                Toast.makeText(MainActivity.this, "Pesquisa", Toast.LENGTH_SHORT).show();
                break;
            case R.id.config_menu:
                Toast.makeText(MainActivity.this, "Config", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }
}

