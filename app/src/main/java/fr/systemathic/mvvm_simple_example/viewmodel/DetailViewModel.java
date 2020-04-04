package fr.systemathic.mvvm_simple_example.viewmodel;

import androidx.databinding.ObservableField;
import fr.systemathic.mvvm_simple_example.model.JphPicture;
import fr.systemathic.mvvm_simple_example.repository.PicturesRepository;

public class DetailViewModel extends BaseViewModel {

    private PicturesRepository picturesRepository;
    ObservableField<JphPicture> picture;
    ObservableField<String> content;

    public DetailViewModel(PicturesRepository picturesRepository){
        super();
        this.picturesRepository = picturesRepository;
        picture = new ObservableField<>();
        content = new ObservableField<>();
    }

    public ObservableField<JphPicture> getPicture() {
        return picture;
    }

    public void setPicture(JphPicture picture) {
        this.picture.set(picture);
    }

    public void onNextClick(){
        onClick(picture.get().getId()+1);
    }

    public void onPreviousClick(){
        onClick(picture.get().getId()-1);
    }

    private void onClick(int id){
        try {
            picturesRepository.updatePictureFromDbById(picture,id);
        }catch (Exception e){
            e.printStackTrace();
            try {
                picturesRepository.updatePictureFromApiById(picture,id);
            }catch (Exception ex){ex.printStackTrace();}
        }
    }
}
