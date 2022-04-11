package com.urs.devops.service;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.JobWithDetails;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
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

    public void changeBranch(String service, String branch) throws URISyntaxException, IOException, DocumentException {
            String jobName = "devv5-zl-actsv5";
            JenkinsServer jenkinsServer = getJk() ;
            String jobConfig = jenkinsServer.getJobXml(jobName);
            Document doc = DocumentHelper.parseText(jobConfig);
            Element root = doc.getRootElement();

            String branchPath = "/" + branch.substring(0,4) + "/" +  branch.substring(4,6) + "/" + branch;
            String url = "http://svn.cnzhonglunnet.com/svn/zlnet/code/project/branch" + branchPath + "/" + service + "@HEAD";

            Element remoteNode = (Element) doc.selectSingleNode("//definition/scm/locations/hudson.scm.SubversionSCM_-ModuleLocation/remote");
            remoteNode.setText(url);

            String newConfig =  doc.asXML();
            jenkinsServer.updateJob(jobName, newConfig);



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
