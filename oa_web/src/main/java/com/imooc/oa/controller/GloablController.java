package com.imooc.oa.controller;

/**
 * @author 王韧锋QAQ
 * @date 2019/5/12
 * @description
 */

import com.imooc.oa.biz.GlobalBiz;
import com.imooc.oa.entity.Employee;
import com.sun.org.apache.xml.internal.security.c14n.implementations.Canonicalizer20010315ExclOmitComments;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 登陆控制器
 */
@Controller("globalController")
public class GloablController {
    @Resource
    private GlobalBiz globalBizImpl;
    //去登陆
    @RequestMapping("/to_login")
    public  String toLogin(){
        return "login";
    }
@RequestMapping("/login")
    public String login(HttpSession httpSession, @RequestParam String sn, @RequestParam String password){
        //查出员工
        Employee employee=globalBizImpl.login(sn,password);
        if(employee==null){
            return "redirect:to_login";
        }else {
            //放入 session
            httpSession.setAttribute("employee",employee);
            return "redirect:self";
        }
    }
    //跳转到个人信息界面
    @RequestMapping("/self")
    public String self(){
        return "self";
    }

    @RequestMapping("/quit")
    public  String quit(HttpSession session){
        session.setAttribute("employee",null);
        return "redirect:to_login";
    }


    //去修改密码
    @RequestMapping("/to_change_password")
    public  String toChangePassword(){
        return "change_password";  //跳到修改密码界面
    }

    @RequestMapping("/change_password")
    public String changePassword(HttpSession httpSession, @RequestParam String old, @RequestParam String new1,@RequestParam String new2){
        //直接从session查出员工
        Employee employee = (Employee) httpSession.getAttribute("employee");
        if(employee.getPassword().equals(old)){   //查看密码是否一致
            if(new1.equals(new2)){                     //两次填写的密码是否一致
                                   //一般在前端控制，不会在后端进行逻辑判断
                /*在进行修改之前，应该先更新用户的密码*/
                employee.setPassword(new1);
                globalBizImpl.changePassword(employee);
                return "redirect:self";
            }
        }
        return "redirect:to_change_password";
    }


}
