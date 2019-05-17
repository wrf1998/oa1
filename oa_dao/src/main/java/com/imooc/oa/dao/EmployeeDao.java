package com.imooc.oa.dao;

import com.imooc.oa.entity.Department;
import com.imooc.oa.entity.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//@Repository("departmentDao")   /*自定义注解方便扫描*/

public interface EmployeeDao {
    void insert(Employee employee);

    void update(Employee employee);

    void delete(String sn);

    Employee select(String sn);

    List<Employee> selectAll();

    List<Employee> selectByDepartmentAndPost(@Param("dsn") String dsn,@Param("post") String post);
}
