package com.caterpillar.shamil.SpringSecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <h3>SpringSecurity</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 13.04.2023
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("hello");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/logout").setViewName("login");
//        registry.addViewController("/user").setViewName("user");
//        registry.addViewController("/admin").setViewName("admin");
    }
}
