package com.imooc.oa.controller;

import com.imooc.oa.biz.DepartmentBiz;
import com.imooc.oa.biz.EmployeeBiz;
import com.imooc.oa.entity.Department;
import com.imooc.oa.entity.Employee;
import com.imooc.oa.global.Contant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 王韧锋QAQ
 * @date 2019/5/10
 * @description
 */

@Controller("employeeController")
@RequestMapping("/employee")
public class EmployeeController {
    @Resource
    private DepartmentBiz departmentBizImpl;
    @Resource
    private EmployeeBiz employeeBizImpl;

    @RequestMapping("/list")
    public String list(Map<String,Object> map){
      map.put("list",employeeBizImpl.getAll());
        //System.out.println(map.get("list"));
        return "employee_list";
    }

    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
        map.put("employee",new Employee());
        //同时也需要添加部门信息
        map.put("dlist",departmentBizImpl.getAll());
        //添加所有植物名称
        map.put("plist", Contant.getPosts());
        return "employee_add";
    }
    @RequestMapping("/add")
    public String add(Employee employee){
        employeeBizImpl.add(employee);
        return "redirect:list";
    }

    //修改
    @RequestMapping(value = "/to_update",params = "sn")
    public String toUpdate(String sn,Map<String,Object> map){
        map.put("employee",employeeBizImpl.get(sn));
        //同时也需要添加部门信息
        map.put("dlist",departmentBizImpl.getAll());

        //添加所有职务名称
        map.put("plist", Contant.getPosts());
        return "employee_update";
    }

    @RequestMapping("/update")
    public String update(Employee employee){
        employeeBizImpl.edit(employee);
        return "redirect:list";
    }
    //删除
    @RequestMapping(value = "/remove",params = "sn")
    public String remove(String sn){
        employeeBizImpl.remove(sn);
        return "redirect:list";
    }
}
