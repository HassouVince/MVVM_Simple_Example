package fr.systemathic.mvvm_simple_example.viewmodel;

import android.view.View;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {
    public ObservableInt loading;
    public ObservableBoolean connected;

    BaseViewModel(){
        this.loading = new ObservableInt(View.GONE);
        this.connected = new ObservableBoolean(false);
    }
}
