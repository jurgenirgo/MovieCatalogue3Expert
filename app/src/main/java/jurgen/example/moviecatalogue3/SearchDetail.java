package jurgen.example.moviecatalogue3;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import io.realm.Realm;
import io.realm.RealmResults;
import jurgen.example.moviecatalogue3.Database.Model;

public class SearchDetail extends AppCompatActivity {

    String poster_path, backdrop_path, title, overview, release_date;
    int id, vote_average;

    ImageView img_movies_poster;
    TextView tv_movies_title, tv_movies_overview, tv_movies_release, tv_movies_vote;
    ProgressBar progressBar;
    Button btnFavorite;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        realm = Realm.getDefaultInstance();

        poster_path = getIntent().getStringExtra("poster_path");
        id = getIntent().getIntExtra("id", 0);
        backdrop_path = getIntent().getStringExtra("backdrop_path");
        title = getIntent().getStringExtra("title");
        vote_average = getIntent().getIntExtra("vote_average", 0);
        overview = getIntent().getStringExtra("overview");
        release_date = getIntent().getStringExtra("release_date");

        img_movies_poster = findViewById(R.id.img_movies_poster);
        tv_movies_title = findViewById(R.id.tv_movies_title);
        tv_movies_release = findViewById(R.id.tv_movies_release);
        tv_movies_overview = findViewById(R.id.tv_movies_overview);
        tv_movies_vote = findViewById(R.id.tv_movies_vote);
        progressBar = findViewById(R.id.progressBar);
        btnFavorite = findViewById(R.id.btnFavorite);

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500" + poster_path)
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
                .into(img_movies_poster);

        tv_movies_title.setText(title);
        tv_movies_overview.setText(overview);
        tv_movies_vote.setText("" + vote_average);
        tv_movies_release.setText(release_date);

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnFavorite.getText().equals("Favorite")) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Number currentNum = realm.where(Model.class).max("id");
                            int nextId;
                            if (currentNum == null) {
                                nextId = 1;
                            } else {
                                nextId = currentNum.intValue() + 1;
                            }

                            Model model = realm.createObject(Model.class);
                            model.setId(nextId);
                            model.setMovie_id(id);
                            model.setBackdropPath(backdrop_path);
                            model.setOverview(overview);
                            model.setPosterPath(poster_path);
                            model.setReleaseDate(release_date);
                            model.setTitle(title);
                            model.setVoteAverage(vote_average);

                            btnFavorite.setText("Favorited");
                        }
                    });
                } else {
                    final RealmResults<Model> model = realm.where(Model.class).equalTo("movie_id", id).findAll();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            model.deleteFromRealm(0);
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
