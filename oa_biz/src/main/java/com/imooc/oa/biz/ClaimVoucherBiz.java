package com.imooc.oa.biz;

import com.imooc.oa.entity.ClaimVoucher;
import com.imooc.oa.entity.ClaimVoucherItem;
import com.imooc.oa.entity.DealRecord;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ClaimVoucherBiz {

    //���ڱ�������д����
    void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

    //���ڱ������������
    ClaimVoucher get(int id);

    List<ClaimVoucherItem> getItems(int cvid);
    List<DealRecord> getRecords(int cvid);

    //��ȡ���˱�����
    List<ClaimVoucher> getForSelf(String sn);

    //��ȡ���˴���������
    List<ClaimVoucher> getForDeal(String sn);


    //�޸ı�����
    void update(ClaimVoucher claimVoucher,List<ClaimVoucherItem> items);

    //�ύ����
    void submit(int id);


    //��˴��
    void deal(DealRecord dealRecord);
}
