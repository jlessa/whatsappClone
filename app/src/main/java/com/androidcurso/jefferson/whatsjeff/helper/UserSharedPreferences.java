package com.androidcurso.jefferson.whatsjeff.helper;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by jefferson on 29/09/17.
 */

public class UserSharedPreferences {
    private Context context;
    private SharedPreferences sharedPreferences;
    private final String ARQUIVO = "whatsjeff";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String ID_KEY = "idUser";
    private final String NAME_KEY = "nameUser";
    private final String EMAIL_KEY = "emailUser";

    public UserSharedPreferences(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(ARQUIVO, MODE);
        this.editor = sharedPreferences.edit();
    }

    public void saveCurrentUserPreference(String id, String name, String email) {
        editor.putString(ID_KEY, id);
        editor.putString(NAME_KEY, name);
        editor.putString(EMAIL_KEY, email);
        editor.commit();
    }

    public String getCurrentUserId(){
        return sharedPreferences.getString(ID_KEY,null);
    }

    public String getCurrentUserName(){
        return sharedPreferences.getString(NAME_KEY,null);
    }

    public String getCurrentUserEmail(){
        return sharedPreferences.getString(EMAIL_KEY,null);
    }

}

