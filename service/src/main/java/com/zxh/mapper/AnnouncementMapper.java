package com.zxh.mapper;

import com.zxh.pojo.Announcement;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AnnouncementMapper {

    //查询所有公告信息
    List<Announcement> queryAll();

    //根据公告ID查询一条
    Announcement queryById(Integer annoId);

    Integer add(Announcement announcement);

    Integer update(Announcement announcement);

    Integer delete(Integer annoId);

}
