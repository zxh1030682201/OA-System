package com.zxh.mapper;

import com.zxh.pojo.Meeting;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MeetingMapper {

    List<Meeting> queryByOrganizer(int organizer);

    List<Meeting> queryByMember(int member);

    Meeting queryById(int mtId);

    Integer add(Meeting meeting);

    Integer update(Meeting meeting);

    Integer delete(Integer mtId);
}
