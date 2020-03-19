package com.zxh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int userId;
    private int role;
    private String name;
    private String username;
    private String password;
    private int gender;
    private String tel;
    private String email;
    private int deptId;
    private String deptName;
    private int userStatus;

}
