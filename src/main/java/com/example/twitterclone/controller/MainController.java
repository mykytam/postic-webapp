package com.example.twitterclone.controller;

import com.example.twitterclone.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.twitterclone.repos.MessageRepo;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String greeting(String name, Map<String, Object> model ) // то куда складывать данные, которые возвратятся пользователю
     { return "greeting"; }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages", messages);

        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {  // @RequestParam выдергивает с запросов (либо форма, либо url) значения
        // сохранил
        Message message = new Message(text, tag);
        messageRepo.save(message);
        // изъял из репозитория, положил в модель, отдал пользователю
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Message> messages;

        if(filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }

        model.put("messages", messages);
        return "main";
    }

}