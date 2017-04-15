package me.gurpreetsk.marvel.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gurpreetsk.marvel.R;
import me.gurpreetsk.marvel.model.Character;
import me.gurpreetsk.marvel.model.Comic;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textview_description)
    TextView textviewDescription;
    @BindView(R.id.textview_id)
    TextView textviewID;
    @BindView(R.id.imageview_detail)
    ImageView imageView;
    @Nullable
    @BindView(R.id.textview_format)
    TextView textviewFormat;
    @Nullable
    @BindView(R.id.textview_issue_number)
    TextView textviewIssueNumber;
    @Nullable
    @BindView(R.id.textview_page_count)
    TextView textviewPageCount;

    private static final String TAG = DetailsActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isComic = getIntent().getBooleanExtra(getString(R.string.key_is_comic), false);
        if (isComic) {
            setContentView(R.layout.activity_comic_details);
            ButterKnife.bind(this);
            setSupportActionBar(toolbar);
            try {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } catch (Exception e) {
                Log.e(TAG, "onCreate: ", e);
            }
            Comic comic = getIntent().getParcelableExtra(getString(R.string.key_comic));
            toolbar.setTitle(comic.getTitle());
            String description = comic.getDescription() == null ?
                    "No Description Available" : comic.getDescription();
            textviewDescription.setText(description);
            textviewID.setText(comic.getId());
            Picasso.with(DetailsActivity.this).load(comic.getThumbnail()).into(imageView);
            if (textviewFormat != null) {
                textviewFormat.setText(comic.getFormat());
                textviewPageCount.setText(comic.getPageCount());
                textviewIssueNumber.setText(comic.getIssueNumber());
            }

        } else {
            setContentView(R.layout.activity_character_details);
            ButterKnife.bind(this);
            setSupportActionBar(toolbar);
            try {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } catch (Exception e) {
                Log.e(TAG, "onCreate: ", e);
            }
            Character character = getIntent().getParcelableExtra(getString(R.string.key_character));
            toolbar.setTitle(character.getName());
            Picasso.with(DetailsActivity.this).load(character.getThumbnail()).into(imageView);
            String description = character.getDescription().equals("") ?
                    "No Description Available" : character.getDescription();
            textviewDescription.setText(description);
            textviewID.setText(character.getId());
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }
}
