package com.zxh.controller;

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

    @GetMapping("/queryAll")
    public List<Apply> queryAll(){
        List<Apply> applies = applyService.queryAll();
        return applies;
    }

    @GetMapping("/queryById/{applyId}")
    public Apply queryById(@PathVariable("applyId") Integer applyId){
        Apply apply = applyService.queryById(applyId);
        return apply;
    }

    @GetMapping("/queryByFrom/{applyUser}")
    public List<Apply> queryByFrom(@PathVariable("applyUser") Integer applyUser){
        List<Apply> applies = applyService.queryByFrom(applyUser);
        return applies;
    }

    @GetMapping("/queryByTo/{approvalUser}")
    public List<Apply> queryByTo(@PathVariable("approvalUser") Integer approvalUser){
        List<Apply> applies = applyService.queryByTo(approvalUser);
        return applies;
    }

    @PostMapping("/add")
    public String addUser(@RequestBody Map<String,Object> reqMap){

        return applyService.add(getApply(reqMap));

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
        Integer applyStatus=(int)map.get("applyStatus");

        Apply apply=new Apply(0,applyUser,approvalUser,applyType,startTime,endTime,applyReason,applyStatus);

        System.out.println("重新生成的apply   "+apply);
        return apply;
    }
}
