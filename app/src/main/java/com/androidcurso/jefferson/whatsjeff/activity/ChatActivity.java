package com.androidcurso.jefferson.whatsjeff.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.androidcurso.jefferson.whatsjeff.R;
import com.androidcurso.jefferson.whatsjeff.config.FirebaseConfig;
import com.androidcurso.jefferson.whatsjeff.helper.Base64Custom;
import com.androidcurso.jefferson.whatsjeff.helper.CurrentUser;
import com.androidcurso.jefferson.whatsjeff.helper.UserSharedPreferences;
import com.androidcurso.jefferson.whatsjeff.model.Message;
import com.androidcurso.jefferson.whatsjeff.model.User;
import com.google.firebase.database.DatabaseReference;

public class ChatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editTextMessage;
    private ImageButton btnSendChat;

    private String userToId;
    private String userFromId;

    private DatabaseReference firebase;

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        UserSharedPreferences userSharedPreferences = new UserSharedPreferences(this);
        Bundle extra = getIntent().getExtras();
        userFromId = userSharedPreferences.getCurrentUserId();
        userToId = Base64Custom.encodeBase64(extra.getString("userEmail"));



        editTextMessage = (EditText) findViewById(R.id.editTextmessageChat);
        btnSendChat = (ImageButton) findViewById(R.id.btnSendChat);

        toolbar = (Toolbar) findViewById(R.id.toolbarChat);
        if (extra != null) {
            toolbar.setTitle(extra.getString("userName"));
        } else {
            toolbar.setTitle("Usuário");
        }
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        btnSendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editTextMessage.getText().toString().isEmpty()){
                    Message message = new Message();
                    message.setTextMessage(editTextMessage.getText().toString());
                    message.setUserFromId(userFromId);
                    message.setUserToId(userToId);
                    saveMessage(message);
                    editTextMessage.setText("");
                }

            }
        });

    }

    private void saveMessage(Message message){
        firebase = FirebaseConfig.getFirebaseInstance().child("messages").child(message.getUserFromId()).child(message.getUserToId());
        firebase.push().setValue(message.getTextMessage());
    }
}
