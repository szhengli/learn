package com.urs.devops.mappers;

import com.urs.devops.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;

@Mapper
public interface StudentMapper {

    @Select("SELECT * FROM student WHERE name=#{name}")
    Student findByName(@Param("name") String name);
}
