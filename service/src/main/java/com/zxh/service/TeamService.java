package com.zxh.service;

import com.zxh.mapper.TeamMapper;
import com.zxh.pojo.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamService {
    @Autowired
    private TeamMapper teamMapper;

    public List<Team> queryAll(){
        return teamMapper.queryAll();
    }

    public List<Team> queryByLeader(int teamLeader){
        return teamMapper.queryByLeader(teamLeader);
    }

    public Team queryById(int teamId){
        return teamMapper.queryById(teamId);
    }

    public String add(Team team){
        teamMapper.add(team);
        return "OK";
    }

    public String udpate(Team team){
        teamMapper.update(team);
        return "OK";
    }

    public String delete(int teamId){
        teamMapper.delete(teamId);
        return "OK";
    }
}
