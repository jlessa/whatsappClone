package com.androidcurso.jefferson.whatsjeff.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androidcurso.jefferson.whatsjeff.R;
import com.androidcurso.jefferson.whatsjeff.activity.ChatActivity;
import com.androidcurso.jefferson.whatsjeff.adapter.ContactAdapter;
import com.androidcurso.jefferson.whatsjeff.config.FirebaseConfig;
import com.androidcurso.jefferson.whatsjeff.helper.UserSharedPreferences;
import com.androidcurso.jefferson.whatsjeff.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<User> listContacts;
    private DatabaseReference firebase;
    private User currentUser;
    private ValueEventListener valueEventListener;

    public ContactFragment() {
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

        listContacts = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        listView = (ListView) view.findViewById(R.id.listViewContacts);

        adapter = new ContactAdapter(getActivity(), listContacts);

        listView.setAdapter(adapter);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    listContacts.clear();
                    currentUser = dataSnapshot.getValue(User.class);
                    for (User user : currentUser.getContacts()) {
                        listContacts.add(user);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        UserSharedPreferences sharedPreferences = new UserSharedPreferences(getActivity());
        firebase = FirebaseConfig.getFirebaseInstance().child("users").child(sharedPreferences.getCurrentUserId());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("userName", listContacts.get(i).getName());
                intent.putExtra("userEmail", listContacts.get(i).getEmail());
                startActivity(intent);
            }
        });

        return view;
    }

}
