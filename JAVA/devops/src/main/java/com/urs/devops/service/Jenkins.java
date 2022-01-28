package com.urs.devops.service;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.JobWithDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class Jenkins {

    @Value("${jenkins.url}")
    private  String jenkinsUrl ;
    @Value("${jenkins.username}")
    private  String username ;
    @Value("${jenkins.password}")
    private  String password ;

    private JenkinsServer getJk() throws URISyntaxException {
        return  new JenkinsServer(new URI(jenkinsUrl), username, password);
    }




    public int build() {

        /**
         String jenkinsUrl = "http://jenkins.cnzhonglunnet.com/";
         String username="zhengli", password = "110ae9c834f10e9cce7a2c99d779862731";
         **/

        try {
            JenkinsServer jenkinsServer = getJk() ;

            JobWithDetails job = jenkinsServer.getJob("prev5-zl-omsv5");
            int buildNumber = job.getNextBuildNumber();
            job.build();

            System.out.println("!!!!!!!!!!!!!!!!");
            System.out.println(buildNumber);
            System.out.println("!!!!!!!!!!!!!!!!");
            jenkinsServer.close();

            return  buildNumber;

        } catch (URISyntaxException | IOException exception) {
            System.out.println("fail to login jenkins");

            return 0;

        }

    }
}
