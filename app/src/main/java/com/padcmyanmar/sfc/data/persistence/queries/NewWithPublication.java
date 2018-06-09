package com.padcmyanmar.sfc.data.persistence.queries;

import android.arch.persistence.room.ColumnInfo;


public class NewWithPublication {
    
    public int id;

    @ColumnInfo(name = "news-id")
    public String newsId;

    public String brief;

    public String details;

    public String postedDate;

    @ColumnInfo(name = "publication-id")
    public String publicationId;

    public String title;

    public String logo;

    @Override
    public String toString() {
        return "NewWithPublication{" +
                "id=" + id +
                ", newsId='" + newsId + '\'' +
                //", brief='" + brief + '\'' +
               // ", details='" + details + '\'' +
                ", postedDate='" + postedDate + '\'' +
                ", publicationId=" + publicationId +
                ", title='" + title + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}
