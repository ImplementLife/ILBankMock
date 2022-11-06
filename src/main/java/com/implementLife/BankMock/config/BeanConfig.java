package com.implementLife.BankMock.config;

import com.implementLife.BankMock.data.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConfig {
    @Bean
    public ClientRepo getClientRepo() {
        return new InMemoryClientRepo();
    }
    @Bean
    public BankAccountRepo getBankAccountRepo() {
        return new InMemoryBankAccountRepo();
    }
    @Bean
    public ClientService getClientService() {
        return new ClientServiceImpl();
    }
    @Bean
    public BusinessAppRepo getBusinessAppRepo() {
        return new InMemoryBusinessAppRepo();
    }
    @Bean
    public ExternalApiService getExternalApiService() {
        return new ExternalApiServiceImpl();
    }
    @Bean
    public PaymentService getPaymentService() {
        return new PaymentServiceImpl();
    }
    @Bean
    public BillingRepo getBillingRepo() {
        return new InMemoryBillingRepo();
    }
}
