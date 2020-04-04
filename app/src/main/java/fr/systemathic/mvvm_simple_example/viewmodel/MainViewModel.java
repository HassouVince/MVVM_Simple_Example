package fr.systemathic.mvvm_simple_example.viewmodel;

import android.view.View;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import fr.systemathic.mvvm_simple_example.model.JphPicture;
import fr.systemathic.mvvm_simple_example.adapter.PictureAdapter;
import fr.systemathic.mvvm_simple_example.repository.PicturesRepository;

public class MainViewModel extends BaseViewModel {

    private MutableLiveData<List<JphPicture>> pictures;
    private MutableLiveData<JphPicture> currentPicture;

    private PictureAdapter adapter;
    private Executor executor;
    private PicturesRepository picturesRepository;
    private SwipeRefreshLayout.OnRefreshListener swipeRefreshListener;

    public ObservableBoolean swipeRefreshing;
    public ObservableInt emptyList;

    private int itemClickedPosition;

    public MainViewModel(Executor executor, final PicturesRepository picturesRepository, final SwipeRefreshCallBack swipeRefreshCallBack) {
        super();
        pictures = new MutableLiveData<>();
        currentPicture = new MutableLiveData<>();
        this.adapter = new PictureAdapter(this);
        this.swipeRefreshing = new ObservableBoolean(false);
        this.emptyList = new ObservableInt(View.GONE);
        this.executor = executor;
        this.picturesRepository = picturesRepository;
        itemClickedPosition = -1;

        this.swipeRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshCallBack.onRefresh();
            }
        };
    }

    public interface SwipeRefreshCallBack {
        void onRefresh();
    }

    public void fetchPictures(){
        if(connected.get()) {
            getPicturesFromApi();
        }else {
            getPicturesFromDB();
        }
    }

    private void getPicturesFromApi(){
        pictures = picturesRepository.getPicturesFromApi();
    }

    public void getPicturesFromDB(){
        picturesRepository.updatePicturesFromDB(pictures);
    }

    public MutableLiveData<List<JphPicture>> getPictures(){
        return pictures;
    }

    public void setPicturesAdapter(@Nullable List<JphPicture> pictures){
        adapter.setPictures((pictures != null)? pictures : this.pictures.getValue());
        adapter.notifyDataSetChanged();
    }

    public JphPicture getJphPictureAt(int position){
        return Objects.requireNonNull(pictures.getValue()).get(position);
    }

    public void onItemClick(int position){
        itemClickedPosition = position;
        JphPicture picture = getJphPictureAt(position);
        currentPicture.setValue(picture);
    }

    public void insertPictures(){
        if (pictures.getValue() != null && pictures.getValue().size()>0)
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    picturesRepository.insertAllPictures(pictures.getValue().toArray(new JphPicture[pictures.getValue().size()]));
                }
            });
    }

    public PictureAdapter getAdapter(){
        return adapter;
    }

    public LiveData<JphPicture> getCurrentPicture(){
        return currentPicture;
    }

    public SwipeRefreshLayout.OnRefreshListener getSwipeRefreshListener(){
        return swipeRefreshListener;
    }

    public int getItemClickedPosition() {
        return itemClickedPosition;
    }
}
