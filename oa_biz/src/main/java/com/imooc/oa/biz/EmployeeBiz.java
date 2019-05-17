package com.imooc.oa.biz;

import com.imooc.oa.entity.Department;
import com.imooc.oa.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王韧锋QAQ
 * @date 2019/5/10
 * @description
 */
@Service
public interface EmployeeBiz {

    void add(Employee employee);  //添加员工

    void edit(Employee employee);  //编辑员工

    void remove(String sn);   //删除员工

    Employee get(String sn);   //添加员工

    List<Employee> getAll();   //获取所有的员工

}
