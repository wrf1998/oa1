package com.imooc.oa.dao;

import com.imooc.oa.entity.DealRecord;
import com.imooc.oa.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository("departmentDao")   /*�Զ���ע�ⷽ��ɨ��*/

public interface DepartmentDao {
    void insert(Department department);

    void update(Department department);

    void delete(String sn);

    Department select(String sn);

    List<Department> selectAll();
}
