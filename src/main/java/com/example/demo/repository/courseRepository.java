package com.example.demo.repository;

import com.example.demo.bean.course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface courseRepository extends JpaRepository<course, Integer> {
    public course findByCourseId (String courseId);
    public List<course> findAllByTeacherEmail (String findByAllTeacherEmail);
}
