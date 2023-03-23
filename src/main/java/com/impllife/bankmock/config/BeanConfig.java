package com.impllife.bankmock.config;

import com.impllife.bankmock.services.*;
import com.impllife.bankmock.services.inMemoryRepo.InMemoryBankAccountRepo;
import com.impllife.bankmock.services.inMemoryRepo.InMemoryBillingRepo;
import com.impllife.bankmock.services.inMemoryRepo.InMemoryBusinessAppRepo;
import com.impllife.bankmock.services.inMemoryRepo.InMemoryClientRepo;
import com.impllife.bankmock.services.interfaces.*;
import com.impllife.bankmock.services.jpa.mgr.DBBankAccountRepo;
import com.impllife.bankmock.services.jpa.mgr.DBBillingRepo;
import com.impllife.bankmock.services.jpa.mgr.DBBusinessAppRepo;
import com.impllife.bankmock.services.jpa.mgr.DBClientRepo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

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

    //region Springfox & actuator conflict fix
    @Bean
    public BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                List<T> copy = mappings.stream()
                    .filter(mapping -> mapping.getPatternParser() == null)
                    .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            @SuppressWarnings("unchecked")
            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }
    //endregion
}
