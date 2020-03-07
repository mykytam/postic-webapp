package com.example.twitterclone;

import com.example.twitterclone.controller.MainController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class) // анотация JUnit, указываем окружение, где стартует тест
@SpringBootTest // комбо анотация, указывает, что запускаем тесты в окружении Spring Boot приложения
@AutoConfigureMockMvc // Spring автоматические создаёт структуру классов подменяя слой Mvc
@WithUserDetails("dima") // пользователь, под которым выполняем тест
@TestPropertySource("/application-test.properties") // указывает, какие пропертя брать для тестов
@Sql(value = {"/create-user-before.sql", "/messages-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) // выполнить скрипт перед тестом
@Sql(value = {"/create-user-after.sql", "/messages-list-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) // выполнить скрипт после теста
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
                .andExpect(xpath("//div[@id='navbarSupportedContent']/div").string("dima")); // ищем в дерево атрибут id со значением
    }

    @Test
    public void messageListTest() throws Exception { // корректное отображение списка сообщений
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='message-list']/div").nodeCount(4)); // ожидаем какое-то к-во элементов на странице
    }

    @Test
    public void filterMessageTest() throws Exception { // корректная работа фильтр сообщений
        this.mockMvc.perform(get("/main").param("filter", "my-tag"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='message-list']/div").nodeCount(2))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='1']").exists()) // существование элемента
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='3']").exists());
    }

    @Test
    public void addMessageToListTest() throws Exception { // корректное добавление сообщений
        MockHttpServletRequestBuilder multipart = multipart("/main") //
                .file("file", "123".getBytes())
                .param("text", "fifth") // обязательный текст
                .param("tag", "new one") // обязательный тэг
                .with(csrf()); // csrf токены для проверки того, что клиент отправляет запросы

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='message-list']/div").nodeCount(5)) // к-во сообщений стало больше на 1
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='10']").exists()) // существование элемента с id = 10
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='10']/div/span").string("fifth")) // приложение возвращает текст
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='10']/div/i").string("#new one")); // приложение возвращает тэг
    }

}
