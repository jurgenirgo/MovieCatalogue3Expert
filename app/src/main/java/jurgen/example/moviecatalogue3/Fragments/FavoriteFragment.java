package jurgen.example.moviecatalogue3.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import jurgen.example.moviecatalogue3.Adapter.FavoriteAdapter;
import jurgen.example.moviecatalogue3.Database.Model;
import jurgen.example.moviecatalogue3.Model.FavoriteViewModel;
import jurgen.example.moviecatalogue3.R;

public class FavoriteFragment extends Fragment {

    RecyclerView recyclerView;
    FavoriteViewModel favoriteViewModel;
    FavoriteAdapter favoriteAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoriteAdapter = new FavoriteAdapter();
        favoriteAdapter.notifyDataSetChanged();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        favoriteViewModel = ViewModelProviders.of(getActivity()).get(FavoriteViewModel.class);
        favoriteViewModel.getFavoriteMovie().observe(getActivity(), getFavorite);

        recyclerView.setAdapter(favoriteAdapter);
    }

    private Observer<List<Model>> getFavorite = new Observer<List<Model>>() {
        @Override
        public void onChanged(@Nullable List<Model> models) {
            if (models != null) {
                favoriteAdapter.setData(getActivity(), models);
            }
        }
    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        favoriteViewModel.getMovieFromDb();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        favoriteViewModel.getFavoriteMovie().removeObserver(getFavorite);
        favoriteViewModel.closeRealm();
    }
}
