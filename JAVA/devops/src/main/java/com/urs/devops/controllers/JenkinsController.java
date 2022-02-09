package com.urs.devops.controllers;

import com.urs.devops.service.Jenkins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin
@Controller
@RequestMapping("/jenkins")
public class JenkinsController {

    @Autowired
    private Jenkins jenkins;

    @RequestMapping("/build")
    @ResponseBody
    public int build(){
        return  jenkins.build("prev5-zl-omsv5");
    }



    @RequestMapping("/job")
    @ResponseBody
    public String job(){
        return  jenkins.job("prev5-zl-omsv5", "20240122");
    }


}
