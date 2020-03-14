package com.zxh.mapper;

import com.zxh.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {
    List<Admin> queryAll();

    Admin queryByUser(Integer userId);

    Integer add(Admin admin);

    Integer update(Admin admin);

    Integer delete(Integer adminId);
}
