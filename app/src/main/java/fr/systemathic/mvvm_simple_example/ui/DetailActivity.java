package fr.systemathic.mvvm_simple_example.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import fr.systemathic.mvvm_simple_example.R;
import fr.systemathic.mvvm_simple_example.databinding.ActivityDetailBinding;
import fr.systemathic.mvvm_simple_example.di.Injection;
import fr.systemathic.mvvm_simple_example.model.JphPicture;
import fr.systemathic.mvvm_simple_example.utils.Constants;
import fr.systemathic.mvvm_simple_example.viewmodel.DetailViewModel;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    DetailViewModel viewModel;
    Bundle state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = Injection.provideDetailViewModel(this);
        JphPicture picture =(savedInstanceState == null)? (JphPicture) getIntent().getSerializableExtra(Constants.KEY_EXTRA_PICTURE)
                : (JphPicture) savedInstanceState.getSerializable(Constants.KEY_EXTRA_PICTURE);
        viewModel.setPicture(picture);

        if(savedInstanceState != null)
            state = savedInstanceState;

        ActivityDetailBinding activityDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail);
        activityDetailBinding.setDetailModel(viewModel);
        activityDetailBinding.executePendingBindings();

        configureToolbar(activityDetailBinding);
        configurePicture();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        state = outState;
        outState.putSerializable(Constants.KEY_EXTRA_PICTURE, viewModel.getPicture().get());
    }

    private void configureToolbar(ActivityDetailBinding activityDetailBinding){
        setSupportActionBar(activityDetailBinding.detailToolbar.customToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_detail_previous:
                viewModel.onPreviousClick();
                break;
            case R.id.menu_detail_next:
                viewModel.onNextClick();
        }
        return super.onOptionsItemSelected(item);
    }

    private void configurePicture(){
        viewModel.getPicture().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if(state == null)
                    state = new Bundle();

                try{
                    state.putSerializable(Constants.KEY_EXTRA_PICTURE,((ObservableField<JphPicture>) sender).get());
                    onSaveInstanceState(state);
                }catch (Exception e){e.printStackTrace();}
            }
        });
    }
}
