package com.urs.devops;

import com.urs.devops.dao.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class StudentTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testFindStudentById() throws Exception {

        System.out.println("xxxxxxx test ");
        StudentMapper studentMapper = (StudentMapper) applicationContext.getBean("studentMapper");
        System.out.println(studentMapper.findByName("james").getPlace());


    }
}
