package com.padcmyanmar.sfc.data.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.padcmyanmar.sfc.data.persistence.AppDatabase;

/**
 * Created by aung on 12/3/17.
 */
@Entity(tableName = "FavouriteActions",
        foreignKeys = {
                @ForeignKey(entity = NewsVO.class,
                        parentColumns = "news-id",
                        childColumns = "news-id",
                        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = ActedUserVO.class,
                        parentColumns = "user-id",
                        childColumns = "user-id",
                        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)},
        indices = {@Index(value = "news-id"), @Index(value = "user-id")})

public class FavoriteActionVO {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "favorite-id")
    @SerializedName("favorite-id")
    private String favoriteId;

    @SerializedName("favorite-date")
    private String favoriteDate;

    @ColumnInfo(name = "news-id")
    private String newsId;

    @ColumnInfo(name = "user-id")
    private String userId;

    @Ignore
    @SerializedName("acted-user")
    private ActedUserVO actedUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getFavoriteDate() {
        return favoriteDate;
    }

    public void setFavoriteDate(String favoriteDate) {
        this.favoriteDate = favoriteDate;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ActedUserVO getActedUser() {
        return actedUser;
    }

    public void setActedUser(ActedUserVO actedUser) {
        this.actedUser = actedUser;
    }

    @Override
    public String toString() {
        return "FavoriteActionVO{" +
                "favoriteId='" + favoriteId + '\'' +
                ", favoriteDate='" + favoriteDate + '\'' +
                ", newsId='" + newsId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
