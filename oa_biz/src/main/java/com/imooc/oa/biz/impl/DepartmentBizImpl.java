package com.imooc.oa.biz.impl;

import com.imooc.oa.biz.DepartmentBiz;
import com.imooc.oa.dao.DepartmentDao;
import com.imooc.oa.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 王韧锋QAQ
 * @date 2019/5/10
 * @description
 */
@Service
public class DepartmentBizImpl implements DepartmentBiz {
   // @Autowired(required = false)                       //Spring的标签，不要写get/set 默认按照ByType注入
    @Resource
    private DepartmentDao departmentDao;

    public void add(Department department) {
        departmentDao.insert(department);
    }

    public void edit(Department department) {
        departmentDao.update(department);
    }

    public void remove(String sn) {
        departmentDao.delete(sn);
    }

    public Department get(String sn) {
        return departmentDao.select(sn);
    }

    public List<Department> getAll() {
        return departmentDao.selectAll();
    }
}
