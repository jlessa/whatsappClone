package com.androidcurso.jefferson.whatsjeff.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.androidcurso.jefferson.whatsjeff.R;
import com.androidcurso.jefferson.whatsjeff.helper.UserSharedPreferences;
import com.androidcurso.jefferson.whatsjeff.model.Message;
import com.androidcurso.jefferson.whatsjeff.model.User;

import java.util.ArrayList;

/**
 * Created by jefferson on 04/10/17.
 */

public class ChatItemAdapter extends ArrayAdapter<Message> {

    private ArrayList<Message> messageArrayList;
    private Context context;

    public ChatItemAdapter(@NonNull Context context, @NonNull ArrayList<Message> objects) {
        super(context, 0, objects);
        this.messageArrayList = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        if (messageArrayList != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            UserSharedPreferences userSharedPreferences = new UserSharedPreferences(context);

            if (messageArrayList.get(position).getUserFromId().equals(userSharedPreferences.getCurrentUserId())) {
                view = inflater.inflate(R.layout.chat_message_from, parent, false);
            } else {
                view = inflater.inflate(R.layout.chat_message_to, parent, false);
            }


            TextView textView = (TextView) view.findViewById(R.id.messageChat);
            textView.setText(messageArrayList.get(position).getTextMessage());


        }
        return view;
    }
}
