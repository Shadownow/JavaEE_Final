package com.example.demo.repository;

import com.example.demo.bean.student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface studentRepository extends JpaRepository<student, Integer> {
    public student findByStudentEmail(String email);
    public student findByStudentId(String studentId);
}
