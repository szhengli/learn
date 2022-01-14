package utis;

import java.io.Serializable;

public class Employee implements Serializable {
    public String name ;
    public String address ;
    public transient int SSN;
    public int number;
    public String place;

    public Employee(){

    }
    public Employee(String name, String address){
        this.name = name ;
        this.address = address ;
    }
    public void mailCheck(){
        System.out.println("Mail to :" + name + " " + address );
    }
}
