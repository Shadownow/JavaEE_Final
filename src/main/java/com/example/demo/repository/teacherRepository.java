package com.example.demo.repository;

import com.example.demo.bean.teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface teacherRepository extends JpaRepository<teacher, Integer> {
    public teacher findByTeacherEmail(String teacherEmail);
}
