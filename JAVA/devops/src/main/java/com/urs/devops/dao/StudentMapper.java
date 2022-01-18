package com.urs.devops.dao;

import com.urs.devops.entity.Student;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StudentMapper {

    @Select("SELECT * FROM student WHERE name=#{name}")
    Student findByName(@Param("name") String name);

    @Insert("insert into student (name, place) values (#{name}, #{place})")
    void addStudent(@Param("name") String name, @Param("place") String place);

    @Select("SELECT * FROM student WHERE ${column}=#{name}")
    Student[] findByColumn(@Param("column") String column, @Param("name") String name );
}
