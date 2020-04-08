package jurgen.example.moviecatalogue3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import jurgen.example.moviecatalogue3.Model.ResultModel;
import jurgen.example.moviecatalogue3.R;
import jurgen.example.moviecatalogue3.SearchDetail;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context context;
    private int kategori;
    private ArrayList<ResultModel> arrayList;

    public SearchAdapter(Context context, int kategori, ArrayList<ResultModel> arrayList) {
        this.context = context;
        this.kategori = kategori;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movies, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final ResultModel model = arrayList.get(i);

        viewHolder.tvTitle.setText(model.getTitle());
        viewHolder.tvRelease_date.setText(model.getReleaseDate());
        viewHolder.tvVote_average.setText(String.valueOf(model.getVoteAverage()));

        String url = "https://image.tmdb.org/t/p/w500" + model.getPosterPath();
        Glide.with(context)
                .load(url)
                .into(viewHolder.imgPoster_path);

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SearchDetail.class)
                        .putExtra("poster_path", model.getPosterPath())
                        .putExtra("id", model.getId())
                        .putExtra("backdrop_path", String.valueOf(model.getBackdropPath()))
                        .putExtra("title", model.getTitle())
                        .putExtra("vote_average", model.getVoteAverage())
                        .putExtra("overview", model.getOverview())
                        .putExtra("release_date", model.getReleaseDate()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvRelease_date, tvVote_average;
        ImageView imgPoster_path;
        RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_movies_title);
            tvRelease_date = itemView.findViewById(R.id.tv_movies_release);
            tvVote_average = itemView.findViewById(R.id.tv_movies_vote);
            imgPoster_path = itemView.findViewById(R.id.img_movies_poster);
            container = itemView.findViewById(R.id.container);
        }
    }
}
