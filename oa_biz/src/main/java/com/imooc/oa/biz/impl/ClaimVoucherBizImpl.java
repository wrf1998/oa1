package com.imooc.oa.biz.impl;

import com.imooc.oa.biz.ClaimVoucherBiz;
import com.imooc.oa.dao.ClaimVoucherDao;
import com.imooc.oa.dao.ClaimVoucherItemDao;
import com.imooc.oa.dao.DealRecordDao;
import com.imooc.oa.dao.EmployeeDao;
import com.imooc.oa.entity.ClaimVoucher;
import com.imooc.oa.entity.ClaimVoucherItem;
import com.imooc.oa.entity.DealRecord;
import com.imooc.oa.entity.Employee;
import com.imooc.oa.global.Contant;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author ���ͷ�QAQ
 * @date 2019/5/13
 * @description
 */
@Service
public class ClaimVoucherBizImpl implements ClaimVoucherBiz {
    @Resource
    private ClaimVoucherDao claimVoucherDao;
    @Resource
    private ClaimVoucherItemDao claimVoucherItemDao;
    @Resource
    private DealRecordDao dealRecordDao;
    @Resource
    private EmployeeDao employeeDao;
    public void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        //�����������޷�ͨ������д�ģ�
        claimVoucher.setCreateTime(new Date());
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);

        claimVoucherDao.insert(claimVoucher);

        //�����Ժ���Ϊ������items��id�ı�����id
        for(ClaimVoucherItem item:items){
            item.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemDao.insert(item);
        }
    }

    public ClaimVoucher get(int id) {
        System.out.println(claimVoucherDao.select(id).getCreateSn());
        return claimVoucherDao.select(id);
    }

    public List<ClaimVoucherItem> getItems(int cvid) {
        return claimVoucherItemDao.selectByClaimVoucher(cvid);
    }

    public List<DealRecord> getRecords(int cvid) {
        return dealRecordDao.selectByClaimVoucher(cvid);
    }


    public List<ClaimVoucher> getForSelf(String sn) {
        return claimVoucherDao.selectByCreatSn(sn);
    }

    public List<ClaimVoucher> getForDeal(String sn) {
        return claimVoucherDao.selectByNextDealSn(sn);
    }



    //����
    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        //��Ϊ�˴�Ҫupdate����Ҫ������ص���Ϣ
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        //���� ������
        claimVoucherDao.update(claimVoucher);

        List<ClaimVoucherItem> olds= claimVoucherItemDao.selectByClaimVoucher(claimVoucher.getId());
        //��������������ݿ��е����ݣ�����Ҫupdate�����ݽ��бȽϣ������ǰ���ݿ���û�г�����update�е�
        //����Ҫɾ������
        for (ClaimVoucherItem old: olds){
            boolean is=false;
            for (ClaimVoucherItem item:items){
                if(item.getId()==old.getId()){
                    is=true;
                    break;
                }
            }
            if(!is){
                claimVoucherItemDao.delete(old.getId());
            }
        }

        for (ClaimVoucherItem item:items){

            //��Ϊupdate�е�ClaimVoucherItems�в�û�������Ҫ������id����item�����Ӧ�ı�����id
            item.setClaimVoucherId(claimVoucher.getId());

            if(item.getId()!=null&&item.getId()>0){   //�����ǰ���˵��ClaimVoucherItem��id��
                                        // ��Ϊid���������ݿ��Զ���д����˵������������������ݿ���
                claimVoucherItemDao.update(item);
            }else {    //û�л�û���ClaimVoucherItem����û�����ݿ��id
                claimVoucherItemDao.insert(item);
            }
        }

    }

    public void submit(int id) {
        //����id���������
        ClaimVoucher claimVoucher=claimVoucherDao.select(id);

        //���ݱ������Ĵ�����id��� ������

        Employee employee=employeeDao.select(claimVoucher.getCreateSn());

        //���ñ�����״̬Ϊ���ύ
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_SUBMIT);
        //��ͬ���ŵĲ��ž������

        claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(employee.getDepartmentSn(),Contant.POST_FM).get(0).getSn());
        //����һ��
        claimVoucherDao.update(claimVoucher);

        DealRecord dealRecord=new DealRecord();
        dealRecord.setDealWay(Contant.DEAL_SUBMIT);
        dealRecord.setDealSn(employee.getSn());
        dealRecord.setClaimVoucherId(id);
        dealRecord.setDealResult(Contant.CLAIMVOUCHER_SUBMIT);
        dealRecord.setDealTime(new Date());
        dealRecord.setComment("��");
        dealRecordDao.insert(dealRecord);

    }

    public void deal(DealRecord dealRecord) {
        //�Ȼ�ȡ�� ����������
        ClaimVoucher claimVoucher=claimVoucherDao.select(dealRecord.getClaimVoucherId());

        //ͨ�������¼�еĴ�����id ��������˶���
        Employee employee=employeeDao.select(dealRecord.getDealSn());

        //���ô���ʱ��Ϊ��ǰ����ʱ��
        dealRecord.setDealTime(new Date(System.currentTimeMillis()));

        if(dealRecord.getDealWay().equals(Contant.DEAL_PASS)){
            if(claimVoucher.getTotalAmount()<=5000||employee.getDepartment().equals(Contant.POST_GM)){
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_APPROVED);
                //����Ժ�Ὣ�������ύ������
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null,Contant.POST_CASHIER).get(0).getSn());
                dealRecord.setDealResult(Contant.CLAIMVOUCHER_APPROVED);
            }else{  //������
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_RECHECK);
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null,Contant.POST_GM).get(0).getSn());

                dealRecord.setDealResult(Contant.CLAIMVOUCHER_RECHECK);
            }
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_BACK)){
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_BACK);
            claimVoucher.setNextDealSn(claimVoucher.getCreateSn());

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_BACK);
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_REJECT)){
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_TERMINATED);
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_TERMINATED);
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_PAID)){
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_PAID);
            claimVoucher.setNextDealSn(null);

            dealRecord.setDealResult(Contant.CLAIMVOUCHER_PAID);
        }

        claimVoucherDao.update(claimVoucher);
        dealRecordDao.insert(dealRecord);
    }
}
