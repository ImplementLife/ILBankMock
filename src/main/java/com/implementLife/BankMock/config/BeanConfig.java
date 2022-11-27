package com.implementLife.BankMock.config;

import com.implementLife.BankMock.data.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.awt.image.BufferedImage;
import java.util.Properties;

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
    @Bean
    public ImageCodeGenerator getImageCodeGenerator() {
        return new ImageCodeGeneratorImpl();
    }
    @Bean
    public HttpMessageConverter<BufferedImage> BufferedImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }
    @Bean
    public MailService getMailService() {
        return new MailServiceImpl();
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
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
