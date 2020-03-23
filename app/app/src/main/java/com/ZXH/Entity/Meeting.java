package com.zxh.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meeting {
    private int mtId;
    private int organizer;
    private String organizerName;
    private String mtTime;
    private String mtPlace;
    private int member;
    private String memberName;
}
