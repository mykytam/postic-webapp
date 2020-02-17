package com.example.twitterclone.controller;

import com.example.twitterclone.domain.Message;
import com.example.twitterclone.domain.User;
import com.example.twitterclone.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private MessageRepo messageRepo;

    @Value("${upload.path}") //получение переменной
    private String uploadPath;

    @GetMapping("/")
    public String greeting(String name, Map<String, Object> model ) // то куда складывать данные, которые возвратятся пользователю
    { return "greeting"; }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages = messageRepo.findAll();

        if(filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String add( @AuthenticationPrincipal User user,
                       @RequestParam String text,
                       @RequestParam String tag, Map<String, Object> model, // @RequestParam выдергивает с запросов (либо форма, либо url) значения
                       @RequestParam("file") MultipartFile file ) throws IOException {
        // сохранил
        Message message = new Message(text, tag, user);
        if (file!= null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidfile = UUID.randomUUID().toString();
            String resultFileName = uuidfile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));
            message.setFilename(resultFileName);
        }
        messageRepo.save(message);
        // изъял из репозитория, положил в модель, отдал пользователю
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        model.put("filter", "");
        return "main";
    }
}