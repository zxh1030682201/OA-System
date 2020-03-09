package com.zxh.mapper;

import com.zxh.pojo.Calender;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CalenderMapper {

    //查询所有
    List<Calender> queryAll();

    Calender queryById(Integer cldId);

    Calender queryByUD(Integer user,String date);

    //根据用户查询
    List<Calender> queryByUser(Integer cldUser);

    Integer add(Calender calender);

    Integer update(Calender calender);

    Integer delete(Integer cldId);
}
