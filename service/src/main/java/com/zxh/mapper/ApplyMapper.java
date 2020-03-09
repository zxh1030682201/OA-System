package com.zxh.mapper;

import com.zxh.pojo.Apply;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ApplyMapper {

    //查询所有申请记录
    List<Apply>  queryAll();

    //根据ID查询一条
    Apply queryById(Integer applyId);

    //根据申请人ID查询记录
    List<Apply> queryByFrom(Integer applyUser);

    //根据审批人ID查询记录
    List<Apply> queryByTo(Integer approvalUser);

    Integer add(Apply apply);

    Integer update(Apply apply);

    Integer delete(Integer applyId);

}
