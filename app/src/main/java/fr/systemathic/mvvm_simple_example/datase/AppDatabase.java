package fr.systemathic.mvvm_simple_example.datase;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import fr.systemathic.mvvm_simple_example.model.JphPicture;
import fr.systemathic.mvvm_simple_example.model.JphPictureDao;
import fr.systemathic.mvvm_simple_example.utils.Constants;

@Database(entities = {JphPicture.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract JphPictureDao jphPictureDao();

    public static AppDatabase getInstance(Context context){
        return Room.databaseBuilder(context,
                AppDatabase.class, Constants.DB_NAME).build();
    }
}
