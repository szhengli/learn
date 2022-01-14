package com.urs.devops.impl;

import com.urs.devops.dao.StudentMapper;
import com.urs.devops.entity.Student;
import com.urs.devops.interfaces.SchoolService;
import com.urs.devops.utils.Auditable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private StudentMapper studentMapper ;

    @Auditable(code = "2020")
    public Student haveClass(String name){
        return   studentMapper.findByName("james");
    }

}
