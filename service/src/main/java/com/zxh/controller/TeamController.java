package com.zxh.controller;

import com.zxh.pojo.Team;
import com.zxh.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/queryAll")
    public List<Team> queryAll(){
        return teamService.queryAll();
    }

    @GetMapping("/queryByLeader/{teamLeader}")
    public List<Team> queryByLeader(@PathVariable("teamLeader") Integer teamLeader){
        return teamService.queryByLeader(teamLeader);
    }

    @GetMapping("/queryById/{teamId}")
    public Team queryById(@PathVariable("teamId") Integer teamId){
        return teamService.queryById(teamId);
    }

    @PostMapping("/add")
    public String add(@RequestBody Map<String,Object> reqMap){
        return teamService.add(getTeam(reqMap));
    }

    @PutMapping("/update")
    public String update(@RequestBody Map<String,Object> reqMap){
        Integer teamId=(int)reqMap.get("teamId");
        Team team=getTeam(reqMap);
        team.setTeamId(teamId);
        return teamService.udpate(team);
    }

    @DeleteMapping("/delete/{teamId}")
    public String delete(@PathVariable("teamId") Integer teamId){
        return teamService.delete(teamId);
    }

    private Team getTeam(Map<String,Object> map){
        System.out.println("获取到的map   "+map);

        String teamName=map.get("teamName").toString();
        Integer teamLeader=(int)map.get("teamLeader");
        String teamMembers=map.get("teamMembers").toString();

        Team team=new Team(0,teamName,teamLeader,null,teamMembers);

        System.out.println("重新生成的   "+team);
        return team;
    }

}
