package com.zxh.mapper;

import com.zxh.pojo.Task;
import com.zxh.pojo.Team;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TeamMapper {

    //查询所有
    List<Team> queryAll();

    //查询一条
    Team queryById(Integer teamId);

    //根据leader查询
    List<Team> queryByLeader(Integer teamLeader);

    Integer add(Team team);

    Integer update(Team team);

    Integer delete(Integer teamId);

}
