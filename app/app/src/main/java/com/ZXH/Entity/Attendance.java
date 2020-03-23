package com.zxh.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
    private int atdId;
    private int atdUser;
    private String checkDate;
    private String checkInTime;
    private String checkOutTime;
}
