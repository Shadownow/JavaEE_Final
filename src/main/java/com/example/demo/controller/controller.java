package com.example.demo.controller;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.*;
import com.example.demo.repository.*;
import com.example.demo.util.Password;
import com.example.demo.util.SentEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class controller {
    @Autowired
    applyRepository applyRepository;
    @Autowired
    courseRepository courseRepository;
    @Autowired
    homeworkRepository homeworkRepository;
    @Autowired
    nameListRepository nameListRepository;
    @Autowired
    studentRepository studentRepository;
    @Autowired
    teacherRepository teacherRepository;
    @Autowired
    homeworkBookRepository homeworkBookRepository;
    @ResponseBody
    @RequestMapping(value="/studentRegisterVerification")
    public JSONObject studentRegisterVerification(HttpServletRequest data){
        System.out.println("进入接口");
        String studentEmail = data.getParameter("studentEmail");
        String studentId = data.getParameter("studentId");
        String studentName = data.getParameter("studentName");
        String password = data.getParameter("password");
        if(studentEmail==null||studentEmail.length()==0){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "邮箱不能为空");
            return object;
        }
        else if(studentId==null||studentId.length()==0){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "学号不能为空");
            return object;
        }
        else if(studentName==null||studentName.length()==0){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "姓名不能为空");
            return object;
        }
        else if(password==null||password.length()==0){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "密码不能为空");
            return object;
        }
        else if(studentRepository.findByStudentEmail(studentEmail)!=null){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "该邮箱已被注册");
            return object;
        }
        else if(studentRepository.findByStudentId(studentId)!=null){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "该学号已存在");
            return object;
        }
        else{
            SentEmail sentEmail = new SentEmail();
            return sentEmail.sendMail(studentEmail);
        }
    }

    @ResponseBody
    @RequestMapping(value="/studentRegister")
    public JSONObject studentRegister(HttpServletRequest data){
        String studentEmail = data.getParameter("studentEmail");
        String studentId = data.getParameter("studentId");
        String studentName = data.getParameter("studentName");
        String password = Password.getMD5(data.getParameter("password"));
        student student = new student();
        student.setPassword(password);
        student.setStudentEmail(studentEmail);
        student.setStudentId(studentId);
        student.setStudentName(studentName);
        studentRepository.save(student);
        JSONObject object = new JSONObject();
        object.put("response", "false");
        object.put("result", "成功");
        return object;
    }

    @ResponseBody
    @RequestMapping(value="/teacherRegisterVerification")
    public JSONObject teacherRegisterVerification(HttpServletRequest data){
        System.out.println("进入接口");
        String teacherName = data.getParameter("teacherName");
        String teacherEmail = data.getParameter("teacherEmail");
        String password = data.getParameter("password");
        if(teacherEmail==null||teacherEmail.length()==0){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "邮箱不能为空");
            return object;
        }
        else if(teacherName==null||teacherName.length()==0){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "姓名不能为空");
            return object;
        }
        else if(password==null||password.length()==0){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "密码不能为空");
            return object;
        }
        else if(teacherRepository.findByTeacherEmail(teacherEmail)!=null){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "该邮箱已被注册");
            return object;
        }
        else{
            SentEmail sentEmail = new SentEmail();
            return sentEmail.sendMail(teacherEmail);
        }
    }

    @ResponseBody
    @RequestMapping(value="/teacherRegister")
    public JSONObject teacherRegister(HttpServletRequest data){
        String teacherName = data.getParameter("teacherName");
        String teacherEmail = data.getParameter("teacherEmail");
        String password = Password.getMD5(data.getParameter("password"));
        teacher teacher = new teacher();
        teacher.setPassword(password);
        teacher.setTeacherEmail(teacherEmail);
        teacher.setTeacherName(teacherName);
        teacherRepository.save(teacher);
        JSONObject object = new JSONObject();
        object.put("response", "true");
        object.put("result", "成功");
        return object;
    }

    @ResponseBody
    @RequestMapping(value="/StudentLogin")
    public JSONObject StudentLogin(HttpServletRequest data){
        String email = data.getParameter("email");
        String password = Password.getMD5(data.getParameter("password"));
        if(studentRepository.findByStudentEmail(email)==null){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "邮箱不存在");
            return object;
        }
        else if(studentRepository.findByStudentEmail(email).getPassword().equals(password)){
            JSONObject object = new JSONObject();
            object.put("response", "true");
            object.put("result", "登录成功，跳转中");
            return object;
        }
        else if(!studentRepository.findByStudentEmail(email).getPassword().equals(password)){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "密码错误");
            return object;
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value="/TeacherLogin")
    public JSONObject TeacherLogin(HttpServletRequest data){
        String email = data.getParameter("email");
        String password = Password.getMD5(data.getParameter("password"));
        if(teacherRepository.findByTeacherEmail(email)==null){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "邮箱不存在");
            return object;
        }
        else if(teacherRepository.findByTeacherEmail(email).getPassword().equals(password)){
            JSONObject object = new JSONObject();
            object.put("response", "true");
            object.put("result", "登录成功，跳转中");
            return object;
        }
        else if(!teacherRepository.findByTeacherEmail(email).getPassword().equals(password)){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "密码错误");
            return object;
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value="/CreateCourse")
    public JSONObject CreateCourse(HttpServletRequest data){
        String email = data.getParameter("email");
        String courseId = data.getParameter("courseId");
        String courseName = data.getParameter("courseName");
        if(courseId.length()==0){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "课程号不能为空");
            return object;
        }
        else if(courseName.length()==0){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "课程名不能为空");
            return object;
        }
        else if(courseRepository.findByCourseId(courseId)==null){
            course course = new course();
            course.setCourseId(courseId);
            course.setCourseName(courseName);
            course.setTeacherEmail(email);
            courseRepository.save(course);
            JSONObject object = new JSONObject();
            object.put("response", "true");
            object.put("result", "创建成功");
            return object;
        }
        else{
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "该课程号已存在");
            return object;
        }
    }

    @ResponseBody
    @RequestMapping(value="/PublishHomework")
    public JSONObject PublishHomework(HttpServletRequest data){
        String email = data.getParameter("email");
        String courseId = data.getParameter("courseId");
        String homeworkId = data.getParameter("homeworkId");
        String homeworkTitle = data.getParameter("homeworkTitle");
        String homeworkContent = data.getParameter("homeworkContent");
        String deadline = data.getParameter("deadline");
        if(courseRepository.findByCourseId(courseId)!=null){
            if(!courseRepository.findByCourseId(courseId).getTeacherEmail().equals(email)){
                JSONObject object = new JSONObject();
                object.put("response", "true");
                object.put("result", "对当前班级无操作权限");
                return object;
            }
        }
        if(courseId.length()==0){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "课程号不能为空");
            return object;
        }
        else if(homeworkId.length()==0){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "作业号不能为空");
            return object;
        }
        else if(homeworkTitle.length()==0){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "作业标题不能为空");
            return object;
        }else if(homeworkContent.length()==0){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "作业内容不能为空");
            return object;
        }
        else if(deadline.length()==0){
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "截止日期不能为空");
            return object;
        }
        else if(courseRepository.findByCourseId(courseId)==null){
            JSONObject object = new JSONObject();
            object.put("response", "true");
            object.put("result", "班级不存在");
            return object;
        }

        else if(homeworkRepository.findByHomeworkId(homeworkId)!=null){
            JSONObject object = new JSONObject();
            object.put("response", "true");
            object.put("result", "作业号已存在");
            return object;
        }
        else {
            homework homework = new homework();
            homework.setCourseId(courseId);
            homework.setDeadline(deadline);
            homework.setHomeworkContent(homeworkContent);
            homework.setHomeworkId(homeworkId);
            homework.setHomeworkTitle(homeworkTitle);
            homeworkRepository.save(homework);
            JSONObject object = new JSONObject();
            object.put("response", "true");
            object.put("result", "发布成功");
            return object;
        }

    }

    @ResponseBody
    @RequestMapping(value="/QueryCourse")
    public String QueryCourse(HttpServletRequest data){
        System.out.println("bushiwodecuo");
        List<JSONObject> jsonlist=new ArrayList<JSONObject>();
        String email = data.getParameter("email");
        System.out.println(email);
        student student = studentRepository.findByStudentEmail(email);
        List<course> courses = courseRepository.findAll();
        List<apply> applies = applyRepository.findAllByStudentId(student.getStudentId());
        for(int i = 0; i<courses.size();i++){
            int x= 0;
            jsonlist.add(new JSONObject());
            if(applies.size()==0){
                System.out.println("jinru");
                jsonlist.get(i).put("班级名", courses.get(i).getCourseName());
                jsonlist.get(i).put("班级号", courses.get(i).getCourseId());
                jsonlist.get(i).put("老师邮箱", courses.get(i).getTeacherEmail());
                jsonlist.get(i).put("老师名", teacherRepository.findByTeacherEmail(courses.get(i).getTeacherEmail()).getTeacherName());
                jsonlist.get(i).put("状态", "未加入");
            }else{
                for(int j=0; j<applies.size();j++){
                    if (courses.get(i).getCourseId().equals(applies.get(j).getCourseId())){
                        jsonlist.get(i).put("班级名", courses.get(i).getCourseName());
                        jsonlist.get(i).put("班级号", courses.get(i).getCourseId());
                        jsonlist.get(i).put("老师邮箱", courses.get(i).getTeacherEmail());
                        jsonlist.get(i).put("老师名", teacherRepository.findByTeacherEmail(courses.get(i).getTeacherEmail()).getTeacherName());
                        jsonlist.get(i).put("状态", applies.get(j).getState());
                        x=1;
                    }
                    else if(j==applies.size()-1&&x==0){
                        jsonlist.get(i).put("班级名", courses.get(i).getCourseName());
                        jsonlist.get(i).put("班级号", courses.get(i).getCourseId());
                        jsonlist.get(i).put("老师邮箱", courses.get(i).getTeacherEmail());
                        jsonlist.get(i).put("老师名", teacherRepository.findByTeacherEmail(courses.get(i).getTeacherEmail()).getTeacherName());
                        jsonlist.get(i).put("状态", "未加入");
                    }
                }
            }

        }
        return String.valueOf(jsonlist);
    }


    @ResponseBody
    @RequestMapping(value="/UpApply")
    public JSONObject UpApply(HttpServletRequest data){
        String email = data.getParameter("email");
        String courseId = data.getParameter("courseId");
        String state = "审批中";
        apply apply1 = new apply();
        if(applyRepository.findByCourseIdAndStudentId(courseId,studentRepository.findByStudentEmail(email).getStudentId())!=null){
            apply1 = applyRepository.findByCourseIdAndStudentId(courseId,studentRepository.findByStudentEmail(email).getStudentId());
            applyRepository.delete(apply1);
        }
        apply apply = new apply();
        apply.setCourseId(courseId);
        apply.setState(state);
        apply.setStudentId(studentRepository.findByStudentEmail(email).getStudentId());
        applyRepository.save(apply);
        JSONObject object = new JSONObject();
        object.put("response", "true");
        object.put("result", "申请成功");
        return object;
    }


    @ResponseBody
    @RequestMapping(value="/QueryApply")
    public String QueryApply(HttpServletRequest data){
        //学号 姓名 班级号
        int num=0;
        List<JSONObject> jsonlist=new ArrayList<JSONObject>();
        String email = data.getParameter("email");
        List<course> courses = courseRepository.findAllByTeacherEmail(email);
        for(int i=0;i<courses.size();i++){
            if(applyRepository.findAllByCourseIdAndState(courses.get(i).getCourseId(),"审批中")!=null){
                for(int j=0;j<applyRepository.findAllByCourseIdAndState(courses.get(i).getCourseId(),"审批中").size();j++){
                    jsonlist.add(new JSONObject());
                    jsonlist.get(num).put("学号", applyRepository.findAllByCourseIdAndState(courses.get(i).getCourseId(),"审批中").get(j).getStudentId());
                    jsonlist.get(num).put("姓名", studentRepository.findByStudentId(applyRepository.findAllByCourseIdAndState(courses.get(i).getCourseId(),"审批中").get(j).getStudentId()).getStudentName());
                    jsonlist.get(num).put("班级号", applyRepository.findAllByCourseIdAndState(courses.get(i).getCourseId(),"审批中").get(j).getCourseId());
                    num++;
                }

            }
        }
        return String.valueOf(jsonlist);
    }

    @ResponseBody
    @RequestMapping(value="/ProcessApply")
    public JSONObject ProcessApply(HttpServletRequest data){
        String state = data.getParameter("state");
        String courseId = data.getParameter("courseId");
        String studentId = data.getParameter("studentId");
        apply apply = new apply();
        apply = applyRepository.findByCourseIdAndStudentId(courseId,studentId);
        applyRepository.delete(apply);
        if(state.equals("Sure")){
            apply apply1 = new apply();
            apply1.setCourseId(courseId);
            apply1.setState("已加入");
            apply1.setStudentId(studentId);
            applyRepository.save(apply1);
        }else if(state.equals("Reject")){
            apply apply1 = new apply();
            apply1.setCourseId(courseId);
            apply1.setState("未加入");
            apply1.setStudentId(studentId);
            applyRepository.save(apply1);
        }

        JSONObject object = new JSONObject();
        object.put("response", "true");
        object.put("result", "处理成功");
        return object;
    }

    @ResponseBody
    @RequestMapping(value="/QueryHomework")
    public String QueryHomework(HttpServletRequest data){
        //学号 姓名 班级号
        int num=0;
        List<JSONObject> jsonlist=new ArrayList<JSONObject>();
        String email = data.getParameter("email");
        String studentId = studentRepository.findByStudentEmail(email).getStudentId();
        List<apply> applies = applyRepository.findAllByStudentIdAndState(studentId,"已加入");
        for(int i = 0;i<applies.size();i++){
            String courseId = applies.get(i).getCourseId();
            List<homework> homework = homeworkRepository.findAllByCourseId(courseId) ;
            for(int j = 0;j<homework.size();j++){
                jsonlist.add(new JSONObject());
                jsonlist.get(num).put("班级号",homework.get(j).getCourseId());
                jsonlist.get(num).put("作业号",homework.get(j).getHomeworkId());
                jsonlist.get(num).put("作业标题",homework.get(j).getHomeworkTitle());
                jsonlist.get(num).put("作业内容",homework.get(j).getHomeworkContent());
                jsonlist.get(num).put("截止时间",homework.get(j).getDeadline());


                if(homeworkBookRepository.findByHomeworkIdAndStudentId(homework.get(j).getHomeworkId(),studentId)==null){
                    jsonlist.get(num).put("状态","未提交");
                    jsonlist.get(num).put("分数","暂无");
                    jsonlist.get(num).put("回答","暂无");
                }
                else{
                    jsonlist.get(num).put("状态",homeworkBookRepository.findByHomeworkIdAndStudentId(homework.get(j).getHomeworkId(),studentId).getState());
                    jsonlist.get(num).put("分数",homeworkBookRepository.findByHomeworkIdAndStudentId(homework.get(j).getHomeworkId(),studentId).getResult());
                    jsonlist.get(num).put("回答",homeworkBookRepository.findByHomeworkIdAndStudentId(homework.get(j).getHomeworkId(),studentId).getAnswer());
                }
                num++;
            }
        }
        System.out.println(jsonlist);
        return String.valueOf(jsonlist);
    }
    @ResponseBody
    @RequestMapping(value="/UpHomework")
    public JSONObject UpHomework(HttpServletRequest data){
        String email = data.getParameter("email");
        String studentId = studentRepository.findByStudentEmail(email).getStudentId();
        String homeworkId = data.getParameter("homeworkId");
        String answer = data.getParameter("answer");
        homeworkBook homeworkBook1 = new homeworkBook();
        if(homeworkBookRepository.findByHomeworkIdAndStudentId(homeworkId,studentId)!=null){
            homeworkBook1 = homeworkBookRepository.findByHomeworkIdAndStudentId(homeworkId,studentId);
            homeworkBookRepository.delete(homeworkBook1);
        }
        homeworkBook homeworkBook = new homeworkBook();
        homeworkBook.setHomeworkId(homeworkId);
        homeworkBook.setStudentId(studentId);
        homeworkBook.setAnswer(answer);
        homeworkBook.setState("已提交");
        homeworkBook.setResult("暂无");
        homeworkBookRepository.save(homeworkBook);
        JSONObject object = new JSONObject();
        object.put("response", "true");
        object.put("result", "提交成功");
        return object;
    }
    @ResponseBody
    @RequestMapping(value="/QueryUpHomework")
    public String QueryUpHomework(HttpServletRequest data){
        //班级号 作业号 作业标题 作业内容 截止时间 提交人数
        int num=0;
        List<JSONObject> jsonlist=new ArrayList<JSONObject>();
        String email = data.getParameter("email");
        List<course> courses = courseRepository.findAllByTeacherEmail(email);
        for (int i=0;i<courses.size();i++){
            List<homework> homework = homeworkRepository.findAllByCourseId(courses.get(i).getCourseId());
            for(int j=0; j<homework.size();j++){
                jsonlist.add(new JSONObject());
                jsonlist.get(num).put("班级号",homework.get(j).getCourseId());
                jsonlist.get(num).put("作业号",homework.get(j).getHomeworkId());
                jsonlist.get(num).put("作业标题",homework.get(j).getHomeworkTitle());
                jsonlist.get(num).put("作业内容",homework.get(j).getHomeworkContent());
                jsonlist.get(num).put("截止时间",homework.get(j).getDeadline());
                List<apply> applies = applyRepository.findAllByCourseIdAndState(homework.get(j).getCourseId(),"已加入");
                List<homeworkBook> homeworkBooks = homeworkBookRepository.findAllByHomeworkId(homework.get(j).getHomeworkId());
                jsonlist.get(num).put("提交人数",homeworkBooks.size()+"/"+applies.size());
                num++;
            }
        }
        System.out.println(jsonlist);
        return String.valueOf(jsonlist);
    }



    @ResponseBody
    @RequestMapping(value="/QueryUpHomeworkDetails")
    public String QueryUpHomeworkDetails(HttpServletRequest data){
        int num=0;
        List<JSONObject> jsonlist=new ArrayList<JSONObject>();
        String email = data.getParameter("email");
        String courseId = data.getParameter("courseId");
        String homeworkId = data.getParameter("homeworkId");
        String homeworkTitle = data.getParameter("homeworkTitle");
        String homeworkContent = data.getParameter("homeworkContent");
        String deadline = data.getParameter("deadline");
        List<homeworkBook> homeworkBooks = homeworkBookRepository.findAllByHomeworkId(homeworkId);
        List<apply> applies = applyRepository.findAllByCourseIdAndState(courseId,"已加入");
        for(int i=0;i<applies.size();i++){
            if(homeworkBookRepository.findByHomeworkIdAndStudentId(homeworkId,applies.get(i).getStudentId())==null){
                jsonlist.add(new JSONObject());
                jsonlist.get(i).put("班级号",courseId);
                jsonlist.get(i).put("作业号",homeworkId);
                jsonlist.get(i).put("作业标题",homeworkTitle);
                jsonlist.get(i).put("作业内容",homeworkContent);
                jsonlist.get(i).put("截止时间",deadline);
                jsonlist.get(i).put("学号",applies.get(i).getStudentId());
                jsonlist.get(i).put("状态","未提交");
                jsonlist.get(i).put("答案","暂无");
                jsonlist.get(i).put("分数","暂无");
            }
            else{
                jsonlist.add(new JSONObject());
                jsonlist.get(i).put("班级号",courseId);
                jsonlist.get(i).put("作业号",homeworkId);
                jsonlist.get(i).put("作业标题",homeworkTitle);
                jsonlist.get(i).put("作业内容",homeworkContent);
                jsonlist.get(i).put("截止时间",deadline);
                jsonlist.get(i).put("学号",applies.get(i).getStudentId());
                jsonlist.get(i).put("状态",homeworkBookRepository.findByHomeworkIdAndStudentId(homeworkId,applies.get(i).getStudentId()).getState());
                jsonlist.get(i).put("答案",homeworkBookRepository.findByHomeworkIdAndStudentId(homeworkId,applies.get(i).getStudentId()).getAnswer());
                jsonlist.get(i).put("分数",homeworkBookRepository.findByHomeworkIdAndStudentId(homeworkId,applies.get(i).getStudentId()).getResult());
            }
        }
        System.out.println(jsonlist);
        return String.valueOf(jsonlist);
    }


    @ResponseBody
    @RequestMapping(value="/ProcessHomework")
    public JSONObject ProcessHomework(HttpServletRequest data){
        String state = "已批阅";
        String studentId = data.getParameter("studentId");
        String homeworkId = data.getParameter("homeworkId");
        String answer = data.getParameter("answer");
        String result = data.getParameter("result");
        homeworkBook homeworkBook1 = new homeworkBook();
        if(homeworkBookRepository.findByHomeworkIdAndStudentId(homeworkId,studentId)!=null){
            homeworkBook1 = homeworkBookRepository.findByHomeworkIdAndStudentId(homeworkId,studentId);
            homeworkBookRepository.delete(homeworkBook1);
        }
        homeworkBook homeworkBook = new homeworkBook();
        homeworkBook.setHomeworkId(homeworkId);
        homeworkBook.setStudentId(studentId);
        homeworkBook.setAnswer(answer);
        homeworkBook.setState(state);
        homeworkBook.setResult(result);
        homeworkBookRepository.save(homeworkBook);
        JSONObject object = new JSONObject();
        object.put("response", "true");
        object.put("result", "提交成功");
        return object;
    }

}
