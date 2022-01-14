package com.urs.devops.dao;

import com.urs.devops.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentMapper {

    @Select("SELECT * FROM student WHERE name=#{name}")
    Student findByName(@Param("name") String name);

    @Select("SELECT * FROM student WHERE ${column}=#{name}")
    Student[] findByColumn(@Param("column") String column, @Param("name") String name );
}
