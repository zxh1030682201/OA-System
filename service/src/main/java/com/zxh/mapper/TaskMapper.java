package com.zxh.mapper;

import com.zxh.pojo.Task;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TaskMapper {
    //查询所有
    List<Task> queryAll();

    //根据ID查询一条
    Task queryById(Integer taskId);

    //根据接受者查询
    List<Task> queryByTaskUser(Integer taskUser);

    //根据发任务者查询
    List<Task> queryByPublisher(Integer publisher);

    Integer add(Task task);

    Integer update(Task task);

    Integer delete(Integer taskId);
}
