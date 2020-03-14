package com.zxh.mapper;

import com.zxh.pojo.Attendance;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AttendanceMapper {

    //查询所有考勤记录
    List<Attendance> queryAll();

    //根据记录ID查询一条记录
    Attendance queryById(Integer atdId);

    //根据用户查询所有记录
    List<Attendance> queryByUser(Integer atdUser);

    List<Attendance> queryByUD(Integer atdUser,String date);

    Integer add(Attendance attendance);

    Integer update(Attendance attendance);

    Integer delete(Integer atdId);
}
