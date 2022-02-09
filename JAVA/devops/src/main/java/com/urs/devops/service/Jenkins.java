package com.urs.devops.service;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.JobWithDetails;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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

    public String job (String jobName, String newBranch)  {
        try{
            JenkinsServer jenkinsServer = getJk() ;

            String year = newBranch.substring(0,4);
            String month =  newBranch.substring(4,6);
            String[] nameArray = jobName.split("-");

            String serviceName = nameArray[nameArray.length-1];


            String jobPath = jenkinsUrl + "/svn/zlnet/code/project/branch/" +
                              year + "/" + month + "/" + newBranch + "/" + serviceName + "@HEAD";

            String jobConfigStr = jenkinsServer.getJobXml("prev5-zl-omsv5");
            Document config = DocumentHelper.parseText(jobConfigStr);
            Node svnPath = config.selectSingleNode("//flow-definition/definition/scm/locations" +
                                                   "/hudson.scm.SubversionSCM_-ModuleLocation/remote");
            svnPath.setText(jobPath);
            String newConfig = config.asXML();
            jenkinsServer.updateJob("prev5-zl-omsv5",newConfig);
            System.out.println(newConfig);
            jenkinsServer.close();

            return  newConfig;

        }catch (URISyntaxException | IOException | DocumentException e) {
            System.out.println(e);
            return "None";
        }



    }



    public int build(String jobName) {
        try {
            JenkinsServer jenkinsServer = getJk() ;
            JobWithDetails job = jenkinsServer.getJob(jobName);
            int buildNumber = job.getNextBuildNumber();
            job.build();

            jenkinsServer.close();
            return  buildNumber;

        } catch (URISyntaxException | IOException exception) {
            System.out.println("fail to login jenkins");

            return 0;

        }

    }
}
