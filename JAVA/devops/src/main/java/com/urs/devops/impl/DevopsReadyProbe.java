package com.urs.devops.impl;

import org.apache.dubbo.qos.probe.impl.DeployerReadinessProbe;
import org.apache.dubbo.rpc.model.ApplicationModel;

public class DevopsReadyProbe extends DeployerReadinessProbe {
    public DevopsReadyProbe(ApplicationModel applicationModel) {
        super(applicationModel);
    }
}
