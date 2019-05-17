package com.imooc.oa.biz;

import com.imooc.oa.entity.Department;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王韧锋QAQ
 * @date 2019/5/10
 * @description
 */
@Service
public interface DepartmentBiz {

    void add(Department department);  //添加部门

    void edit(Department department);  //编辑部门

    void remove(String sn);   //删除部门

    Department get(String sn);   //添加部门

    List<Department> getAll();   //获取所有的部门

}
