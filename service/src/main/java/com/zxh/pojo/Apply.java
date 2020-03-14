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
    private String applyUserName;
    private int approvalUser;
    private String approvalUserName;
    private int applyType;
    private String startTime;
    private String endTime;
    private String applyReason;
    private String approvalReason;
    private int applyStatus;
}
