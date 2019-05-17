package com.imooc.oa.controller;

import com.imooc.oa.biz.DepartmentBiz;
import com.imooc.oa.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 王韧锋QAQ
 * @date 2019/5/10
 * @description
 */

@Controller("departmentController")
@RequestMapping("/department")
public class DepartmentController {
    @Resource
    private DepartmentBiz departmentBizImpl;

    @RequestMapping("/list")
    public String list(Map<String,Object> map){
      map.put("list",departmentBizImpl.getAll());
        //System.out.println(map.get("list"));
        return "department_list";
    }

    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
        map.put("department",new Department());
        return "department_add";
    }
    @RequestMapping("/add")
    public String add(Department department){
        departmentBizImpl.add(department);
        return "redirect:list";
    }
    //修改
    @RequestMapping(value = "/to_update",params = "sn")
    public String toUpdate(String sn,Map<String,Object> map){
        map.put("department",departmentBizImpl.get(sn));
        return "department_update";
    }
    @RequestMapping("/update")
    public String update(Department department){
        departmentBizImpl.edit(department);
        return "redirect:list";
    }
    //删除
    @RequestMapping(value = "/remove",params = "sn")
    public String remove(String sn){
        departmentBizImpl.remove(sn);
        return "redirect:list";
    }
}
