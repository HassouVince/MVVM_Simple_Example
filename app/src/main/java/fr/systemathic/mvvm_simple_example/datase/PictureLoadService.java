package fr.systemathic.mvvm_simple_example.datase;

import android.os.AsyncTask;

import androidx.databinding.ObservableField;
import fr.systemathic.mvvm_simple_example.model.JphPicture;
import fr.systemathic.mvvm_simple_example.model.JphPictureDao;

public class PictureLoadService extends AsyncTask<Integer,Integer, JphPicture> {

    private JphPictureDao dao;
    private ObservableField<JphPicture> picture;

    public PictureLoadService(JphPictureDao dao, ObservableField<JphPicture> picture) {
        this.dao = dao;
        this.picture = picture;
    }

    @Override
    protected JphPicture doInBackground(Integer... integers) {
        return dao.getPictureById(integers[0]);
    }

    @Override
    protected void onPostExecute(JphPicture picture) {
        super.onPostExecute(picture);
        if(picture != null)
        this.picture.set(picture);

    }
}
