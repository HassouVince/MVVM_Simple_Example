package fr.systemathic.mvvm_simple_example.model;

import java.io.Serializable;
import java.util.Locale;

import androidx.databinding.BaseObservable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class JphPicture extends BaseObservable implements Serializable {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "album_id")
    private int albumId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "thumbnail_url")
    private String thumbnailUrl;

    public JphPicture() {
    }

    @Override
    public String toString() {
        return String.format(Locale.FRENCH,"Id = %d / albumId = %d\nThumbnailUrl : %s",
        this.getId(),this.getAlbumId(),this.getThumbnailUrl());
    }

    public int getId() {
        return id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

}

