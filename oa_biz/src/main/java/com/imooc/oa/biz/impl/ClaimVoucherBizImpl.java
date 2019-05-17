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
 * @author 王韧锋QAQ
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
        //以下三个是无法通过表单填写的！
        claimVoucher.setCreateTime(new Date());
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);

        claimVoucherDao.insert(claimVoucher);

        //插入以后因为报销单items的id的报销单id
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



    //更新
    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        //因为此处要update所以要加入相关的信息
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        //更新 报销单
        claimVoucherDao.update(claimVoucher);

        List<ClaimVoucherItem> olds= claimVoucherItemDao.selectByClaimVoucher(claimVoucher.getId());
        //查出本来存在数据库中的数据，跟需要update的数据进行比较，如果当前数据库中没有出现在update中的
        //则需要删除掉，
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

            //因为update中的ClaimVoucherItems中并没有添加需要报销单id，给item添加相应的报销单id
            item.setClaimVoucherId(claimVoucher.getId());

            if(item.getId()!=null&&item.getId()>0){   //如果当前这个说明ClaimVoucherItem有id，
                                        // 因为id属性是数据库自动填写，则说明这个东西存在在数据库中
                claimVoucherItemDao.update(item);
            }else {    //没有还没存过ClaimVoucherItem表，就没有数据库的id
                claimVoucherItemDao.insert(item);
            }
        }

    }

    public void submit(int id) {
        //根据id查出报销单
        ClaimVoucher claimVoucher=claimVoucherDao.select(id);

        //根据报销单的创建人id查出 创建人

        Employee employee=employeeDao.select(claimVoucher.getCreateSn());

        //设置报销单状态为已提交
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_SUBMIT);
        //将同部门的部门经理插入

        claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(employee.getDepartmentSn(),Contant.POST_FM).get(0).getSn());
        //更新一下
        claimVoucherDao.update(claimVoucher);

        DealRecord dealRecord=new DealRecord();
        dealRecord.setDealWay(Contant.DEAL_SUBMIT);
        dealRecord.setDealSn(employee.getSn());
        dealRecord.setClaimVoucherId(id);
        dealRecord.setDealResult(Contant.CLAIMVOUCHER_SUBMIT);
        dealRecord.setDealTime(new Date());
        dealRecord.setComment("无");
        dealRecordDao.insert(dealRecord);

    }

    public void deal(DealRecord dealRecord) {
        //先获取到 报销单对象
        ClaimVoucher claimVoucher=claimVoucherDao.select(dealRecord.getClaimVoucherId());

        //通过处理记录中的处理人id 查出处理人对象
        Employee employee=employeeDao.select(dealRecord.getDealSn());

        //设置处理时间为当前处理时间
        dealRecord.setDealTime(new Date(System.currentTimeMillis()));

        if(dealRecord.getDealWay().equals(Contant.DEAL_PASS)){
            if(claimVoucher.getTotalAmount()<=5000||employee.getDepartment().equals(Contant.POST_GM)){
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_APPROVED);
                //审核以后会将报销单提交给财务
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null,Contant.POST_CASHIER).get(0).getSn());
                dealRecord.setDealResult(Contant.CLAIMVOUCHER_APPROVED);
            }else{  //待复审
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
