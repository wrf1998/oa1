package com.imooc.oa.biz;

import com.imooc.oa.entity.Employee;
import org.springframework.stereotype.Service;

@Service
public interface GlobalBiz {
    //��½
    Employee login(String sn,String password);

    void changePassword(Employee employee);
}
