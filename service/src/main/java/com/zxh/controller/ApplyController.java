package com.zxh.controller;

import com.google.gson.Gson;
import com.zxh.pojo.Apply;
import com.zxh.pojo.User;
import com.zxh.service.ApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apply")
public class ApplyController {

    @Autowired
    private ApplyService applyService;

    private Gson gson=new Gson();

    @GetMapping("/queryAll")
    public String queryAll(){
        List<Apply> applies = applyService.queryAll();
        return gson.toJson(applies);
    }

    @GetMapping("/queryById/{applyId}")
    public String queryById(@PathVariable("applyId") Integer applyId){
        Apply apply = applyService.queryById(applyId);
        return gson.toJson(apply);
    }

    @GetMapping("/queryByFrom/{applyUser}")
    public String queryByFrom(@PathVariable("applyUser") Integer applyUser){
        List<Apply> applies = applyService.queryByFrom(applyUser);
        return gson.toJson(applies);
    }

    @GetMapping("/queryByTo/{approvalUser}")
    public String queryByTo(@PathVariable("approvalUser") Integer approvalUser){
        List<Apply> applies = applyService.queryByTo(approvalUser);
        return gson.toJson(applies);
    }

    @PostMapping("/add")
    public String addUser(@RequestBody Map<String,Object> reqMap){
        Integer applyUser=(int)reqMap.get("applyUser");
        Integer approvalUser=(int)reqMap.get("approvalUser");
        Integer applyType=(int)reqMap.get("applyType");
        String startTime=reqMap.get("startTime").toString();
        String endTime=reqMap.get("endTime").toString();
        String applyReason=reqMap.get("applyReason").toString();
        Integer applyStatus=(int)reqMap.get("applyStatus");

        Apply apply=new Apply(0,applyUser,null,approvalUser,null,applyType,startTime,endTime,applyReason,null,applyStatus);

        return applyService.add(apply);

    }

    @PutMapping("/update")
    public String updateUser(@RequestBody Map<String,Object> reqMap){
        Integer applyId = (int)reqMap.get("applyId");
        Apply apply = getApply(reqMap);
        apply.setApplyId(applyId);

        return applyService.update(apply);

    }

    @DeleteMapping("/delete/{applyId}")
    public String deleteUser(@PathVariable("applyId") Integer applyId){

        return applyService.delete(applyId);

    }




    private Apply getApply(Map<String,Object> map){
        System.out.println("获取到的map   "+map);

        Integer applyUser=(int)map.get("applyUser");
        Integer approvalUser=(int)map.get("approvalUser");
        Integer applyType=(int)map.get("applyType");
        String startTime=map.get("startTime").toString();
        String endTime=map.get("endTime").toString();
        String applyReason=map.get("applyReason").toString();
        String approvalReason=map.get("approvalReason").toString();
        Integer applyStatus=(int)map.get("applyStatus");

        Apply apply=new Apply(0,applyUser,null,approvalUser,null,applyType,startTime,endTime,applyReason,approvalReason,applyStatus);

        System.out.println("重新生成的apply   "+apply);
        return apply;
    }
}
