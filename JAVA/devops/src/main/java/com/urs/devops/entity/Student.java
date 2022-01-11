package com.urs.devops.entity;

public class Student {

    Integer id;
    String name;
    String place;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public Student(String name, String place){
        this.name = name;
        this.place = place;
    }
}
