package com.androidcurso.jefferson.whatsjeff.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidcurso.jefferson.whatsjeff.R;
import com.androidcurso.jefferson.whatsjeff.helper.UserSharedPreferences;
import com.androidcurso.jefferson.whatsjeff.model.Chat;

import java.util.ArrayList;

/**
 * Created by jefferson on 04/10/17.
 */

public class ChatsAdapter extends ArrayAdapter<Chat> {

    private ArrayList<Chat> chatArrayList;
    private Context context;

    public ChatsAdapter(@NonNull Context context, @NonNull ArrayList<Chat> objects) {
        super(context, 0, objects);
        this.chatArrayList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        if (chatArrayList != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            UserSharedPreferences userSharedPreferences = new UserSharedPreferences(context);

            view = inflater.inflate(R.layout.chats_list, parent, false);


            TextView textViewName = (TextView) view.findViewById(R.id.textViewNameChatsFragment);
            textViewName.setText(chatArrayList.get(position).getName());

            TextView textViewLastMessage = (TextView) view.findViewById(R.id.textViewLastMessageChatsFragment);
            textViewLastMessage.setText(chatArrayList.get(position).getTextMessage());

        }
        return view;
    }
}
