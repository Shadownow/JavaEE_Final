package com.example.demo.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.util.Random;


public class SentEmail {
    public JSONObject sendMail(String string) {
        if (StringUtils.isEmpty(string)) {
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "邮箱不能为空");
            return object;
        }
        // 设置邮件服务器
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.qq.com");
        mailSender.setUsername("1822177200@qq.com");
        mailSender.setPassword("animbfxhtvsmidhh");

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");

            messageHelper.setSubject("utf-8");

            messageHelper.setSubject("欢迎使用合同管理系统！");
            messageHelper.setTo(string);
            messageHelper.setFrom("1822177200@qq.com");

//	        if (filenames != null) {
//	            File file = null;
//	            for (String files : filenames) {
//	                file = new File(files.trim());
//	                messageHelper.addAttachment(MimeUtility.encodeWord(file.getName()), file);
//	            }
//	        }

            Random rand = new Random();
            int check = rand.nextInt(9000) + 1000;
            String s = String.valueOf(check);

            messageHelper.setText(s, true);
            mailSender.send(message);

            JSONObject object = new JSONObject();
            object.put("response", "true");
            object.put("check", check);
            object.put("result", "发送成功");
            System.out.println(check);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject object = new JSONObject();
            object.put("response", "false");
            object.put("result", "服务器原因或邮箱地址错误，发送失败");
            return object;
        }

    }
}