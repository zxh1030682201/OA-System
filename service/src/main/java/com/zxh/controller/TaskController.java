package com.zxh.controller;

import com.google.gson.Gson;
import com.zxh.pojo.Task;
import com.zxh.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    private Gson gson=new Gson();

    @GetMapping("/queryAll")
    public String queryAll(){
        return gson.toJson(taskService.queryAll());
    }

    @GetMapping("/queryByUser/{taskUser}")
    public String queryByUser(@PathVariable("taskUser") Integer taskUser){
        return gson.toJson(taskService.queryByTaskUser(taskUser));
    }
    @GetMapping("/queryByPublisher/{publisher}")
    public String queryByPublisher(@PathVariable("publisher") Integer publisher){
        return gson.toJson(taskService.queryByPublisher(publisher));
    }

    @GetMapping("/queryById/{taskId}")
    public String queryById(@PathVariable("taskId") Integer taskId){
        return gson.toJson(taskService.queryById(taskId));
    }

    @PostMapping("/add")
    public String add(@RequestBody Map<String,Object> reqMap){
        return taskService.add(getTask(reqMap));
    }

    @PutMapping("/update")
    public String update(@RequestBody Map<String,Object> reqMap){
        Integer taskId=(int)reqMap.get("taskId");
        Task task=getTask(reqMap);
        task.setTaskId(taskId);
        return taskService.udpate(task);
    }

    @DeleteMapping("/delete/{taskId}")
    public String delete(@PathVariable("taskId") Integer taskId){
        return taskService.delete(taskId);
    }


    private Task getTask(Map<String,Object> map){
        System.out.println("获取到的map   "+map);

        int taskUser=(int)map.get("taskUser");
        String taskContent=map.get("taskContent").toString();
        int taskStatus=(int)map.get("taskStatus");
        int publisher=(int)map.get("publisher");
        String startTime=map.get("startTime").toString();
        String endTime=map.get("endTime").toString();
        String createTime=map.get("createTime").toString();
        Task task=new Task(0,taskUser,null,taskContent,taskStatus,publisher,null,startTime,endTime,createTime);

        System.out.println("重新生成的  "+task);
        return task;
    }
}
