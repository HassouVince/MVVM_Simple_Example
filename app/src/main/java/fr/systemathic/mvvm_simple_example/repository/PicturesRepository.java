package fr.systemathic.mvvm_simple_example.repository;

import java.util.List;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import fr.systemathic.mvvm_simple_example.datase.AllPicturesLoadService;
import fr.systemathic.mvvm_simple_example.datase.PictureLoadService;
import fr.systemathic.mvvm_simple_example.model.JphPicture;
import fr.systemathic.mvvm_simple_example.model.JphPictureDao;
import fr.systemathic.mvvm_simple_example.net.PicturesApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PicturesRepository {

    private final JphPictureDao pictureDao;

    private PicturesApiManager picturesApiManager;

    private static PicturesRepository picturesRepository;

    private PicturesRepository(JphPictureDao pictureDao) {
        this.pictureDao = pictureDao;
        picturesApiManager = new PicturesApiManager();
    }

    public static PicturesRepository getInstance(JphPictureDao pictureDao){
        if(picturesRepository == null){
            picturesRepository = new PicturesRepository(pictureDao);
        }
        return picturesRepository;
    }

    // ROOM

    public void updatePictureFromDbById(ObservableField<JphPicture> picture, int id){
        new PictureLoadService(pictureDao,picture).execute(id);
    }

    public void insertAllPictures(JphPicture... pictures){
        pictureDao.insertAll(pictures);
    }

    public void updatePicturesFromDB(final MutableLiveData<List<JphPicture>> data){
       new AllPicturesLoadService(pictureDao,data).execute();
    }

    // NET

    public MutableLiveData<List<JphPicture>> getPicturesFromApi(){
        final MutableLiveData<List<JphPicture>> pictureData = new MutableLiveData<>();
        picturesApiManager.getManager().getPictures().enqueue(new Callback<List<JphPicture>>() {
            @Override
            public void onResponse(Call<List<JphPicture>> call, Response<List<JphPicture>> response) {
                pictureData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<JphPicture>> call, Throwable t) {
                pictureData.setValue(null);
            }
        });
        return pictureData;
    }

    public void updatePictureFromApiById(final ObservableField<JphPicture> picture, int id){
        new PicturesApiManager().getManager().getPictureWithId(id)
                .enqueue(new Callback<JphPicture>() {
                    @Override
                    public void onResponse(Call<JphPicture> call, Response<JphPicture> response) {
                        picture.set(response.body());
                    }

                    @Override
                    public void onFailure(Call<JphPicture> call, Throwable t) {}
                });
    }
}
