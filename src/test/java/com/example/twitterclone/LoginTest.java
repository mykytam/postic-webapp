package com.example.twitterclone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class) // анотация JUnit, указываем окружение, где стартует тест
@SpringBootTest // комбо анотация, указывает, что запускаем тесты в окружении Spring Boot приложения
@AutoConfigureMockMvc // Spring автоматические создаёт структуру классов подменяя слой Mvc
public class LoginTest {
    @Autowired
    private MockMvc mockMvc;

    @Test // помечает тестовые методы
    public void contextLoads() throws Exception { // может бросать любые исключения
        this.mockMvc.perform(get("/")) // get запрос на главную страницу проекта
                .andDo(print()) // полученый результат в консоль
                .andExpect(status().isOk()) // сравнение результата, ждём http 200
                .andExpect(content().string(containsString("Hello, guest!"))); // сравниванием строки
    }

    @Test
    public void accessDeniedTest() throws Exception { // проверка, что приложение попросит авторизацию, если пользователь не авторизован
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(status().is3xxRedirection()) // система ожидает статус отличный, от 200
                .andExpect(redirectedUrl("http://localhost/login")); // подкидывает адрес
    }

    @Test
    public void correctLoginTest() throws Exception { // проверка авторизации
        this.mockMvc.perform(formLogin().user("Jia").password("69")) // вызывает обращение к страничке
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void badCredentials() throws Exception { // проверка отбивки на некоректные данные пользователя
        this.mockMvc.perform(post("/login").param("user", "Jia")) // вызывает обращение к страничке
                .andDo(print())
                .andExpect(status().isForbidden()); // ждем 403
    }
}
