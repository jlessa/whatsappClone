package com.androidcurso.jefferson.whatsjeff.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jefferson on 29/09/17.
 */

public class Permissions {

    public static boolean validPermissions(int requestCode, Activity activity, String permissions[]) {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissionList = new ArrayList<>();


            //Check If permission is granted
            for (String permission : permissions) {
                boolean validPermission = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
                if (!validPermission) {
                    permissionList.add(permission);
                }
            }

            //Don't need aks permisson
            if (permissionList.isEmpty()) return true;

            String[] permissionArray = new String[permissionList.size()];
            permissionList.toArray(permissionArray);


            //Ask Permission
            ActivityCompat.requestPermissions(activity, permissionArray, requestCode);

        }
        return true;
    }
}
