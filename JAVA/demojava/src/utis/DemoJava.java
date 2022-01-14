package utis;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;



public class DemoJava {

    public static String getValue(String key) throws IOException{
        Properties pro = new Properties();
        FileReader in = new FileReader("c:\\web\\learn\\JAVA\\demojava\\src\\utis\\pro.txt");
        pro.load(in);
        in.close();
        return pro.getProperty(key);
    }

    public static void main(String[] args)   {

        String[] params = {"james", "suzhou"};

    try {



        Class employeeClass = Class.forName(getValue("name"));

        Constructor[] conArray = employeeClass.getConstructors();

        for (Constructor c : conArray) {
            System.out.println(c);
        }

        Object object= employeeClass.getConstructors()[1].newInstance( params);

        Method m = employeeClass.getMethod("mailCheck");
        m.invoke(object);

    } catch (Exception e){
        System.out.println(e);
    }























    }
}
