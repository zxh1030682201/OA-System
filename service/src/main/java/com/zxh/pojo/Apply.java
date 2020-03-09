package com.zxh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Apply {
    private int applyId;
    private int applyUser;
    private int approvalUser;
    private int applyType;
    private String startTime;
    private String endTime;
    private String applyReason;
    private int applyStatus;
}
