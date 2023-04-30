package com.example.application.Config;

import com.example.application.Security.CustomUserDetailsService;
import com.example.application.Security.JWTAuthenticationEntryPoint;
import com.example.application.SiteController.JWTRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER")
                .and()
                .withUser("admin").password("{noop}password").roles("ADMIN");
    }
    @Autowired
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JWTRequestFilter jwtRequestFilter;

    public SpringSecurity(JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          CustomUserDetailsService customUserDetailsService,
                          JWTRequestFilter jwtRequestFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    public static SpringSecurity createWebSecurityConfig(JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                                                         CustomUserDetailsService customUserDetailsService,
                                                         JWTRequestFilter jwtRequestFilter) {
        return new SpringSecurity(jwtAuthenticationEntryPoint, customUserDetailsService, jwtRequestFilter);

        }
}