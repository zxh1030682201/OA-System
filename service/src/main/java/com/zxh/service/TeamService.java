package com.zxh.service;

import com.zxh.mapper.TeamMapper;
import com.zxh.mapper.UserMapper;
import com.zxh.pojo.Team;
import com.zxh.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamService {
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private UserMapper userMapper;

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
        List<Team> teamTest=teamMapper.queryByLeader(team.getTeamLeader());
        if(teamTest.size() == 0){
            User user=userMapper.queryById(team.getTeamLeader());
            if(user.getRole()==1){
                user.setRole(2);
            }
            userMapper.update(user);
        }
        teamMapper.add(team);
        return "OK";
    }

    public String udpate(Team team){
        Team oldTeam=teamMapper.queryById(team.getTeamId());
        if(oldTeam.getTeamLeader() != team.getTeamLeader()){
            User oldLeader=userMapper.queryById(oldTeam.getTeamLeader());
            User newLeader=userMapper.queryById(team.getTeamLeader());
            List<Team> oldTeams=teamMapper.queryByLeader(oldTeam.getTeamLeader());
            if(oldTeams.size()==1 && oldLeader.getRole() == 2){
                oldLeader.setRole(1);
                userMapper.update(oldLeader);
            }
            if(newLeader.getRole()==1){
                newLeader.setRole(2);
                userMapper.update(newLeader);
            }
        }
        teamMapper.update(team);
        return "OK";
    }

    public String delete(int teamId){
        Team team=teamMapper.queryById(teamId);
        User user=userMapper.queryById(team.getTeamLeader());
        List<Team> teams=teamMapper.queryByLeader(team.getTeamLeader());
        if(teams.size()== 1 && user.getRole() == 2){
            user.setRole(1);
            userMapper.update(user);
        }
        teamMapper.delete(teamId);
        return "OK";
    }
}
