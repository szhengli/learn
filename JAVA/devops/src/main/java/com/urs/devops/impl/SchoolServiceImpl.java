package com.urs.devops.impl;

import com.urs.devops.dao.StudentMapper;
import com.urs.devops.entity.Student;
import com.urs.devops.interfaces.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private StudentMapper studentMapper ;

    public Student haveClass(String name){

        return   studentMapper.findByName("james");
    }

}
