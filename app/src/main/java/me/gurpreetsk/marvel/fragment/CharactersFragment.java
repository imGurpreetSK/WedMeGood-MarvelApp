package me.gurpreetsk.marvel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gurpreetsk.marvel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CharactersFragment extends Fragment {

    @BindView(R.id.characters_recycler_view)
    RecyclerView recyclerView;

    public CharactersFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_characters, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

}
