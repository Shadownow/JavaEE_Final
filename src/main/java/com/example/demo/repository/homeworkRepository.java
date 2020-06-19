package com.example.demo.repository;

import com.example.demo.bean.homework;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface homeworkRepository extends JpaRepository<homework, Integer> {
    public homework findByHomeworkId(String homeworkId);
    public List<homework> findAllByCourseId(String courseId);
}
