package fr.systemathic.mvvm_simple_example.datase;

import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import fr.systemathic.mvvm_simple_example.model.JphPicture;
import fr.systemathic.mvvm_simple_example.model.JphPictureDao;

public class AllPicturesLoadService extends AsyncTask<Integer,Integer, List<JphPicture>> {

    private JphPictureDao dao;
    private MutableLiveData<List<JphPicture>> pictures;

    public AllPicturesLoadService(JphPictureDao dao, MutableLiveData<List<JphPicture>> pictures) {
        this.dao = dao;
        this.pictures = pictures;
    }

    @Override
    protected List<JphPicture> doInBackground(Integer... integers) {
        return dao.getAll();
    }
    @Override
    protected void onPostExecute(List<JphPicture> jphPictures) {
        super.onPostExecute(jphPictures);
        pictures.setValue(jphPictures);
    }
}
