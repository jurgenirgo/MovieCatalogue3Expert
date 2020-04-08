package jurgen.example.moviecatalogue3.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import jurgen.example.moviecatalogue3.Database.Model;

public class FavoriteViewModel extends ViewModel {

    private MutableLiveData<List<Model>> listFavoriteMovie = new MutableLiveData<>();
    private Realm realm;

    public void getMovieFromDb() {
        realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Model> resultModels = realm.where(Model.class).findAll();
                listFavoriteMovie.setValue(resultModels);
            }
        });
    }

    public LiveData<List<Model>> getFavoriteMovie() {
        return listFavoriteMovie;
    }

    public void closeRealm() {
        realm.close();
    }
}
