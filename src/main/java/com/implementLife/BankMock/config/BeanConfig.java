package com.implementLife.BankMock.config;

import com.implementLife.BankMock.data.ClientRepo;
import com.implementLife.BankMock.data.InMemoryClientRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConfig {
    @Bean
    @Scope("singleton")
    public ClientRepo getClientRepo() {
        return new InMemoryClientRepo();
    }
}
