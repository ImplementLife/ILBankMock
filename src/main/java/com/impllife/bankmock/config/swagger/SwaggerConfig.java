package com.impllife.bankmock.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Value("${impl_life.swagger.version}")
    private String version;
    @Value("${impl_life.baseUrl}")
    private String baseUrl;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
            .securityContexts(List.of(securityContext()))
            .securitySchemes(List.of(apiKey()))
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.implementLife.BankMock.controller.rest"))
            .paths(PathSelectors.any())
            .build()
            .useDefaultResponseMessages(false)
            .apiInfo(apiInfo());
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "JSESSIONID", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        List<AuthorizationScope> authorizationScopes1 = List.of(new AuthorizationScope("global", "accessEverything"));
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
            "Bank Mock",
            "Mock Banking API for test payment system without real money",
            version,
            "",
            new Contact("", "", "implementlifep@gmail.com"),
            "", "",
            Collections.emptyList());
    }

    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }
}
