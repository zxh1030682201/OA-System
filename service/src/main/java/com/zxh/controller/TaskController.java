package com.zxh.controller;

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

    @GetMapping("/queryAll")
    public List<Task> queryAll(){
        return taskService.queryAll();
    }

    @GetMapping("/queryByUser/{taskUser}")
    public List<Task> queryByUser(@PathVariable("taskUser") Integer taskUser){
        return taskService.queryByTaskUser(taskUser);
    }
    @GetMapping("/queryByPublisher/{publisher}")
    public List<Task> queryByPublisher(@PathVariable("publisher") Integer publisher){
        return taskService.queryByPublisher(publisher);
    }

    @GetMapping("/queryById/{taskId}")
    public Task queryById(@PathVariable("taskId") Integer taskId){
        return taskService.queryById(taskId);
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
