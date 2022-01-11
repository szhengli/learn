package com.urs.devops.mappers;

import com.urs.devops.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMapper2 {
  Student SelectStudent2(int id);
}
