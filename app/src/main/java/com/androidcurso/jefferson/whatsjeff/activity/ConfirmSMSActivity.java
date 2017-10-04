package com.androidcurso.jefferson.whatsjeff.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidcurso.jefferson.whatsjeff.R;
import com.androidcurso.jefferson.whatsjeff.helper.UserSharedPreferencesSMS;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

public class ConfirmSMSActivity extends AppCompatActivity {

    private Button btnConfirmSMS;
    private EditText editTextConfirmSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_sms);

        btnConfirmSMS = (Button) findViewById(R.id.btnConfirmSMS);
        editTextConfirmSMS = (EditText) findViewById(R.id.editTextConfirmSMS);

        SimpleMaskFormatter simpleMaskFormatterConfirmSMS = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher maskTextWatcherConfirmSMS = new MaskTextWatcher(editTextConfirmSMS, simpleMaskFormatterConfirmSMS);
        editTextConfirmSMS.addTextChangedListener(maskTextWatcherConfirmSMS);

        btnConfirmSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSharedPreferencesSMS userSharedPreferencesSMS = new UserSharedPreferencesSMS(ConfirmSMSActivity.this);
                HashMap<String, String> user = userSharedPreferencesSMS.getUserPreferences();
                String generatedToken = user.get("token");
                String inputedToken = editTextConfirmSMS.getText().toString();

                if(inputedToken.equals(generatedToken)){
                    Toast.makeText(ConfirmSMSActivity.this, "Código validado",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(ConfirmSMSActivity.this, "Código não validado",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
