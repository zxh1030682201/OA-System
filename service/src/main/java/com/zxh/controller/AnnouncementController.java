package com.zxh.controller;

import com.google.gson.Gson;
import com.zxh.pojo.Announcement;
import com.zxh.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/anno")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    private Gson gson=new Gson();

    @GetMapping("/queryAll")
    public String queryAll(){
        List<Announcement> announcementList = announcementService.queryAll();
        return gson.toJson(announcementList);
    }

    @GetMapping("/queryById/{annoId}")
    public String queryUserById(@PathVariable("annoId") Integer annoId){
        Announcement announcement = announcementService.queryById(annoId);
        return gson.toJson(announcement);
    }

    @PostMapping("/add")
    public String addUser(@RequestBody Map<String,Object> reqMap){

        return announcementService.add(getAnno(reqMap));

    }

    @PutMapping("/update")
    public String updateUser(@RequestBody Map<String,Object> reqMap){
        Integer annoId = (int)reqMap.get("annoId");
        Announcement announcement = getAnno(reqMap);
        announcement.setAnnoId(annoId);
        return announcementService.update(announcement);

    }

    @DeleteMapping("/delete/{annoId}")
    public String deleteUser(@PathVariable("annoId") Integer annoId){
        return announcementService.delete(annoId);
    }


    public Announcement getAnno(Map<String,Object> map){
        System.out.println("获取到的map   "+map);

        String annoContent=map.get("annoContent").toString();
        String annoTime=map.get("annoTime").toString();
        Announcement announcement=new Announcement(0,annoContent,annoTime);

        System.out.println("重新生成的anno   "+announcement);
        return announcement;
    }
}
