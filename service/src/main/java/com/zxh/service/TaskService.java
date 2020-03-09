package com.zxh.service;

import com.zxh.mapper.TaskMapper;
import com.zxh.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskService {
    @Autowired
    private TaskMapper taskMapper;

    public List<Task> queryAll(){
        return taskMapper.queryAll();
    }

    public List<Task> queryByTaskUser(int taskUser){
        return taskMapper.queryByTaskUser(taskUser);
    }

    public List<Task> queryByPublisher(int publisher){
        return taskMapper.queryByPublisher(publisher);
    }

    public Task queryById(int taskId){
        return taskMapper.queryById(taskId);
    }

    public String add(Task task){
        taskMapper.add(task);
        return "OK";
    }

    public String udpate(Task task){
        taskMapper.update(task);
        return "OK";
    }

    public String delete(int taskId){
        taskMapper.delete(taskId);
        return "OK";
    }
}
