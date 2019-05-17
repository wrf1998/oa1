package com.imooc.oa.dao;

import com.imooc.oa.entity.DealRecord;

import java.util.List;

public interface DealRecordDao {
    void insert(DealRecord dealRecord);


    //通过报销单号来查询记录
    List<DealRecord> selectByClaimVoucher(int cvid);


}
