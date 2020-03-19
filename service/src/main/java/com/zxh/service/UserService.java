package com.zxh.service;

import com.zxh.mapper.DepartmentMapper;
import com.zxh.mapper.TeamMapper;
import com.zxh.mapper.UserMapper;
import com.zxh.pojo.Department;
import com.zxh.pojo.Team;
import com.zxh.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private TeamMapper teamMapper;


    public List<User> queryAll(){
        List<User> userList = userMapper.queryAll();
        return userList;
    }

    public List<User> queryPage(Integer pageIndex,String name){
        List<User> users = userMapper.queryByName(name);
        List<User> pageUserList = new ArrayList<>();
        for(int i=10*(pageIndex-1); i<(10*pageIndex>users.size()?users.size():10*pageIndex); i++){
            pageUserList.add(users.get(i));
        }
        return pageUserList;
    }

    public User queryById(Integer userId){
        User user=userMapper.queryById(userId);
        return user;
    }

    public User queryByUN(String username){
        User user=userMapper.queryByUN(username);
        return user;
    }

    public List<User> queryByName(String name){
        List<User> userList= userMapper.queryByName(name);
        return userList;
    }

    public List<User> queryByDept(Integer deptId){
        List<User> userList= userMapper.queryByDept(deptId);
        return userList;
    }

    public String add(User user){

        User user1=userMapper.queryByUsername(user.getUsername());
        if(user1 == null){
            userMapper.add(user);
            return "添加用户成功";
        }else{
            return "用户名已存在";
        }
    }

    public String update(User user){
        User user1=userMapper.queryByUsername(user.getUsername());
        //判断用户名不能重复
        if(user1 == null || user1.getUserId()==user.getUserId()){
            userMapper.update(user);
            return "修改用户成功";
        }else{
            return "用户名已存在";
        }
    }

    public String delete(int userId){
        Department department=departmentMapper.queryByMId(userId);
        List<Team> teams=teamMapper.queryByLeader(userId);
        System.out.println("hello   "+teams+"============================================");
        if(department == null  && teams.size() == 0){
            userMapper.delete(userId);
            return "删除用户成功";
        }
        if(department != null){
            return "存在用户主管的部门，不能删除";
        }
        if(teams.size()!=0){
            return "存在用户负责的项目，不能删除";
        }else {
            return "未知错误";
        }
    }

}
