package com.androidcurso.jefferson.whatsjeff.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidcurso.jefferson.whatsjeff.R;
import com.androidcurso.jefferson.whatsjeff.activity.ChatActivity;
import com.androidcurso.jefferson.whatsjeff.adapter.ChatsAdapter;
import com.androidcurso.jefferson.whatsjeff.config.FirebaseConfig;
import com.androidcurso.jefferson.whatsjeff.helper.Base64Custom;
import com.androidcurso.jefferson.whatsjeff.helper.UserSharedPreferences;
import com.androidcurso.jefferson.whatsjeff.model.Chat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<Chat> adapter;
    private ArrayList<Chat> chatArrayList;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        chatArrayList = new ArrayList<>();
        listView = view.findViewById(R.id.listViewChats);
        adapter = new ChatsAdapter(getActivity(), chatArrayList);

        listView.setAdapter(adapter);

        UserSharedPreferences userSharedPreferences = new UserSharedPreferences(getActivity());

        firebase = FirebaseConfig.getFirebaseInstance().child("chats").child(userSharedPreferences.getCurrentUserId());

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatArrayList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    chatArrayList.add(data.getValue(Chat.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("userName", chatArrayList.get(i).getName());
                intent.putExtra("userEmail", Base64Custom.decodeBase64(chatArrayList.get(i).getUserToId()));
                startActivity(intent);
            }
        });

        return view;
    }

}
