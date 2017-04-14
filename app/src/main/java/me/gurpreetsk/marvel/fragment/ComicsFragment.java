package me.gurpreetsk.marvel.fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gurpreetsk.marvel.R;
import me.gurpreetsk.marvel.adapter.ComicsAdapter;
import me.gurpreetsk.marvel.model.Comic;
import me.gurpreetsk.marvel.model.ComicsTable;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComicsFragment extends Fragment {

    @BindView(R.id.comics_recycler_view)
    RecyclerView comicsRecyclerView;

    private static final String TAG = ComicsFragment.class.getSimpleName();


    public ComicsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_comics, container, false);
        ButterKnife.bind(this, v);

        comicsRecyclerView.setAdapter(new ComicsAdapter(getContext(), fetchComics()));
        comicsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        return v;
    }


    private List<Comic> fetchComics() {
        Cursor cursor = getContext().getContentResolver().query(ComicsTable.CONTENT_URI, null, null, null, null);
        return ComicsTable.getRows(cursor, true);
    }

}
