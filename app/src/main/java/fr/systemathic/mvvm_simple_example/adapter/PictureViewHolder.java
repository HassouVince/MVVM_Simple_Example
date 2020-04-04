package fr.systemathic.mvvm_simple_example.adapter;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import fr.systemathic.mvvm_simple_example.viewmodel.MainViewModel;

class PictureViewHolder extends RecyclerView.ViewHolder{

    private final ViewDataBinding binding;

    PictureViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    void bind(Integer _position, MainViewModel viewModel) {
        itemView.setTag(_position);
        binding.setVariable(fr.systemathic.mvvm_simple_example.BR.item_model, viewModel);
        binding.setVariable(fr.systemathic.mvvm_simple_example.BR.position, _position);
        binding.executePendingBindings();
    }
}
