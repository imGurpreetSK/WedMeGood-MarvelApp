package me.gurpreetsk.marvel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import me.gurpreetsk.marvel.fragment.CharactersFragment;
import me.gurpreetsk.marvel.fragment.ComicsFragment;

/**
 * Created by gurpreet on 14/04/17.
 */

public class ScreenSlideAdapter extends FragmentStatePagerAdapter {

    private String tabTitles[] = new String[]{"Comics", "Characters"};

    private static final String TAG = ScreenSlideAdapter.class.getSimpleName();


    public ScreenSlideAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ComicsFragment();
            case 1:
                return new CharactersFragment();
        }
        return null;
    }


    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
