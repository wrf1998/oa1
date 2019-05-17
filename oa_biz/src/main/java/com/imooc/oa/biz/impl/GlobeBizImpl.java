package com.imooc.oa.biz.impl;

import com.imooc.oa.biz.GlobalBiz;
import com.imooc.oa.dao.EmployeeDao;
import com.imooc.oa.entity.Employee;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Õı»Õ∑ÊQAQ
 * @date 2019/5/12
 * @description
 */
@Service
public class GlobeBizImpl implements GlobalBiz {
    @Resource
    private EmployeeDao employeeDao;
    public Employee login(String sn, String password) {
        Employee employee=employeeDao.select(sn);
        if(employee!=null&&employee.getPassword().equals(password)){
            return  employee;
        }
        return null;
    }

    public void changePassword(Employee employee) {
        employeeDao.update(employee);
    }
}
