package fr.systemathic.mvvm_simple_example.model;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface JphPictureDao {

    @Query("SELECT * FROM jphpicture")
    List<JphPicture> getAll();

    @Query("SELECT * FROM jphpicture WHERE id == :pId LIMIT 1")
    JphPicture getPictureById(int pId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(JphPicture... pictures);

    @Insert
    void insert(JphPicture picture);
}
