package com.example.twitterclone.controller;

import com.example.twitterclone.domain.Message;
import com.example.twitterclone.domain.User;
import com.example.twitterclone.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
                       @Valid Message message,
                       BindingResult bindingResult, // список аргументов и сообщение ошибок валидации
                       Model model, // @RequestParam выдергивает с запросов (либо форма, либо url) значения
                       @RequestParam("file") MultipartFile file ) throws IOException {
        // сохранил
       message.setAuthor(user);
       if (bindingResult.hasErrors()) { // проверка ошибок bindingResult, сообщения не сохранять
           Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

           model.mergeAttributes(errorsMap);
           model.addAttribute("message", message);
       } else {
           if (file != null && !file.getOriginalFilename().isEmpty()) {
               File uploadDir = new File(uploadPath);
               if (!uploadDir.exists()) {
                   uploadDir.mkdir();
               }

               String uuidfile = UUID.randomUUID().toString();
               String resultFileName = uuidfile + "." + file.getOriginalFilename();
               file.transferTo(new File(uploadPath + "/" + resultFileName));
               message.setFilename(resultFileName);
           }

           model.addAttribute("message", null); // удаления message из модели после валидации, чтобы после добавления не получить открытую форму
           messageRepo.save(message);
       }
        // изъял из репозитория, положил в модель, отдал пользователю
        Iterable<Message> messages = messageRepo.findAll();
        model.addAttribute("messages", messages);
       // model.put("filter", "");
        return "main";
    }


}