package fr.systemathic.mvvm_simple_example.di;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import fr.systemathic.mvvm_simple_example.viewmodel.DetailViewModel;
import fr.systemathic.mvvm_simple_example.viewmodel.MainViewModel;
import fr.systemathic.mvvm_simple_example.datase.AppDatabase;
import fr.systemathic.mvvm_simple_example.repository.PicturesRepository;

public class Injection {

    private static AppDatabase provideDataBase(Context context){
        return AppDatabase.getInstance(context);
    }

    private static PicturesRepository providePicturesRepository(Context context) {
        AppDatabase database = provideDataBase(context);
        return PicturesRepository.getInstance(database.jphPictureDao());
    }

    private static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static MainViewModel provideMainViewModel(Context context, MainViewModel.SwipeRefreshCallBack swipeRefreshCallBack) {
        PicturesRepository dataSourcePictures = providePicturesRepository(context);
        Executor executor = provideExecutor();
        return new MainViewModel(executor, dataSourcePictures, swipeRefreshCallBack);
    }

    public static DetailViewModel provideDetailViewModel(Context context){
        PicturesRepository dataSourcePictures = providePicturesRepository(context);
        return new DetailViewModel(dataSourcePictures);
    }
}
