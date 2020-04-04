package fr.systemathic.mvvm_simple_example.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import fr.systemathic.mvvm_simple_example.R;
import fr.systemathic.mvvm_simple_example.databinding.ActivityMainBinding;
import fr.systemathic.mvvm_simple_example.di.Injection;
import fr.systemathic.mvvm_simple_example.model.JphPicture;
import fr.systemathic.mvvm_simple_example.net.ConnectionHelper;
import fr.systemathic.mvvm_simple_example.utils.Constants;
import fr.systemathic.mvvm_simple_example.viewmodel.MainViewModel;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MainViewModel.SwipeRefreshCallBack {

    private MainViewModel viewModel;
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    private void init(@Nullable Bundle savedInstanceState){

        viewModel = Injection.provideMainViewModel(this,this);
        viewModel.connected.set(ConnectionHelper.isConnected(this));

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        activityMainBinding.setMainmodel(viewModel);
        activityMainBinding.executePendingBindings();

        configureToolbar(activityMainBinding);

        if(savedInstanceState!=null){
            restoreInstanceState(savedInstanceState);
        }else{
            configurePicturesList();
        }

        configureItemClick();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
            try {
                int position = ((GridLayoutManager) Objects.requireNonNull
                        (activityMainBinding.list.getLayoutManager())).findFirstVisibleItemPosition();
                outState.putInt(Constants.KEY_EXTRA_POSITION_RV,position);
            }catch (Exception e){e.printStackTrace();}
    }

    public void restoreInstanceState(Bundle bundle){
            try {
                int position = bundle.getInt(Constants.KEY_EXTRA_POSITION_RV,0);
                showListFromRoom(position);
            }catch (Exception e){
                e.printStackTrace();
                configurePicturesList();
            }
    }

    private void showListFromRoom(int positionToScroll){
        viewModel.getPicturesFromDB();
        getPictures(positionToScroll);
    }

    private void configureToolbar(ActivityMainBinding activityMainBinding){
        setSupportActionBar(activityMainBinding.mainToolbar.customToolbar);
        activityMainBinding.setVariable(fr.systemathic.mvvm_simple_example.BR.toolbar_title,getString(R.string.app_name));
    }

    private void configurePicturesList(){
        viewModel.loading.set(View.VISIBLE);
        viewModel.fetchPictures();
        getPictures(0);
    }

    private void getPictures(final int positionToScoll){
        viewModel.getPictures().observe(this, new Observer<List<JphPicture>>() {
            @Override
            public void onChanged(List<JphPicture> jphPictures) {

                viewModel.loading.set(View.GONE);
                viewModel.swipeRefreshing.set(false);
                stopRefreshing();

                if(jphPictures == null || jphPictures.size() == 0){
                    viewModel.emptyList.set(View.VISIBLE);
                }else{
                    viewModel.emptyList.set(View.GONE);
                    viewModel.setPicturesAdapter(jphPictures);
                    if(viewModel.connected.get()) {
                        try {
                            viewModel.insertPictures();
                        } catch (Exception e) {e.printStackTrace();}
                    }
                    activityMainBinding.list.scrollToPosition(positionToScoll);
                }
            }
        });
    }

    private void configureItemClick(){
            viewModel.getCurrentPicture().observe(this, new Observer<JphPicture>() {
                @Override
                public void onChanged(JphPicture picture) {
                    showDetails(picture);
                }
            });
    }

    private void showDetails(JphPicture picture){
        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra(Constants.KEY_EXTRA_PICTURE,picture);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ImageView v = (Objects.requireNonNull(
                    Objects.requireNonNull(activityMainBinding.list.getLayoutManager())
                    .findViewByPosition(viewModel.getItemClickedPosition())))
                    .findViewById(R.id.imageCell);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, v, getString(R.string.animation_main_to_detail));
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    @Override
    public void onRefresh() {
        getPictures(0);
    }

    private void stopRefreshing(){
        try{
            SwipeRefreshLayout refreshLayout = findViewById(R.id.swipeMain);
            if(refreshLayout.isRefreshing()){
                refreshLayout.setRefreshing(false);
            }
        }catch (Exception e){e.printStackTrace();}
    }
}
