package com.implementLife.BankMock.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class SecurityBaseConfig /*extends WebSecurityConfigurerAdapter*/ {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityBaseConfig.class);

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return (String) rawPassword;
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.equals(encodedPassword);
            }
        };
    }

//    @Bean
//    public AuthenticationManager authManager(HttpSecurity http, CustomAuthenticationProvider authProvider) throws Exception {
//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.authenticationProvider(authProvider);
//        return authenticationManagerBuilder.build();
//    }

//    @Bean
//    public AccessDeniedHandler customAccessDeniedHandler() {
//        return new CustomAccessDeniedHandler();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity https) throws Exception {
        https
            .authorizeRequests(a -> a
                .antMatchers("/api/**").hasRole("USER")
                .antMatchers("/profile/**").hasRole("USER")
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(fl -> fl
                .loginPage("/login")
                .loginProcessingUrl("/j_spring_security_check")
                .failureUrl("/login?error")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .permitAll()
            )
            .logout(l -> l
                .logoutSuccessUrl("/login").permitAll()
            );
        return https.build();
    }

    /*@Override
    protected void configure(HttpSecurity https) throws Exception {
        https
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeRequests(a -> a
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/api/**").hasRole("USER")
//                .antMatchers("/**").hasRole("R")
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(fl -> fl
                .loginPage("/login")
                .loginProcessingUrl("/j_spring_security_check")
                .failureUrl("/login?error")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .permitAll()
            )
            .logout(l -> l
                .logoutSuccessUrl("/login").permitAll()
            );
    }*/
}
