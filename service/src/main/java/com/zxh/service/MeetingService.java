package com.zxh.service;

import com.zxh.mapper.MeetingMapper;
import com.zxh.pojo.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MeetingService {
    @Autowired
    private MeetingMapper meetingMapper;

    public List<Meeting> queryByOrganizer(int organizer){
        return meetingMapper.queryByOrganizer(organizer);
    }

    public List<Meeting> queryByMember(int member){
        return meetingMapper.queryByMember(member);
    }

    public List<Meeting> queryMeeting(int member,String date){
        return meetingMapper.queryMeeting(member,date);
    }

    public Meeting queryById(int mtId){
        return meetingMapper.queryById(mtId);
    }

    public String add(Meeting meeting){
        meetingMapper.add(meeting);
        return "OK";
    }

    public String update(Meeting meeting){
        meetingMapper.update(meeting);
        return "OK";
    }
    public String delete(int mtId){
        meetingMapper.delete(mtId);
        return "OK";
    }

}
