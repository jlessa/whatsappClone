package com.androidcurso.jefferson.whatsjeff.helper;

import android.app.Application;

import com.androidcurso.jefferson.whatsjeff.config.FirebaseConfig;
import com.androidcurso.jefferson.whatsjeff.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by jefferson on 05/10/17.
 */

public class CurrentUser extends Application {
    private static User currentUser;
    private DatabaseReference firebase;

    @Override
    public void onCreate() {
        super.onCreate();
        if (currentUser == null)
            loadCurrentUser();
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void nullCurrentUser() {
        currentUser = null;
    }

    private void loadCurrentUser() {
        UserSharedPreferences userSharedPreferences = new UserSharedPreferences(this);
        firebase = FirebaseConfig.getFirebaseInstance().child("users").child(userSharedPreferences.getCurrentUserId());

        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
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
    }
}
