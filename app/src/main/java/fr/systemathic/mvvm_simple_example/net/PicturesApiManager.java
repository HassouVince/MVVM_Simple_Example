package fr.systemathic.mvvm_simple_example.net;

import java.util.List;

import fr.systemathic.mvvm_simple_example.model.JphPicture;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class PicturesApiManager {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static Retrofit retrofitInstance = null;
    private JphApiManagerService manager;

    public PicturesApiManager() {
        manager = getInstance().create(JphApiManagerService.class);
    }

    public interface JphApiManagerService {
        @GET("photos")
        Call<List<JphPicture>> getPictures();
        @GET("photos/{id}")
        Call<JphPicture> getPictureWithId(@Path("id") int id);

    }

    private static Retrofit getInstance() {
        if (retrofitInstance == null) {
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }

    public JphApiManagerService getManager() {
        return manager;
    }

}
