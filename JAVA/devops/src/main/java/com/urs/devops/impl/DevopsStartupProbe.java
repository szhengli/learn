package com.urs.devops.impl;

import org.apache.dubbo.qos.probe.impl.DeployerStartupProbe;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.springframework.stereotype.Component;


public class DevopsStartupProbe extends DeployerStartupProbe {
    public DevopsStartupProbe(ApplicationModel applicationModel) {
        super(applicationModel);
    }
}

