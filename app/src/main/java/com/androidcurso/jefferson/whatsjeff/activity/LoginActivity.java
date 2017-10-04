package com.androidcurso.jefferson.whatsjeff.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidcurso.jefferson.whatsjeff.R;
import com.androidcurso.jefferson.whatsjeff.helper.Permissions;
import com.androidcurso.jefferson.whatsjeff.helper.UserSharedPreferences;
import com.androidcurso.jefferson.whatsjeff.helper.UserSharedPreferencesSMS;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {


    private EditText editTextPhone;
    private EditText editTextDDI;
    private EditText editTextName;
    private Button btnSignIn;
    private String[] permissions = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_CONTACTS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Permissions.validPermissions(1, LoginActivity.this, permissions);

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextDDI = (EditText) findViewById(R.id.editTextDDI);
        editTextName = (EditText) findViewById(R.id.editTextName);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String phone = editTextDDI.getText().toString().replace("+", "").replace(" ", "") +
                        editTextPhone.getText().toString().replace("(", "").replace(")", "").replace("-", "").replace(" ", "");

                //Generate Token - mocking webservice
                Random random = new Random();
                int randomNumber = random.nextInt(9999 - 1000) + 1000;

                String token = String.valueOf(randomNumber);

                UserSharedPreferencesSMS userSharedPreferencesSMS = new UserSharedPreferencesSMS(getApplicationContext());
                userSharedPreferencesSMS.saveUserPreference(name, phone, token);

                //Send SMS
                boolean checkSMSSend = sendTokenSMS("+" + phone, "WHATSJEFF - Código de Confirmação " + token);

                if (checkSMSSend) {
                    Intent intent = new Intent(LoginActivity.this, ConfirmSMSActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Problema ao Enviar SMS", Toast.LENGTH_LONG).show();
                }

            }
        });

        //Creating Masks
        SimpleMaskFormatter simpleMaskFormatterTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher maskTextWatcherTelefone = new MaskTextWatcher(editTextPhone, simpleMaskFormatterTelefone);
        editTextPhone.addTextChangedListener(maskTextWatcherTelefone);

        SimpleMaskFormatter simpleMaskFormatterDDI = new SimpleMaskFormatter("+NN");
        MaskTextWatcher maskTextWatcherDDI = new MaskTextWatcher(editTextDDI, simpleMaskFormatterDDI);
        editTextDDI.addTextChangedListener(maskTextWatcherDDI);
        editTextDDI.setText("55");

    }

    private boolean sendTokenSMS(String phone, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, msg, null, null);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED)
                permissionDeniedAlert();
        }
    }

    private void permissionDeniedAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar esse app é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setNegativeButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
