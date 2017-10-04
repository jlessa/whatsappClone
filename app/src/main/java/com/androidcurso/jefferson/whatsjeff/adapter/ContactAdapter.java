package com.androidcurso.jefferson.whatsjeff.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidcurso.jefferson.whatsjeff.R;
import com.androidcurso.jefferson.whatsjeff.model.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jefferson on 04/10/17.
 */

public class ContactAdapter extends ArrayAdapter<User> {

    private ArrayList<User> users;
    private Context context;

    public ContactAdapter(@NonNull Context context, @NonNull ArrayList<User> objects) {
        super(context, 0, objects);
        this.users = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        if (users != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);


            view = inflater.inflate(R.layout.contact_list, parent, false);

            TextView textViewName = (TextView) view.findViewById(R.id.textViewNameContactFragment);
            textViewName.setText(users.get(position).getName());

            TextView textViewEmail = (TextView) view.findViewById(R.id.textViewEmailContactFragment);
            textViewEmail.setText(users.get(position).getEmail());


        }
        return view;
    }
}
