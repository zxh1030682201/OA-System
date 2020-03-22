package com.ZXH.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Calender {
    private int cldId;
    private int cldUser;
    private String cldDate;
    private String cldContent;
    private String createTime;
}
