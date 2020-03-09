package com.zxh.service;

import com.zxh.mapper.ApplyMapper;
import com.zxh.pojo.Apply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApplyService {
    @Autowired
    private ApplyMapper applyMapper;

    public List<Apply> queryAll(){
        List<Apply> applyList=applyMapper.queryAll();
        return applyList;
    }

    public Apply queryById(Integer applyId){
        Apply apply=applyMapper.queryById(applyId);
        return apply;
    }

    public List<Apply> queryByFrom(Integer applyUser){
        List<Apply> applies=applyMapper.queryByFrom(applyUser);
        return applies;
    }

    public List<Apply> queryByTo(Integer approvalUser){
        List<Apply> applies=applyMapper.queryByTo(approvalUser);
        return applies;
    }

    public String add(Apply apply){
        applyMapper.add(apply);
        return "新增申请成功";
    }

    public String update(Apply apply){
        applyMapper.update(apply);
        return "修改申请成功";
    }

    public String delete(int applyId){
        applyMapper.delete(applyId);
        return "删除申请成功";
    }


}
