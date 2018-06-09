package com.padcmyanmar.sfc.data.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ImagesInNew",
        foreignKeys = @ForeignKey(entity = NewsVO.class,
                parentColumns = "news-id",
                childColumns = "news-id",
                onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
indices = @Index(value = "news-id"))

public class ImagesInNewVO {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String imageUrl;

    @ColumnInfo(name = "news-id")
    private String newsId;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public ImagesInNewVO() {
    }

    @Ignore
    public ImagesInNewVO(String imageUrl, String newsId) {
        this.imageUrl = imageUrl;
        this.newsId = newsId;
    }

    @Ignore
    @Override
    public String toString() {
        return "ImagesInNewVO{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", newsId='" + newsId + '\'' +
                '}';
    }
}
