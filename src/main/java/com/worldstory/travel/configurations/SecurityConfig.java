package com.worldstory.travel.configurations;

import com.worldstory.travel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    @Bean
    @Order(1)
    public SecurityFilterChain filterChainApp1(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity
                .securityMatcher("/admin/**")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll())
                .authenticationProvider(daoAuthenticationProvider())
                .formLogin(form -> form
                        .loginPage("/auth/login-admin")
                        .loginProcessingUrl("/auth/login-admin")
                );
        return httpSecurity.build();
    }

    @Bean
    public SecurityFilterChain filterChainApp2(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
                .authenticationProvider(daoAuthenticationProvider())
                .formLogin(form -> form.loginPage("/auth/login").permitAll()
                        .usernameParameter("email")
                        .passwordParameter("password")
                )
                .logout(logout -> logout.logoutSuccessUrl("/auth/login").permitAll())
        ;

        return httpSecurity.build();
    }
}
