package com.urs.devops.controllers;

import com.urs.devops.dao.StudentMapper;
import com.urs.devops.dao.StudentMapper2;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.urs.devops.entity.*;
//import com.urs.devops.mappers.StudentMapper2 ;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/demo")
public class DemoController {

    private static Logger logging = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private  StudentMapper2 studentMapper2;





    @RequestMapping("/say")
    public ModelAndView say(){

        Student student = studentMapper.findByName("james");
        Student student2 = studentMapper2.SelectStudent2(2);
        HashMap student3 = studentMapper2.SelectStudent3(3);

        Student[] student4 = studentMapper.findByColumn("place", "suzhou");
        System.out.println("///////////////////");

        System.out.println(student4);


        System.out.println(student3);
        System.out.println(student2);
        System.out.println(student);
        System.out.println("//////////////、、、、、/////");
      //  System.out.println(request.getURI());
        System.out.println("///////////////////");

        System.out.println("///xxx////////////////");

        for (Student s : student4) {
            System.out.println(s.getName());
        }

        System.out.println("////xxx///////////////");

        HashMap<String, String> s = new HashMap<String, String>();

        ModelAndView page = new ModelAndView();
        logging.info("xx");
        String[] names = {"server","ecs", "slb"};
        page.addObject("student",student);
        page.addObject("student2",student2);
        page.addObject("student3",student3);
        page.addObject("show", true);
        page.setViewName("hello");
        return page ;

    }


}
