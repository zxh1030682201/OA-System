package com.zxh.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private int deptId;
    private String deptName;
    private int sdeptId;
    private String sdeptName;
    private int deptMId;
    private String deptMName;
}
