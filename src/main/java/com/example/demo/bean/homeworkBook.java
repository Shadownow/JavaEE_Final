package com.example.demo.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="homeworkBook")
public class homeworkBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int number;
    private String homeworkId;
    private String studentId;
    private String answer;
    private String state;
    private String result;

    public String getHomeworkId() {
        return homeworkId;
    }
    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }
    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }

}
