package com.androidcurso.jefferson.whatsjeff.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.androidcurso.jefferson.whatsjeff.R;
import com.androidcurso.jefferson.whatsjeff.adapter.ChatItemAdapter;
import com.androidcurso.jefferson.whatsjeff.config.FirebaseConfig;
import com.androidcurso.jefferson.whatsjeff.helper.Base64Custom;
import com.androidcurso.jefferson.whatsjeff.helper.CurrentUser;
import com.androidcurso.jefferson.whatsjeff.helper.UserSharedPreferences;
import com.androidcurso.jefferson.whatsjeff.model.Chat;
import com.androidcurso.jefferson.whatsjeff.model.Message;
import com.androidcurso.jefferson.whatsjeff.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editTextMessage;
    private ImageButton btnSendChat;

    private User userTo;
    private User userFrom;

    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;

    private ListView listView;
    private ArrayList<Message> messageArrayList;
    private ChatItemAdapter adapter;

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        userTo = new User();
        userFrom = new User();

        UserSharedPreferences userSharedPreferences = new UserSharedPreferences(this);
        final Bundle extra = getIntent().getExtras();
        userFrom = CurrentUser.getCurrentUser();
        userTo.setId(Base64Custom.encodeBase64(extra.getString("userEmail")));
        setUserTo(Base64Custom.encodeBase64(extra.getString("userEmail")));


        editTextMessage = (EditText) findViewById(R.id.editTextMessageChat);
        btnSendChat = (ImageButton) findViewById(R.id.btnSendChat);
        listView = (ListView) findViewById(R.id.listViewChat);
        //Configuring Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbarChat);
        if (extra != null) {
            toolbar.setTitle(extra.getString("userName"));
        } else {
            toolbar.setTitle("Usuário");
        }
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        //Mounting Listview
        messageArrayList = new ArrayList<>();
        adapter = new ChatItemAdapter(ChatActivity.this, messageArrayList);
        listView.setAdapter(adapter);

        firebase = FirebaseConfig.getFirebaseInstance()
                .child("messages")
                .child(userFrom.getId())
                .child(userTo.getId());

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messageArrayList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Message message = data.getValue(Message.class);
                    messageArrayList.add(message);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        //Set clickbutton event
        btnSendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextMessage.getText().toString().isEmpty()) {
                    Message message = new Message();
                    message.setTextMessage(editTextMessage.getText().toString());
                    message.setUserFromId(userFrom.getId());
                    message.setUserToId(userTo.getId());

                    if (!saveMessage(userFrom.getId(), userTo.getId(), message)) {
                        Toast.makeText(ChatActivity.this, "Problema ao enviar Mensagem para Remetente", Toast.LENGTH_SHORT).show();
                    } else if (!saveMessage(userTo.getId(), userFrom.getId(), message)) {
                        Toast.makeText(ChatActivity.this, "Problema ao enviar Mensagem para Destinatario", Toast.LENGTH_SHORT).show();
                    }

                    Chat chat = new Chat();
                    chat.setUserToId(userTo.getId());
                    chat.setName(userTo.getName());
                    chat.setTextMessage(editTextMessage.getText().toString());
                    if (!saveChat(userFrom.getId(), userTo.getId(), chat, userFrom)) {
                        Toast.makeText(ChatActivity.this, "Problema ao salvar Mensagem para Remetente", Toast.LENGTH_SHORT).show();
                    } else {
                        chat = new Chat();
                        chat.setUserToId(userFrom.getId());
                        chat.setName(userFrom.getName());
                        chat.setTextMessage(editTextMessage.getText().toString());
                        if (!saveChat(userTo.getId(), userFrom.getId(), chat, userTo)) {
                            Toast.makeText(ChatActivity.this, "Problema ao salvar Mensagem para Destinatario", Toast.LENGTH_SHORT).show();
                        }
                    }

                    editTextMessage.setText("");
                }

            }
        });

    }

    private boolean saveChat(String userFromId, String userToId, Chat chat, User user) {
        //TODO: Save chat on User
        try {
            firebase = FirebaseConfig.getFirebaseInstance().child("users").child(userFromId);
            user.addChat(chat);
            firebase.setValue(user);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean saveMessage(String userFromId, String userToId, Message message) {
        //TODO: Save message on User
        try {
            firebase = FirebaseConfig.getFirebaseInstance().child("messages").child(userFromId).child(userToId);
            firebase.push().setValue(message);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    private void setUserTo(String userId) {
        firebase = FirebaseConfig.getFirebaseInstance().child("users").child(userId);
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    userTo = dataSnapshot.getValue(User.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
