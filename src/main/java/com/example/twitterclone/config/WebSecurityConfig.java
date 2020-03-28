package com.example.twitterclone.config;

import com.example.twitterclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // класс конфигурирует web security
    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8); // характеризует надежность ключа шифрования
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // включение авторизации
                    .antMatchers("/", "/registration", "/static/**", "/activate/*").permitAll() // для этого запроса есть доступ всем
                    .anyRequest().authenticated() // для остальных запросов требуем авторизацию
                .and()
                    .formLogin() // включение form Login
                    .loginPage("/login") // указываем, что loginPage находится на таком мепинге
                    .permitAll() // разрешение пользоваться всем
                .and()
                    .rememberMe() // сохранение сессий
                .and()
                    .logout() // выход
                    .permitAll(); // разрешение пользоваться всем
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(getPasswordEncoder());
    }
}