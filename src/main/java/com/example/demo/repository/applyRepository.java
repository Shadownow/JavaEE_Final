package com.example.demo.repository;

import com.example.demo.bean.apply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface applyRepository extends JpaRepository<apply, Integer> {
    public List<apply> findAllByStudentIdAndState(String studentId,String state);
    public List<apply> findAllByCourseIdAndState(String courseId,String state);
    public apply findByCourseIdAndStudentId(String courseId,String studentId);
    public List<apply> findAllByStudentId(String studentId);

}
