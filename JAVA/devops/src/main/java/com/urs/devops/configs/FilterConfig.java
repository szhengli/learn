package com.urs.devops.configs;

import com.urs.devops.filters.ServiceFilter;
import com.urs.devops.filters.ServiceFilter2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean registFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ServiceFilter());
        registration.addUrlPatterns("/*");
        registration.setName("serviceFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean registFilter2() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ServiceFilter2());
        registration.addUrlPatterns("/*");
        registration.setName("serviceFilter2");
        registration.setOrder(2);
        return registration;
    }

}
