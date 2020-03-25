package com.zxh.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    private int msgId;
    private int sender;
    private String senderName;
    private int receiver;
    private String sendTime;
    private String msgTheme;
    private String msgContent;
    private int readed;
}
