package com.androidcurso.jefferson.whatsjeff.helper;

import android.util.Base64;

/**
 * Created by jefferson on 03/10/17.
 */

public class Base64Custom {
    public static String encodeBase64(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }

    public static String decodeBase64(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }
}
