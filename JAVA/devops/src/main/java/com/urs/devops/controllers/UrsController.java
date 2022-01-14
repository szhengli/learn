package com.urs.devops.controllers;

import com.urs.devops.entity.Student;
import com.urs.devops.dao.StudentMapper;
import com.urs.devops.dao.StudentMapper2;
import com.urs.devops.interfaces.SchoolService;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/urs")
public class UrsController {

    @Autowired
    private SchoolService schoolService ;

    @RequestMapping("/show")
    public Student show(){
        return  schoolService.haveClass("james");
    }



}
