package com.zxh.service;

import com.zxh.mapper.AttendanceMapper;
import com.zxh.pojo.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;

    public List<Attendance> queryAll(){
        List<Attendance> attendances = attendanceMapper.queryAll();
        return attendances;
    }

    public Attendance queryById(int atdId){
        Attendance attendance=attendanceMapper.queryById(atdId);
        return attendance;
    }

    public List<Attendance> queryByUser(int atdUser){
        List<Attendance> attendances = attendanceMapper.queryByUser(atdUser);
        return attendances;
    }

    public List<Attendance> queryByUD(int atdUser,String date){
        List<Attendance> attendances = attendanceMapper.queryByUD(atdUser,date);
        return attendances;
    }

    public String checkIn(int userId,String date,String checkInTime){
        List<Attendance> atds=queryByUD(userId,date);
        if(atds.size() != 0){
            return "今日已签到，请勿重复签到";
        }else{
            attendanceMapper.add(new Attendance(0,userId,date,checkInTime,""));
            return "签到成功";
        }
    }

    public String checkOut(int userId,String date,String checkOutTime){
        List<Attendance> atds=queryByUD(userId,date);
        if(atds.size() ==0){
            return "请先签到";
        }else {
            atds.get(0).setCheckOutTime(checkOutTime);
            attendanceMapper.update(atds.get(0));
            return "签退成功";
        }
    }

    public String add(Attendance attendance){
        attendanceMapper.add(attendance);
        return "新增考勤成功";
    }

    public String udpate(Attendance attendance){
        attendanceMapper.update(attendance);
        return "修改考勤成功";
    }

    public String delete(int atdId){
        attendanceMapper.delete(atdId);
        return "删除考勤成功";
    }
}
