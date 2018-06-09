package com.padcmyanmar.sfc.data.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aung on 12/3/17.
 */
@Entity(tableName = "SentToActions",
        foreignKeys = {
                @ForeignKey(entity = NewsVO.class,
                        parentColumns = "news-id",
                        childColumns = "news-id",
                        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = ActedUserVO.class,
                        parentColumns = "user-id",
                        childColumns = "sender-id",
                        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = ActedUserVO.class,
                        parentColumns = "user-id",
                        childColumns = "receiver-id",
                        onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)},
        indices = {@Index(value = "sender-id"), @Index(value = "news-id"), @Index(value = "receiver-id")})

public class SentToVO {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "send-to-id")
    @SerializedName("send-to-id")
    private String sendToId;

    @SerializedName("sent-date")
    private String sentDate;

    @ColumnInfo(name = "news-id")
    private String newsId;

    @ColumnInfo(name = "sender-id")
    private String senderId;

    @ColumnInfo(name = "receiver-id")
    private String receiverId;

    @Ignore
    @SerializedName("acted-user")
    private ActedUserVO sender;

    @Ignore
    @SerializedName("received-user")
    private ActedUserVO receiver;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSendToId() {
        return sendToId;
    }

    public void setSendToId(String sendToId) {
        this.sendToId = sendToId;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public ActedUserVO getSender() {
        return sender;
    }

    public void setSender(ActedUserVO sender) {
        this.sender = sender;
    }

    public ActedUserVO getReceiver() {
        return receiver;
    }

    public void setReceiver(ActedUserVO receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "SentToVO{" +
                "sendToId='" + sendToId + '\'' +
                ", sentDate='" + sentDate + '\'' +
                ", newsId='" + newsId + '\'' +
                ", senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                '}';
    }
}
