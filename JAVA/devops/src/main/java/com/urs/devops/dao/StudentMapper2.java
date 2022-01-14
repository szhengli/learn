package com.urs.devops.dao;

import com.urs.devops.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;


@Mapper
public interface StudentMapper2 {
  Student SelectStudent2(int id);
  HashMap SelectStudent3(int id);

}
