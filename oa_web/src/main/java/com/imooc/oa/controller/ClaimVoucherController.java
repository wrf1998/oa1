package com.imooc.oa.controller;

import com.imooc.oa.biz.ClaimVoucherBiz;
import com.imooc.oa.dto.ClaimVoucherInfo;
import com.imooc.oa.entity.DealRecord;
import com.imooc.oa.entity.Employee;
import com.imooc.oa.global.Contant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 王韧锋QAQ
 * @date 2019/5/13
 * @description
 */
@Controller
@RequestMapping("/claim_voucher")
public class ClaimVoucherController {
    @Resource
    private ClaimVoucherBiz claimVoucherBizImpl;
    //跳转到添加界面之前的控制器
    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
        //添加常量
        map.put("items", Contant.getItems());

        map.put("info",new ClaimVoucherInfo());

        return "claim_voucher_add";
    }
    @RequestMapping("/add")    //选择保存的控制器
    public String add(ClaimVoucherInfo info, HttpSession httpSession){
        //取出当前用户
        Employee employee = (Employee) httpSession.getAttribute("employee");

        //把当前创建用户的id 设置为 当前登陆用户的id
        info.getClaimVoucher().setCreateSn(employee.getSn());
        //保存
        claimVoucherBizImpl.save(info.getClaimVoucher(),info.getItems());

        return "redirect:deal";

    }

    //跳到详情页面
    @RequestMapping("/detail")
    public String detail(int id,Map<String,Object> map){
        map.put("claimVoucher",claimVoucherBizImpl.get(id));
        map.put("items",claimVoucherBizImpl.getItems(id));
        map.put("records",claimVoucherBizImpl.getRecords(id));
        return "claim_voucher_detail";
    }


    //跳到个人报销单
    @RequestMapping("/self")
    public String self(HttpSession session,Map<String,Object> map){
        Employee employee = (Employee) session.getAttribute("employee");
        map.put("list",claimVoucherBizImpl.getForSelf(employee.getSn()));
        return "claim_voucher_self";
    }

    //跳到个人待处理报销单
    @RequestMapping("/deal")
    public String deal(HttpSession session,Map<String,Object> map){
        Employee employee = (Employee) session.getAttribute("employee");
        map.put("list",claimVoucherBizImpl.getForDeal(employee.getSn()));
        return "claim_voucher_deal";
    }


    @RequestMapping("/to_update")
    public String toUpdate(int id,Map<String,Object> map){

        map.put("items", Contant.getItems());

        ClaimVoucherInfo info=new ClaimVoucherInfo();
        info.setClaimVoucher(claimVoucherBizImpl.get(id));
        info.setItems(claimVoucherBizImpl.getItems(id));
        map.put("info",info);

        return "claim_voucher_update";
    }
    @RequestMapping("/update")    //选择保存的控制器
    public String update(ClaimVoucherInfo info, HttpSession httpSession){
        //取出当前用户
        Employee employee = (Employee) httpSession.getAttribute("employee");

        //把当前创建用户的id 设置为 当前登陆用户的id
        info.getClaimVoucher().setCreateSn(employee.getSn());

        //保存
        claimVoucherBizImpl.update(info.getClaimVoucher(),info.getItems());

        return "redirect:deal";

    }

    @RequestMapping("/submit")    //选择保存的控制器
    public String submit(int id){
        claimVoucherBizImpl.submit(id);
        return "redirect:deal";

    }

    @RequestMapping("/to_check")
    public String toCheck(int id,Map<String,Object> map){
        //携带东西去展示界面
        map.put("claimVoucher",claimVoucherBizImpl.get(id));
        map.put("items",claimVoucherBizImpl.getItems(id));
        map.put("records",claimVoucherBizImpl.getRecords(id));
        DealRecord dealRecord=new DealRecord();
        dealRecord.setClaimVoucherId(id);

        map.put("record",dealRecord);

        return "claim_voucher_check";
    }

    @RequestMapping("/check")    //选择保存的控制器
    public String check(DealRecord dealRecord, HttpSession httpSession){
        //取出当前用户
        Employee employee = (Employee) httpSession.getAttribute("employee");
        dealRecord.setDealSn(employee.getSn());

        claimVoucherBizImpl.deal(dealRecord);
        return "redirect:deal";

    }


}
