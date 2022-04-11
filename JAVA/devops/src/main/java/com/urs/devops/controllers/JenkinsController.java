package com.urs.devops.controllers;

import com.urs.devops.entity.Job;
import com.urs.devops.service.Jenkins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/jenkins")
public class JenkinsController {

    @Autowired
    private Jenkins jenkins;

    @RequestMapping("/build")
    @ResponseBody
    public int build(){
        return  jenkins.build();
    }

    @PostMapping("/job/changeBranch.do")
    @ResponseBody
    public  Map<String, Boolean> changeBranch(@RequestBody Job job){
        Map<String, Boolean> res = new HashMap<>();
        try {
            jenkins.changeBranch(job.getService(), job.getBranch()) ;
            res.put("ok", true);
        } catch (Exception ignored) {
            res.put("ok", false);
        }
        return res;
    }
}
