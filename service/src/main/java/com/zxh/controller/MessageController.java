package com.zxh.controller;

import com.google.gson.Gson;
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

    private Gson gson=new Gson();

    @GetMapping("/queryAll")
    public String queryAll(){
        return gson.toJson(messageService.queryAll());
    }

    @GetMapping("/queryByS/{userId}")
    public String queryByS(@PathVariable("userId") int userId){
        return gson.toJson(messageService.queryByS(userId));
    }

    @GetMapping("/queryByRU/{userId}")
    public String queryByRU(@PathVariable("userId") int userId){
        return gson.toJson(messageService.queryByRU(userId));
    }

    @GetMapping("/queryByRR/{userId}")
    public String queryByRR(@PathVariable("userId") int userId){
        return gson.toJson(messageService.queryByRR(userId));
    }

    @GetMapping("/msgRead/{msgId}")
    public String msgRead(@PathVariable("msgId") int msgId){
        return messageService.msgRead(msgId);
    }

    @GetMapping("/queryById/{msgId}")
    public String queryById(@PathVariable("msgId") Integer msgId){
        return gson.toJson(messageService.queryById(msgId));
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
        Message message=new Message(0,sender,null,receiver,null,sendTime,msgTheme,msgContent,readed);

        System.out.println("重新生成的msg   "+message);
        return message;
    }

}
