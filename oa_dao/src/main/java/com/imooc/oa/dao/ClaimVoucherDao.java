package com.imooc.oa.dao;

import com.imooc.oa.entity.ClaimVoucher;

import java.util.List;

public interface ClaimVoucherDao {
        //插入数据
        void insert(ClaimVoucher claimVoucher);

        //更新数据
        void update(ClaimVoucher claimVoucher);

        //删除数据
        void delete(int id);

        //查询报销单
        ClaimVoucher select(int id);

        //通过创建者的id来查他的相关报销单
        List<ClaimVoucher> selectByCreatSn(String sn);


        //通过下个处理者的sn来查看相关报销单
        List<ClaimVoucher> selectByNextDealSn(String sn);


}
