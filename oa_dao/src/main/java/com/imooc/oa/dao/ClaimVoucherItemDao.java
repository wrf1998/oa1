package com.imooc.oa.dao;


import com.imooc.oa.entity.ClaimVoucher;
import com.imooc.oa.entity.ClaimVoucherItem;

import java.util.List;

/**
 * 报销单具体信息
 */
public interface ClaimVoucherItemDao {

    void insert(ClaimVoucherItem claimVoucherItem);

    void update(ClaimVoucherItem claimVoucherItem);

    void delete(int  id);

    //通过报销单号，查询他的明细   比如有车费，住宿等！！！
    List<ClaimVoucherItem> selectByClaimVoucher(int cvid);

}
