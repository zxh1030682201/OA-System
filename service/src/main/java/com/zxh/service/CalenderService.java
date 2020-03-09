package com.zxh.service;

import com.zxh.mapper.CalenderMapper;
import com.zxh.pojo.Calender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CalenderService {
    @Autowired
    private CalenderMapper calenderMapper;

    public List<Calender> queryAll(){
        List<Calender> calenders=calenderMapper.queryAll();
        return calenders;
    }

    public Calender queryById(int cldId){
        return calenderMapper.queryById(cldId);
    }

    public Calender queryByUD(int user,String date){
        return calenderMapper.queryByUD(user,date);
    }

    public List<Calender> queryByUser(int cldUser){
        return calenderMapper.queryByUser(cldUser);
    }

    public String add(Calender calender){
        calenderMapper.add(calender);
        return "OK";
    }

    public String udpate(Calender calender){
        calenderMapper.update(calender);
        return "OK";
    }

    public String delete(int cldId){
        calenderMapper.delete(cldId);
        return "OK";
    }
}
