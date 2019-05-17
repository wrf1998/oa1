package com.imooc.oa.dao;


import com.imooc.oa.entity.ClaimVoucher;
import com.imooc.oa.entity.ClaimVoucherItem;

import java.util.List;

/**
 * ������������Ϣ
 */
public interface ClaimVoucherItemDao {

    void insert(ClaimVoucherItem claimVoucherItem);

    void update(ClaimVoucherItem claimVoucherItem);

    void delete(int  id);

    //ͨ���������ţ���ѯ������ϸ   �����г��ѣ�ס�޵ȣ�����
    List<ClaimVoucherItem> selectByClaimVoucher(int cvid);

}
