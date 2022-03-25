package com.urs.devops.entity;

import lombok.Data;
import org.apache.yetus.audience.InterfaceAudience;

@Data
public class Job {
   private String service;
   private String branch;
}
