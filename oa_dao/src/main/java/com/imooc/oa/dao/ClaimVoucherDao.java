package com.imooc.oa.dao;

import com.imooc.oa.entity.ClaimVoucher;

import java.util.List;

public interface ClaimVoucherDao {
        //��������
        void insert(ClaimVoucher claimVoucher);

        //��������
        void update(ClaimVoucher claimVoucher);

        //ɾ������
        void delete(int id);

        //��ѯ������
        ClaimVoucher select(int id);

        //ͨ�������ߵ�id����������ر�����
        List<ClaimVoucher> selectByCreatSn(String sn);


        //ͨ���¸������ߵ�sn���鿴��ر�����
        List<ClaimVoucher> selectByNextDealSn(String sn);


}
