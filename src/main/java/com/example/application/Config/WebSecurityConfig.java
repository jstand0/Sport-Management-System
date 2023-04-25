package com.example.application.Config;

import com.example.application.Security.CustomUserDetailsService;
import com.example.application.Security.JWTAuthenticationEntryPoint;
import com.example.application.SiteController.JWTRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {


        private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;


        private CustomUserDetailsService customUserDetailsService;


        private JWTRequestFilter jwtRequestFilter;

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

                authProvider.setUserDetailsService(customUserDetailsService);
                authProvider.setPasswordEncoder(passwordEncoder());

                return authProvider;
        }


        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }


        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.cors().and().csrf().disable()

                        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                        .authorizeRequests().antMatchers("/authenticate").permitAll()
//                .antMatchers("/user/").hasAuthority("ADMIN") // <- LOOK AT ME!
                        .anyRequest().authenticated();
                http.authenticationProvider(authenticationProvider());

                http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);


                return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
                return authConfiguration.getAuthenticationManager();
        }
}
