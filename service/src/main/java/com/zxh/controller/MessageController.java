package com.zxh.controller;

import com.zxh.pojo.Message;
import com.zxh.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/msg")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/queryAll")
    public List<Message> queryAll(){
        return messageService.queryAll();
    }

    @GetMapping("/queryByS/{userId}")
    public List<Message> queryByS(@PathVariable("userId") int userId){
        return messageService.queryByS(userId);
    }

    @GetMapping("/queryByRU/{userId}")
    public List<Message> queryByRU(@PathVariable("userId") int userId){
        return messageService.queryByRU(userId);
    }

    @GetMapping("/queryByRR/{userId}")
    public List<Message> queryByRR(@PathVariable("userId") int userId){
        return messageService.queryByRR(userId);
    }


    @GetMapping("/queryById/{msgId}")
    public Message queryById(@PathVariable("msgId") Integer msgId){
        return messageService.queryById(msgId);
    }

    @PostMapping("/add")
    public String add(@RequestBody Map<String,Object> reqMap){
        return messageService.add(getMsg(reqMap));
    }

    @PutMapping("/update")
    public String update(@RequestBody Map<String,Object> reqMap){
        Integer msgId=(int)reqMap.get("msgId");
        Message message=getMsg(reqMap);
        message.setMsgId(msgId);

        return messageService.udpate(message);
    }

    @DeleteMapping("/delete/{msgId}")
    public String delete(@PathVariable("msgId") Integer msgId){
        return messageService.delete(msgId);
    }

    private Message getMsg(Map<String,Object> map){
        System.out.println("获取到的map   "+map);

        int sender=(int)map.get("sender");
        int receiver=(int)map.get("receiver");
        String sendTime=map.get("sendTime").toString();
        String msgTheme=map.get("msgTheme").toString();
        String msgContent=map.get("msgContent").toString();
        Integer readed=(int)map.get("readed");
        Message message=new Message(0,sender,null,receiver,sendTime,msgTheme,msgContent,readed);

        System.out.println("重新生成的msg   "+message);
        return message;
    }

}
