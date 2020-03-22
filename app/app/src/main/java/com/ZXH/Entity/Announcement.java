package com.ZXH.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {
    private int annoId;
    private String annoContent;
    private String annoTime;
}
