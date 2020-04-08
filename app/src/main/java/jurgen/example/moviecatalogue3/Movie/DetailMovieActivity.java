package jurgen.example.moviecatalogue3.Movie;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import jurgen.example.moviecatalogue3.R;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_PERSON = "extra_preson";

    TextView tvTitle, tvOverview, tvRelease, tvVote;
    ImageView imgPoster;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        tvTitle = findViewById(R.id.tv_movies_title);
        tvOverview = findViewById(R.id.tv_movies_overview);
        tvRelease = findViewById(R.id.tv_movies_release);
        tvVote = findViewById(R.id.tv_movies_vote);
        imgPoster = findViewById(R.id.img_movies_poster);

        progressBar = findViewById(R.id.progressBar);
        progressBar.bringToFront();

        final Movie movie = getIntent().getParcelableExtra(EXTRA_PERSON);

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvRelease.setText(movie.getRelease_date());
        tvVote.setText(movie.getVote_average());

        String url = "https://image.tmdb.org/t/p/w500" + movie.getPoster_path();
        Glide.with(DetailMovieActivity.this)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imgPoster);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}