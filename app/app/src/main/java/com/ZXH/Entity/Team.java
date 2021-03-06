package com.zxh.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    private int teamId;
    private String teamName;
    private int teamLeader;
    private String teamLeaderName;
    private String teamMembers;
}
