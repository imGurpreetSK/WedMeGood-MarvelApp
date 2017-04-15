package me.gurpreetsk.marvel.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gurpreetsk.marvel.R;
import me.gurpreetsk.marvel.activity.DetailsActivity;
import me.gurpreetsk.marvel.model.Character;

/**
 * Created by gurpreet on 14/04/17.
 */

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.MyViewHolder> {

    private List<Character> characters;
    private Context context;


    public CharactersAdapter(Context context, List<Character> characters) {
        this.characters = characters;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recyclerview_element, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(context.getString(R.string.key_is_comic), false);
                intent.putExtra(context.getString(R.string.key_character),
                        characters.get(holder.getAdapterPosition()));
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, holder.imageviewCharacterThumbnail,
                                context.getString(R.string.transition_name));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    context.startActivity(intent, options.toBundle());
                } else {
                    context.startActivity(intent);
                }
            }
        });
        Picasso.with(context)
                .load(characters.get(holder.getAdapterPosition()).getThumbnail())
                .error(context.getResources().getDrawable(R.drawable.ic_error))
                .into(holder.imageviewCharacterThumbnail);
        holder.textviewCharacterTitle.setText(characters.get(holder.getAdapterPosition()).getName());
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }


    public void swap(List<Character> characters) {
        this.characters.clear();
        this.characters.addAll(characters);
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageview_recyclerview_element)
        ImageView imageviewCharacterThumbnail;
        @BindView(R.id.textview_recyclerview_element)
        TextView textviewCharacterTitle;
        @BindView(R.id.recyclerview_element)
        CardView cardView;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
