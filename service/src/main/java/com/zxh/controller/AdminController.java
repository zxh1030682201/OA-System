package com.zxh.controller;

import com.zxh.pojo.Admin;
import com.zxh.pojo.Announcement;
import com.zxh.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/queryAll")
    public List<Admin> queryAll(){
        List<Admin> admins = adminService.queryAll();
        return admins;
    }

    @PostMapping("/add")
    public String addUser(@RequestBody Map<String,Object> reqMap){
        int userId=(int)reqMap.get("userId");
        return adminService.add(new Admin(0,userId,null,null));

    }

    @PutMapping("/update")
    public String updateUser(@RequestBody Map<String,Object> reqMap){
        int adminId=(int)reqMap.get("adminId");
        int userId=(int)reqMap.get("userId");
        return adminService.update(new Admin(adminId,userId,null,null));

    }

    @DeleteMapping("/delete/{adminId}")
    public String deleteUser(@PathVariable("adminId") Integer adminId){
        return adminService.delete(adminId);
    }

}
