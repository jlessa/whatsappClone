package com.androidcurso.jefferson.whatsjeff.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.androidcurso.jefferson.whatsjeff.fragment.ChatFragment;
import com.androidcurso.jefferson.whatsjeff.fragment.ContactFragment;

/**
 * Created by jefferson on 03/10/17.
 */

public class TabAdapter extends FragmentStatePagerAdapter {

    //TODO: Use Strings
    String tabs[] = {"CONVERSAS", "CONTATOS"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ChatFragment();
                break;
            case 1:
                fragment = new ContactFragment();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
