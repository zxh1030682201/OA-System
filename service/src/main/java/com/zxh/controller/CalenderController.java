package com.zxh.controller;

import com.google.gson.Gson;
import com.zxh.pojo.Calender;
import com.zxh.service.CalenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cld")
public class CalenderController {
    @Autowired
    private CalenderService calenderService;

    private Gson gson=new Gson();

    @GetMapping("/queryAll")
    public String queryAll(){
        List<Calender> calenders=calenderService.queryAll();
        return gson.toJson(calenders);
    }

    @GetMapping("/queryByUser/{userId}")
    public String queryByUser(@PathVariable("userId") int userId){
        return gson.toJson(calenderService.queryByUser(userId));
    }

    @GetMapping("/queryById/{cldId}")
    public String queryById(@PathVariable("cldId") int cldId){
        return gson.toJson(calenderService.queryById(cldId));
    }

    @GetMapping("/queryByUD")
    public String queryByUD(@RequestParam("cldUser") int cldUser,@RequestParam("cldDate") String cldDate){
        System.out.println(cldUser+"   "+cldDate);
        return gson.toJson(calenderService.queryByUD(cldUser,cldDate));
    }


    @PostMapping("/add")
    public String add(@RequestBody Map<String,Object> reqMap){
        return calenderService.add(getCld(reqMap));
    }

    @PutMapping("/update")
    public String update(@RequestBody Map<String,Object> reqMap){
        Integer cldId=(int)reqMap.get("cldId");
        Calender calender=getCld(reqMap);
        calender.setCldId(cldId);
        return calenderService.udpate(calender);
    }

    @DeleteMapping("/delete/{cldId}")
    public String delete(@PathVariable("cldId") Integer cldId){
        return calenderService.delete(cldId);
    }


    public Calender getCld(Map<String,Object> map){
        System.out.println("获取到的map   "+map);

        int cldUser=(int)map.get("cldUser");
        String cldDate=map.get("cldDate").toString();
        String cldContent=map.get("cldContent").toString();
        String createTime=map.get("createTime").toString();

        Calender calender=new Calender(0,cldUser,cldDate,cldContent,createTime);

        System.out.println("重新生成的   "+calender);
        return calender;
    }

}
