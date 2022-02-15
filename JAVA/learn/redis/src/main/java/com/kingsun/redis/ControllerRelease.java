package com.kingsun.redis;

import com.kingsun.redis.service.Release;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@CrossOrigin(origins = "http://127.0.0.1:8010")
@RestController
@RequestMapping("/svn")
public class ControllerRelease {

    @Autowired
    Release release;

    @GetMapping("/get_all_branches")
    public ArrayList<Map<String, String>>  get_all_branches(){
        ArrayList<Map<String, String>> allRelease = release.get_all_branches();
       return allRelease;
    }


    @PostMapping("/get_release_records")
    public  ArrayList<Map<Object, Object>> get_release_records(@RequestBody Map<String, String> data){
        String branch = data.get("branch");
        ArrayList<Map<Object, Object>> details = release.get_release_records(branch);
        return details;
    }





}
