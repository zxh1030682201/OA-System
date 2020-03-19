package com.zxh.controller;

import com.zxh.pojo.User;
import com.zxh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public User login(@RequestBody Map<String,Object> reqMap){
        String username=reqMap.get("username").toString();
        String password=reqMap.get("password").toString();
        User user=userService.queryByUN(username);
        if(user==null){
            return null;
        }
        if(user.getPassword().equals(password) && (user.getUserStatus() != 0)){
            return user;
        }
        return null;
    }

    @GetMapping("/queryAll")
    public List<User> queryAll(){
        List<User> userList = userService.queryAll();
        return userList;
    }

    @GetMapping("/queryPage")
    public List<User> queryPage(@RequestParam("page") int pageIndex,@RequestParam("name") String name){
        List<User> userList = userService.queryPage(pageIndex,name);
        return userList;
    }


    @GetMapping("/queryById/{userId}")
    public User queryById(@PathVariable("userId") Integer userId){
        User user = userService.queryById(userId);
        return user;
    }

    @GetMapping("/queryByName")
    public List<User> queryByName(@RequestParam("name") String name){
        List<User> userList = userService.queryByName(name);
        return userList;
    }

    @GetMapping("/queryByDept/{deptId}")
    public List<User> queryByDept(@PathVariable("deptId") Integer deptId){
        List<User> userList = userService.queryByDept(deptId);
        return userList;
    }

    @PostMapping("/add")
    public String addUser(@RequestBody Map<String,Object> reqMap){

        return userService.add(getUser(reqMap));

    }

    @PutMapping("/update")
    public String updateUser(@RequestBody Map<String,Object> reqMap){
        Integer userId=(int)reqMap.get("userId");
        User user = getUser(reqMap);
        user.setUserId(userId);
        return userService.update(user);

    }

    @DeleteMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") Integer userId){

        return userService.delete(userId);

    }

    private User getUser(Map<String,Object> map){

        System.out.println("获取到的map   "+map);

        Integer role=(int)map.get("role");
        String name=map.get("name").toString();
        String username=map.get("username").toString();
        String password=map.get("password").toString();
        Integer gender=(int)map.get("gender");
        String tel=map.get("tel").toString();
        String email=map.get("email").toString();
        Integer deptId=(int)map.get("deptId");
        Integer userStatus=(int)map.get("userStatus");
        User user = new User(0,role,name,username,password,gender,tel,email,deptId,null,userStatus);

        System.out.println("重新生成的user   "+user);
        return user;
    }

}
