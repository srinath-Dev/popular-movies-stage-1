package com.teachableapps.popularmoviesstage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.teachableapps.popularmoviesstage1.model.MoviesClass;
import com.teachableapps.popularmoviesstage1.utilities.NetworkUtils;

public class MovieDetails extends AppCompatActivity {
    private static final String TAG = MovieDetails.class.getSimpleName();
    private TextView ui_Title; // = (TextView) this.findViewById(R.id.tv_original_title);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError("Intent is null");
        }

        MoviesClass movieItem = (MoviesClass) intent.getSerializableExtra("movieItem");
        if (movieItem == null) {
            closeOnError(getString(R.string.Error_NoMovieData));
            return;
        }

        populateDetails(movieItem);
    }

    private void populateDetails(MoviesClass movieItem) {
        Toast.makeText(this,"Details for " + movieItem.getTitle(),Toast.LENGTH_SHORT).show();
//        Log.d(TAG,"Details for " + movieItem.getTitle());

        ((TextView)findViewById(R.id.tv_title)).setText(movieItem.getTitle());
        ((TextView)findViewById(R.id.tv_header_rating)).append(" ("+movieItem.getVote()+"/10)");
        ((RatingBar)findViewById(R.id.rbv_user_rating)).setRating(Float.parseFloat(movieItem.getVote()));
        ((TextView)findViewById(R.id.tv_release_date)).setText(movieItem.getReleaseDate());
        ((TextView)findViewById(R.id.tv_synopsis)).setText(movieItem.getSynopsis());

        String backdropPathURL = NetworkUtils.buildPosterUrl(movieItem.getBackdrop());
//        Log.d(TAG, "Backdrop URL: " + backdropPathURL);
        try {
            Picasso.with(this)
                    .load(backdropPathURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into((ImageView)this.findViewById(R.id.iv_backdrop));
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }

        String imagePathURL = NetworkUtils.buildPosterUrl(movieItem.getImage());
//        Log.d(TAG, "Image URL: " + imagePathURL);
        try {
            Picasso.with(this)
                    .load(imagePathURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into((ImageView)this.findViewById(R.id.iv_image));
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }

    }

    private void closeOnError(String msg) {
        finish();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
