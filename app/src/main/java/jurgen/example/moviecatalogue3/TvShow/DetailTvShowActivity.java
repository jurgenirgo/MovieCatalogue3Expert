package jurgen.example.moviecatalogue3.TvShow;

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

public class DetailTvShowActivity extends AppCompatActivity {

    public static final String EXTRA_PERSON = "extra_person";
    TextView tvName, tvOverview, tvFirst_air_date, tvVote;
    ImageView imgBackdrop_path;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_show);

        tvName = findViewById(R.id.tv_tv_show_name);
        tvOverview = findViewById(R.id.tv_tv_show_overview);
        tvFirst_air_date = findViewById(R.id.tv_tv_show_first_air_date);
        tvVote = findViewById(R.id.tv_tv_show_vote);
        imgBackdrop_path = findViewById(R.id.img_tv_show_backdrop_path);

        progressBar = findViewById(R.id.progressBar);
        progressBar.bringToFront();

        final TvShow tvShow = getIntent().getParcelableExtra(EXTRA_PERSON);
        tvName.setText(tvShow.getName());
        tvOverview.setText(tvShow.getOverview());
        tvFirst_air_date.setText(tvShow.getFirst_air_date());
        tvVote.setText(tvShow.getVote_average());

        String url = "https://image.tmdb.org/t/p/w500" + tvShow.getBackdrop_path();
        Glide.with(DetailTvShowActivity.this)
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
                .into(imgBackdrop_path);

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

