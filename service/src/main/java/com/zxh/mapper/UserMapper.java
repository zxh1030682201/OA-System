package com.zxh.mapper;

import com.zxh.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<User> queryAll();

    User queryById(Integer userId);

    User queryByUN(String username);

    User queryByUsername(String username);

    List<User> queryByName(String name);

    List<User> queryByDept(Integer deptId);

    Integer add(User user);

    Integer update(User user);

    Integer delete(Integer userId);

}
