package com.imooc.oa.biz;

import com.imooc.oa.entity.Department;
import com.imooc.oa.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ���ͷ�QAQ
 * @date 2019/5/10
 * @description
 */
@Service
public interface EmployeeBiz {

    void add(Employee employee);  //���Ա��

    void edit(Employee employee);  //�༭Ա��

    void remove(String sn);   //ɾ��Ա��

    Employee get(String sn);   //���Ա��

    List<Employee> getAll();   //��ȡ���е�Ա��

}
