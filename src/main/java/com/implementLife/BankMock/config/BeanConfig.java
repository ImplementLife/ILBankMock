package com.implementLife.BankMock.config;

import com.implementLife.BankMock.services.*;
import com.implementLife.BankMock.services.inMemoryRepo.InMemoryBankAccountRepo;
import com.implementLife.BankMock.services.inMemoryRepo.InMemoryBillingRepo;
import com.implementLife.BankMock.services.inMemoryRepo.InMemoryBusinessAppRepo;
import com.implementLife.BankMock.services.inMemoryRepo.InMemoryClientRepo;
import com.implementLife.BankMock.services.interfaces.*;
import com.implementLife.BankMock.services.jpa.mgr.DBBankAccountRepo;
import com.implementLife.BankMock.services.jpa.mgr.DBBillingRepo;
import com.implementLife.BankMock.services.jpa.mgr.DBBusinessAppRepo;
import com.implementLife.BankMock.services.jpa.mgr.DBClientRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.awt.image.BufferedImage;
import java.util.Properties;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class BeanConfig {
    @Value("${impl_life.inMemoryRepo}")
    private boolean inMemoryRepo;

    @Bean
    public ClientRepo clientRepo() {
        if (inMemoryRepo) {
            return new InMemoryClientRepo();
        } else {
            return new DBClientRepo();
        }
    }
    @Bean
    public BankAccountRepo bankAccountRepo() {
        if (inMemoryRepo) {
            return new InMemoryBankAccountRepo();
        } else {
            return new DBBankAccountRepo();
        }
    }
    @Bean
    public BusinessAppRepo businessAppRepo() {
        if (inMemoryRepo) {
            return new InMemoryBusinessAppRepo();
        } else {
            return new DBBusinessAppRepo();
        }
    }
    @Bean
    public BillingRepo billingRepo() {
        if (inMemoryRepo) {
            return new InMemoryBillingRepo();
        } else {
            return new DBBillingRepo();
        }
    }

    @Bean
    public ClientService clientService() {
        return new ClientServiceImpl();
    }
    @Bean
    public ExternalApiService externalApiService() {
        return new ExternalApiServiceImpl();
    }
    @Bean
    public PaymentService paymentService() {
        return new PaymentServiceImpl();
    }

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("async-");
        executor.initialize();
        return executor;
    }
    @Bean
    public ImageCodeGenerator imageCodeGenerator() {
        return new ImageCodeGeneratorImpl();
    }
    @Bean
    public HttpMessageConverter<BufferedImage> BufferedImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }
    @Bean
    public MailService mailService() {
        return new MailServiceImpl();
    }
    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("ilfa.dp.ua");
        mailSender.setPort(25);

        mailSender.setUsername("no-reply@ilfa.dp.ua");
        mailSender.setPassword("37171");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

}
