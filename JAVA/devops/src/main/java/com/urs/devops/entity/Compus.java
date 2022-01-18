package com.urs.devops.entity;

import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
@Data
public class Compus implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private String name = "james";
}
