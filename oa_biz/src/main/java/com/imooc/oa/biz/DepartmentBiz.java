package com.imooc.oa.biz;

import com.imooc.oa.entity.Department;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ���ͷ�QAQ
 * @date 2019/5/10
 * @description
 */
@Service
public interface DepartmentBiz {

    void add(Department department);  //��Ӳ���

    void edit(Department department);  //�༭����

    void remove(String sn);   //ɾ������

    Department get(String sn);   //��Ӳ���

    List<Department> getAll();   //��ȡ���еĲ���

}
