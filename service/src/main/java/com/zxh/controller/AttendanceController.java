package com.zxh.controller;

import com.google.gson.Gson;
import com.zxh.pojo.Attendance;
import com.zxh.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/atd")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    private Gson gson=new Gson();

    @GetMapping("/queryAll")
    public String queryAll(){
        List<Attendance> attendances= attendanceService.queryAll();
        return gson.toJson(attendances);
    }

    @GetMapping("/queryById/{atdId}")
    public String queryById(@PathVariable("atdId") int atdId){
        Attendance attendance= attendanceService.queryById(atdId);
        return gson.toJson(attendance);
    }

    @GetMapping("/queryByUser/{atdUser}")
    public String queryByUser(@PathVariable("atdUser") int atdUser){
        List<Attendance> attendances= attendanceService.queryByUser(atdUser);
        return gson.toJson(attendances);
    }

    @GetMapping("/queryByUD")
    public String queryByUD(@RequestParam("userId")int atdUser,@RequestParam("date")String date){
        List<Attendance> attendances= attendanceService.queryByUD(atdUser,date);
        return gson.toJson(attendances);
    }

    @PostMapping("/add")
    public String add(@RequestBody Map<String,Object> reqMap){
        return attendanceService.add(getArd(reqMap));
    }

    @PutMapping("/update")
    public String update(@RequestBody Map<String,Object> reqMap){
        Integer atdId = (int)reqMap.get("atdId");
        Attendance attendance=getArd(reqMap);
        attendance.setAtdId(atdId);
        return attendanceService.udpate(attendance);
    }

    @DeleteMapping("/delete/{atdId}")
    public String delete(@PathVariable("atdId") int atdId){
        return attendanceService.delete(atdId);
    }


    public Attendance getArd(Map<String,Object> map){
        System.out.println("获取到的map   "+map);

        Integer atdUser=(int)map.get("atdUser");
        String checkInTime=map.get("checkInTime").toString();
        String checkOutTime=map.get("checkOutTime").toString();
        String checkDate=map.get("checkDate").toString();

        Attendance attendance=new Attendance(0,atdUser,checkDate,checkInTime,checkOutTime);
        return attendance;

    }
}
