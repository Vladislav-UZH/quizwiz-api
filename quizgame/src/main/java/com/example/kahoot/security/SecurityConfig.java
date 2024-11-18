package com.example.kahoot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//должен быть настроен для разрешения определённых URL-адресов, защищённых JWT
public class SecurityConfig {

    private final JwtTokenProvider tokenProvider;

    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtTokenProvider tokenProvider, UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService; // Инициализация UserDetailsService
    }
    @Bean
    //метод настраивает правила безопасности для вашего приложения
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Отключение CSRF-защиты, если необходимо
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Открытые маршруты для регистрации и входа
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Только для администраторов
                        .anyRequest().authenticated() // Доступ для аутентифицированных пользователей
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Без сохранения сессии на сервере
                .httpBasic(Customizer.withDefaults()); // Включение базовой формы входа

        return http.build();
    }

    @Bean
    //Используется для хэширования паролей
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    //метод настраивает механизм аутентификации
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .inMemoryAuthentication() // Использование in-memory аутентификации для примера
                .withUser ("user").password(passwordEncoder().encode("password")).roles("USER")
                .and()
                .withUser ("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
        return authenticationManagerBuilder.build();
    }
}
