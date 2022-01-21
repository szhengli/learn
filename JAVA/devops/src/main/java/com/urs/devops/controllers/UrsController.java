package com.urs.devops.controllers;

import com.urs.devops.aop.UsageTracked;
import com.urs.devops.entity.Student;
import com.urs.devops.dao.StudentMapper;
import com.urs.devops.dao.StudentMapper2;
import com.urs.devops.impl.SchoolServiceImpl;
import com.urs.devops.interfaces.SchoolService;
import com.urs.devops.utils.StudentNotFindException;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
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
    @GetMapping("/students/{name}")
    public ResponseEntity<Object> show(@PathVariable String name){
        Student student = schoolService.haveClass(name);

        if (student == null) {
            throw new StudentNotFindException();
        }
        UsageTracked usageTracked = (UsageTracked) schoolService;
        usageTracked.increaseUseCount();

        System.out.println("increased");
        ResponseEntity<Object>  res = new ResponseEntity<>(student, HttpStatus.OK);
        return res ;
    }

    @PostMapping("/students")
    public ResponseEntity<Object> addStudent(@RequestBody Student student){
        System.out.println(student);
        schoolService.haveClass2(student);
        return new ResponseEntity<>("added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/students")
    public ResponseEntity<Object>  listStudents(){

        Student[] students = schoolService.listStudents();

        ResponseEntity<Object>  res = new ResponseEntity<>(students, HttpStatus.OK);
        return res ;
    }


}
