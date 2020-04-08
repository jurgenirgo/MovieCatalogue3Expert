package jurgen.example.moviecatalogue3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import jurgen.example.moviecatalogue3.Adapter.SearchAdapter;
import jurgen.example.moviecatalogue3.Model.ResultModel;

public class SearchActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView textView, searchText;
    ProgressBar progressBar;

    String keywordPencarian;
    int kategoriPencarian;
    String API_KEY = "f86b1c0a6ae19306836d34ed10a8c269";
    String URL;

    RecyclerView recyclerView;
    ArrayList<ResultModel> listItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.toolbar);
        textView = findViewById(R.id.textToolbar);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        searchText = findViewById(R.id.searchText);

        keywordPencarian = getIntent().getStringExtra("KEYWORDPENCARIAN");
        kategoriPencarian = getIntent().getIntExtra("KATEGORIPENCARIAN", 0);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        searchText.setText("Keyword: "+keywordPencarian);

        getData();
    }

    public void getData() {
        AsyncHttpClient client = new AsyncHttpClient();

        if (kategoriPencarian == 0) {
            URL = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query=" + keywordPencarian;
        } else if (kategoriPencarian == 1) {
            URL = "https://api.themoviedb.org/3/search/tv?api_key=" + API_KEY + "&language=en-US&query=" + keywordPencarian;
        }

        client.get(URL, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);

                        listItems.add(new ResultModel(
                                movie.getString("poster_path"),
                                movie.getInt("id"),
                                movie.getString("backdrop_path"),
                                ((kategoriPencarian == 0) ? movie.getString("original_title") : movie.getString("original_name")),
                                movie.getInt("vote_average"),
                                movie.getString("overview"),
                                ((kategoriPencarian == 0) ? movie.getString("release_date") : movie.getString("first_air_date"))
                        ));

                        }
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }

                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(new SearchAdapter(SearchActivity.this, kategoriPencarian, listItems));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
