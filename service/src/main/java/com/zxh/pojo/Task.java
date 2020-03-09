package com.zxh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private int taskId;
    private int taskUser;
    private String taskContent;
    private int taskStatus;
    private int publisher;
    private String startTime;
    private String endTime;
    private String createTime;
}
