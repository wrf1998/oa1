package com.imooc.oa.biz.impl;

import com.imooc.oa.biz.EmployeeBiz;
import com.imooc.oa.dao.EmployeeDao;
import com.imooc.oa.entity.Employee;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 王韧锋QAQ
 * @date 2019/5/11
 * @description
 */
@Service
public class EmployeeBizImpl implements EmployeeBiz {

    @Resource
    private EmployeeDao employeeDao;

    public void add(Employee employee) {
        employee.setPassword("000000");   //为员工添加默认密码
        employeeDao.insert(employee);
    }

    public void edit(Employee employee) {
        employeeDao.update(employee);
    }

    public void remove(String sn) {
        employeeDao.delete(sn);
    }

    public Employee get(String sn) {
        return employeeDao.select(sn);
    }

    public List<Employee> getAll() {
        return employeeDao.selectAll();
    }
}
