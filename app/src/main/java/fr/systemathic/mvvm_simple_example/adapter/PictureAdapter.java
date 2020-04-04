package fr.systemathic.mvvm_simple_example.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import fr.systemathic.mvvm_simple_example.R;
import fr.systemathic.mvvm_simple_example.model.JphPicture;
import fr.systemathic.mvvm_simple_example.viewmodel.MainViewModel;

public class PictureAdapter extends RecyclerView.Adapter<PictureViewHolder> {

    private List<JphPicture> pictures;
    private MainViewModel viewModel;

    public PictureAdapter(MainViewModel viewModel) {
        pictures = new ArrayList<>();
        this.viewModel = viewModel;
    }

    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_cell, viewGroup, false);
        return new PictureViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder pictureViewHolder, int i) {
        pictureViewHolder.bind(i, viewModel);
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setPictures(List<JphPicture> pictures){
        this.pictures = pictures;
    }
}
