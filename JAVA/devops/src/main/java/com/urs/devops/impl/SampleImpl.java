package com.urs.devops.impl;

import com.urs.devops.interfaces.Sample;

public class SampleImpl implements Sample<String> {

    public  void take(String place) {
        System.out.println("take ********** ");
    }

    public void pull (String place) {
        System.out.println("pull --------------");
    }
}
