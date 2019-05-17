package com.imooc.oa.biz;

import com.imooc.oa.entity.ClaimVoucher;
import com.imooc.oa.entity.ClaimVoucherItem;
import com.imooc.oa.entity.DealRecord;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ClaimVoucherBiz {

    //用于报销单填写界面
    void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

    //用于报销单详情界面
    ClaimVoucher get(int id);

    List<ClaimVoucherItem> getItems(int cvid);
    List<DealRecord> getRecords(int cvid);

    //获取个人报销单
    List<ClaimVoucher> getForSelf(String sn);

    //获取个人待处理报销单
    List<ClaimVoucher> getForDeal(String sn);


    //修改报销单
    void update(ClaimVoucher claimVoucher,List<ClaimVoucherItem> items);

    //提交操作
    void submit(int id);


    //审核打款
    void deal(DealRecord dealRecord);
}
