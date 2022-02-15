package com.kingsun.redis.service;

import com.kingsun.redis.domain.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class Release {
    @Autowired
    private StringRedisTemplate template;

   public ArrayList<Map<String, String>>  get_all_branches(){
       String releasePat = "202*";
       Set<String> releaseList = new HashSet<>() ;
       ArrayList<Map<String, String>> data = new ArrayList<>();
       template.keys(releasePat).forEach((String release) ->{
           releaseList.add(release.substring(0,8));
       });

       Set<String> releaseSortedList =  new TreeSet<>(new Comparator<String>() {
           @Override
           public int compare(String o1, String o2) {
               return o2.compareTo(o1);
           }
       });

       releaseSortedList.addAll(releaseList);
       releaseSortedList.forEach((String branch)->{
           Map<String, String> entry =  new HashMap<>();
           entry.put("label", branch);
           entry.put("value", branch);
           data.add(entry);
       });

       return data;
   }





   public  ArrayList<Map<Object, Object>> get_release_records (String branch){
       Set<String> releaseSys = template.keys(branch+":*");
       ArrayList<Map<Object, Object>> releaseDetails = new ArrayList<>();

       releaseSys.forEach((String service) ->{
           Map<Object, Object>  details = template.opsForHash().entries(service);
           releaseDetails.add(details);
       });

       return releaseDetails;

   }



}
