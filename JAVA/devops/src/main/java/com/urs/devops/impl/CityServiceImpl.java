package com.urs.devops.impl;

import com.urs.devops.interfaces.CityService;

public class CityServiceImpl implements CityService {
    @Override
    public String buildRoad(String msg) {
        return "from city Service: " + msg;
    }
}
