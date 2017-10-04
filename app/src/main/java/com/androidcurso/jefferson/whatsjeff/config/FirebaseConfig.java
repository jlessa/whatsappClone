package com.androidcurso.jefferson.whatsjeff.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jefferson on 29/09/17.
 */

public final class FirebaseConfig {
    private static DatabaseReference firebaseRef;
    private static FirebaseAuth firebaseAuth;

    public static DatabaseReference getFirebaseInstance() {
        if (firebaseRef == null)
            firebaseRef = FirebaseDatabase.getInstance().getReference();
        return firebaseRef;
    }

    public static FirebaseAuth getFirebaseAuth() {
        if (firebaseAuth == null)
            firebaseAuth = FirebaseAuth.getInstance();
        return firebaseAuth;
    }

}
