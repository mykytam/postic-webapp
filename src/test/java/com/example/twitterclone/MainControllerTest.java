package com.example.twitterclone;

import com.example.twitterclone.controller.MainController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class) // анотация JUnit, указываем окружение, где стартует тест
@SpringBootTest // комбо анотация, указывает, что запускаем тесты в окружении Spring Boot приложения
@AutoConfigureMockMvc // Spring автоматические создаёт структуру классов подменяя слой Mvc
@WithUserDetails("Jia") // пользователь, под которым выполняем тест
@TestPropertySource("/application-test.properties") // указывает, какие пропертя брать для тестов
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController controller;

    @Test
    public void mainPageTest() throws Exception { // корректное отображение имени пользователя
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated()) // работает, если установлена веб сессия
                .andExpect(MockMvcResultMatchers.xpath("//div[@id='navbarSupportedContent']/div").string("Jia")); // ищем в дерево атрибут id со значением
    }

    @Test
    public void messageListTest() throws Exception { // корректное отображение списка сообщений
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(MockMvcResultMatchers.xpath("").nodeCount(0)); // ожидаем какое-то к-во элементов на странице
    }
}
