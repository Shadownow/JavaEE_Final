package com.example.demo.repository;

import com.example.demo.bean.homework;
import com.example.demo.bean.homeworkBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface homeworkBookRepository extends JpaRepository<homeworkBook, Integer> {
    public homeworkBook findByHomeworkIdAndStudentId(String homeworkId,String studentId);
    public List<homeworkBook> findAllByHomeworkId(String homeworkId);
}
