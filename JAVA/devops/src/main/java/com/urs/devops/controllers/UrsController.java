package com.urs.devops.controllers;

import com.urs.devops.aop.UsageTracked;
import com.urs.devops.entity.Student;
import com.urs.devops.dao.StudentMapper;
import com.urs.devops.dao.StudentMapper2;
import com.urs.devops.impl.SchoolServiceImpl;
import com.urs.devops.interfaces.SchoolService;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/urs")
public class UrsController {

    public UrsController(SchoolServiceImpl schoolService){
        this.schoolService = schoolService ;
    }
    private final SchoolServiceImpl schoolService ;

    @Transactional
    @GetMapping("/show/{name}")
    public Student show(@PathVariable String name){
        Student student = schoolService.haveClass(name);
        UsageTracked usageTracked = (UsageTracked) schoolService;
        usageTracked.increaseUseCount();
        System.out.println("increased");
        return  student;
    }




}
