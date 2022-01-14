package com.urs.devops.controllers;

import com.urs.devops.entity.Student;
import com.urs.devops.dao.StudentMapper;
import com.urs.devops.dao.StudentMapper2;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/urs")
public class UrsController {
    @Autowired
    private  SqlSessionTemplate sqlSessionTemplate;

   @Autowired
    private  StudentMapper studentMapper;

    @Autowired
    private  StudentMapper2 studentMapper2;

    @RequestMapping("/show")
    public Student show(){
        Student student = studentMapper.findByName("james");
        Student student2 = studentMapper2.SelectStudent2(2);
        return  student;
    }



}
