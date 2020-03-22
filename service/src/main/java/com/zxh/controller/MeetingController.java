package com.zxh.controller;

import com.google.gson.Gson;
import com.zxh.pojo.Meeting;
import com.zxh.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/meeting")
public class MeetingController {
    @Autowired
    private MeetingService meetingService;

    private Gson gson=new Gson();

    @GetMapping("/queryByOrganizer/{organizer}")
    public String queryByOrganizer(@PathVariable("organizer")int organizer){
        List<Meeting> meetings = meetingService.queryByOrganizer(organizer);
        return gson.toJson(meetings);
    }

    @GetMapping("/queryByMember/{member}")
    public String queryByMember(@PathVariable("member")int member){
        List<Meeting> meetings = meetingService.queryByMember(member);
        return gson.toJson(meetings);
    }

    @GetMapping("/queryMeeting")
    public String queryMeeting(@RequestParam("member")Integer member,@RequestParam("date")String date){
        List<Meeting> meetings = meetingService.queryMeeting(member,date);
        return gson.toJson(meetings);
    }

    @GetMapping("/queryById/{mtId}")
    public String queryById(@PathVariable("mtId")int mtId){
        Meeting meeting = meetingService.queryById(mtId);
        return gson.toJson(meeting);
    }


    @PostMapping("/add")
    public String add(@RequestBody Map<String,Object> reqMap){
        Meeting meeting=getMT(reqMap);
        return meetingService.add(meeting);

    }

    @PutMapping("/update")
    public String update(@RequestBody Map<String,Object> reqMap){
        int mtId=(int)reqMap.get("mtId");
        Meeting meeting=getMT(reqMap);
        meeting.setMtId(mtId);
        return meetingService.update(meeting);

    }

    @DeleteMapping("/delete/{mtId}")
    public String delete(@PathVariable("mtId") Integer mtId){
        return meetingService.delete(mtId);
    }

    private Meeting getMT(Map<String,Object> map){

        System.out.println("获取到的map   "+map);

        Integer organizer=(int)map.get("organizer");
        String mtTime=map.get("mtTime").toString();
        String mtPlace=map.get("mtPlace").toString();
        Integer member=(int)map.get("member");

        Meeting meeting=new Meeting(0,organizer,null,mtTime,mtPlace,member,null);

        return meeting;
    }

}
