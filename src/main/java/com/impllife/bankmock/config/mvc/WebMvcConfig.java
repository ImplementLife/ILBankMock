package com.impllife.bankmock.config.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("auth/login/index");
        registry.addViewController("/registration").setViewName("auth/registration/index");
        registry.addViewController("/access-denied").setViewName("auth/access-denied");
        registry.addViewController("/about").setViewName("about");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/res/**").addResourceLocations("content/pages/com/res/").setCachePeriod(0);
        registry.addResourceHandler("/favicon.ico").addResourceLocations("content/pages/com/res/img/favicon.ico").setCachePeriod(0);
    }

//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configure) {
//        configure.enable();
//    }

}
