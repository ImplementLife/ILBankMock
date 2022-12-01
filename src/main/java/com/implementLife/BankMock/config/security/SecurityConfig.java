package com.implementLife.BankMock.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);

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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity https) throws Exception {
        https
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeRequests(a -> a
                .antMatchers("/api/**").permitAll()
                .antMatchers("/profile/**").hasAnyRole("USER", "ADMIN", "MANAGER")
                .antMatchers("/manager/**").hasRole("MANAGER")
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(fl -> fl
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .failureUrl("/login?error")
                .usernameParameter("identification_key")
                .passwordParameter("password")
                .permitAll()
            )
            .logout(l -> l
                .logoutSuccessUrl("/login").permitAll()
            )
            .exceptionHandling(e -> e
                .accessDeniedPage("/access-denied")
            );
        return https.build();
    }

}
