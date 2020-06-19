package com.example.demo.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="homework")
public class homework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int number;
    private String courseId;
    private String homeworkId;
    private String homeworkContent;
    private String homeworkTitle;
    private String deadline;

    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    public String getHomeworkId() {
        return homeworkId;
    }
    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getHomeworkContent() {
        return homeworkContent;
    }
    public void setHomeworkContent(String homeworkContent) {
        this.homeworkContent = homeworkContent;
    }

    public String getHomeworkTitle() {
        return homeworkTitle;
    }
    public void setHomeworkTitle(String homeworkTitle) {
        this.homeworkTitle = homeworkTitle;
    }

    public String getDeadline() {
        return deadline;
    }
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }


}
