package com.ZXH.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private int msgId;
    private int sender;
    private String senderName;
    private int receiver;
    private String sendTime;
    private String msgTheme;
    private String msgContent;
    private int readed;
}
