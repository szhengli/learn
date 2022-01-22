package com.urs.devops.impl;

import com.urs.devops.interfaces.StudentService;

public class StudentServiceImpl implements StudentService {
    @Override
    public String sayHello(String msg){
        return "Student beijing  dubbo service: " + msg ;
    }
}
