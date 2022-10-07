package com.implementLife.BankMock.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBaseConfig extends WebSecurityConfigurerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityBaseConfig.class);
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                String s = rawPassword.toString();
                LOG.info(s);
                LOG.info((String) rawPassword);
                return s;
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.equals(encodedPassword);
            }
        };
    }

    @Override
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
    }
}
