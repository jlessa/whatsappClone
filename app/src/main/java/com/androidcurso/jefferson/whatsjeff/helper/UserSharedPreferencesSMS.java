package com.androidcurso.jefferson.whatsjeff.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by jefferson on 29/09/17.
 */

public class UserSharedPreferencesSMS {
    private Context context;
    private SharedPreferences sharedPreferences;
    private final String ARQUIVO = "whatsjeff";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String NAME_KEY = "name";
    private final String PHONE_KEY = "phone";
    private final String TOKEN_KEY = "token";

    public UserSharedPreferencesSMS(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(ARQUIVO, MODE);
        this.editor = sharedPreferences.edit();
    }

    public void saveUserPreference(String name, String phone, String token) {
        editor.putString(NAME_KEY, name);
        editor.putString(PHONE_KEY, phone);
        editor.putString(TOKEN_KEY, token);
        editor.commit();
    }

    public HashMap<String, String> getUserPreferences() {
        HashMap<String, String> userPreferences = new HashMap<>();

        userPreferences.put(NAME_KEY, sharedPreferences.getString(NAME_KEY, null));
        userPreferences.put(PHONE_KEY, sharedPreferences.getString(PHONE_KEY, null));
        userPreferences.put(TOKEN_KEY, sharedPreferences.getString(TOKEN_KEY, null));

        return userPreferences;
    }
}
