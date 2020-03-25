package com.zxh.service;

import com.zxh.mapper.MessageMapper;
import com.zxh.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    public List<Message> queryAll(){
        return messageMapper.queryAll();
    }

    public List<Message> queryByS(int sender){
        return messageMapper.queryByS(sender);
    }

    public List<Message> queryByRU(int receiver){
        return  messageMapper.queryByRU(receiver);
    }

    public List<Message> queryByRR(int receiver){
        return  messageMapper.queryByRR(receiver);
    }

    public String msgRead(int msgId){
        Message message=messageMapper.queryById(msgId);
        message.setReaded(1);
        messageMapper.update(message);
        return "已读成功";
    }

    public Message queryById(int msgId){
        return messageMapper.queryById(msgId);
    }

    public String add(Message msg){
        messageMapper.add(msg);
        return "添加消息成功";
    }

    public String udpate(Message msg){
        messageMapper.update(msg);
        return "OK";
    }

    public String delete(int msgId){
        messageMapper.delete(msgId);
        return "OK";
    }
}
