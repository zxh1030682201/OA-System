package com.zxh.mapper;

import com.zxh.pojo.Message;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageMapper {

    //查询所有
    List<Message> queryAll();

    //查询一条
    Message queryById(Integer msgId);

    //根据发送者ID查询
    List<Message> queryByS(Integer sender);

    //根据接受者ID查询已读消息
    List<Message> queryByRR(Integer receiver);

    //根据接受者ID查询未读消息
    List<Message> queryByRU(Integer receiver);

    Integer add(Message message);

    Integer update(Message message);

    Integer delete(Integer msgId);
}
