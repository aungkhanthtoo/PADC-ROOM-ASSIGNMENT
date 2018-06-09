package com.padcmyanmar.sfc.data.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aung on 12/2/17.
 */
@Entity(tableName = "New",
        foreignKeys = @ForeignKey(
                entity = PublicationVO.class,
                parentColumns = "publication-id",
                childColumns = "publication-id"),
        indices = {@Index(value = "news-id", unique = true), @Index(value = "publication-id")})

public class NewsVO {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("news-id")
    @ColumnInfo(name = "news-id")
    private String newsId;

    @SerializedName("brief")
    private String brief;

    @SerializedName("details")
    private String details;

    @Ignore
    @SerializedName("images")
    private List<String> images;

    @SerializedName("posted-date")
    private String postedDate;

    @ColumnInfo(name = "publication-id")
    private String publicationId;

    @Ignore
    @SerializedName("publication")
    private PublicationVO publication;

    @Ignore
    @SerializedName("favorites")
    private List<FavoriteActionVO> favoriteActions;

    @Ignore
    @SerializedName("comments")
    private List<CommentActionVO> commentActions;

    @Ignore
    @SerializedName("sent-tos")
    private List<SentToVO> sentToActions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<String> getImages() {
        if (images == null) {
            images = new ArrayList<>();
        }
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }

    public PublicationVO getPublication() {
        return publication;
    }

    public void setPublication(PublicationVO publication) {
        this.publication = publication;
    }

    public List<FavoriteActionVO> getFavoriteActions() {
        if (favoriteActions == null) {
            favoriteActions = new ArrayList<>();
        }
        return favoriteActions;
    }

    public void setFavoriteActions(List<FavoriteActionVO> favoriteActions) {
        this.favoriteActions = favoriteActions;
    }

    public List<CommentActionVO> getCommentActions() {
        if (commentActions == null) {
            commentActions = new ArrayList<>();
        }
        return commentActions;
    }

    public void setCommentActions(List<CommentActionVO> commentActions) {
        this.commentActions = commentActions;
    }

    public List<SentToVO> getSentToActions() {
        if (sentToActions == null) {
            sentToActions = new ArrayList<>();
        }
        return sentToActions;
    }

    public void setSentToActions(List<SentToVO> sentToActions) {
        this.sentToActions = sentToActions;
    }

    @Override
    public String toString() {
        return "NewsVO{" +
                "id=" + id +
                ", newsId='" + newsId + '\'' +
                ", images=" + images +
                ", postedDate='" + postedDate + '\'' +
                ", publicationId='" + publicationId + '\'' +
                ", publication=" + publication +
                ", favoriteActions=" + favoriteActions +
                ", commentActions=" + commentActions +
                ", sentToActions=" + sentToActions +
                '}';
    }
}
