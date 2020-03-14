package com.zxh.service;

import com.zxh.mapper.AdminMapper;
import com.zxh.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public List<Admin> queryAll(){
        return adminMapper.queryAll();
    }

    public String add(Admin admin){
        Admin admin1=adminMapper.queryByUser(admin.getUserId());
        if(admin1 == null){
            adminMapper.add(admin);
            return "添加成功";
        }else{
            return "管理员已存在，请勿重复添加";
        }

    }

    public String update(Admin admin){
        adminMapper.update(admin);
        return "修改成功";
    }

    public String delete(int adminId){
        adminMapper.delete(adminId);
        return "删除成功";
    }
}
