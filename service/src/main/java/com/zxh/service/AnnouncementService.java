package com.zxh.service;

import com.zxh.mapper.AnnouncementMapper;
import com.zxh.pojo.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    public List<Announcement> queryAll(){
        List<Announcement> announcementList= announcementMapper.queryAll();
        return announcementList;
    }

    public Announcement queryById(Integer annoId){
        Announcement announcement=announcementMapper.queryById(annoId);
        return announcement;
    }

    public String add(Announcement announcement){
        announcementMapper.add(announcement);
        return "添加公告成功";
    }

    public String update(Announcement announcement){
        announcementMapper.update(announcement);
        return "修改公告成功";
    }

    public String delete(int annoId){
        announcementMapper.delete(annoId);
        return "删除公告成功";
    }


}
