package com.ZXH.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
