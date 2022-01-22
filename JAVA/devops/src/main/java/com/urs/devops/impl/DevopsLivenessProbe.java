package com.urs.devops.impl;

import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.qos.probe.LivenessProbe;

public class DevopsLivenessProbe implements LivenessProbe {
    @Override
    public boolean check() {
        return true;
    }
}
