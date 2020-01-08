package com.lxx.controller;

import com.lxx.entity.Admin;
import com.lxx.service.AdminService;
import com.lxx.util.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @RequestMapping("/login")
    @ResponseBody
    public String login(Admin admin, String code, HttpSession session){
        //获取验证码
        String scode = (String) session.getAttribute("scode");
        if(code.equals(scode)||code==""){
            Admin admin1 = adminService.selectOne(admin);
            if (admin1!=null){
                session.setAttribute("admin",admin1);
                return "ok";
            }
            return "error";
        }
        return "codeerror";
    }
    //验证码
    @RequestMapping("/ma")
    public void ma(HttpSession session, HttpServletResponse response) throws IOException {
        CreateValidateCode cvode = new CreateValidateCode();
        String code = cvode.getCode();//生成随机验证码
        cvode.write(response.getOutputStream()); // 把图片输出client
        //把生成的验证码存入session
        session.setAttribute("scode",code);
    }
    @RequestMapping("out")
    public String out(HttpSession session){
        session.removeAttribute("admin");
        return "redirect:/jsp/login.jsp";
    }
}
