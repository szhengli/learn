package com.urs.devops.impl;

import com.urs.devops.dao.StudentMapper;
import com.urs.devops.entity.Cloud;
import com.urs.devops.entity.Student;
import com.urs.devops.interfaces.SchoolService;
import com.urs.devops.utils.Auditable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private StudentMapper studentMapper ;

    @Autowired
    private Cloud cloud;

   //
  //  @Transactional
    @Auditable(code = "2020")
    public Student haveClass(String name){
        System.out.println("*******************");
        System.out.println(cloud.getName());
        System.out.println("*******************");
     //   studentMapper.addStudent("song", "qingdao");

        return   studentMapper.findByName(name);
    }


    @Auditable(code = "2020")
    public void haveClass2(Student student){
        System.out.println("*******************");
        System.out.println(cloud.getName());
        System.out.println("*******************");
        studentMapper.addStudent2(student);

    }


    @Auditable(code = "2020")
    public Student[] listStudents() {

        return  studentMapper.allStudents();
    }



}
