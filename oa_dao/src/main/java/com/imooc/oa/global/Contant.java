package com.imooc.oa.global;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ���ͷ�QAQ
 * @date 2019/5/10
 * @description
 * �����ֵ䳣��
 */
public class Contant {
    //ְ��
    public static final String POST_STAFF ="Ա��";
    public static final String POST_FM ="���ž���";
    public static final String POST_GM ="�ܾ���";
    public static final String POST_CASHIER ="����";

    public static List<String> getPosts(){
        List<String> list =new ArrayList<String>();
        list.add(POST_STAFF);
        list.add(POST_FM);
        list.add(POST_GM);
        list.add(POST_CASHIER);
        return list;
    }

    /*��������*/
    public static List<String> getItems(){
        List<String> list =new ArrayList<String>();
        list.add("��ͨ");
        list.add("����");
        list.add("ס��");
        list.add("�칫");
        return list;
    }

    /*������״̬*/
    public static final String CLAIMVOUCHER_CREATED="�´���";
    public static final String CLAIMVOUCHER_SUBMIT="���ύ";
    public static final String CLAIMVOUCHER_APPROVED="�����";
    public static final String CLAIMVOUCHER_BACK="�Ѵ��";
    public static final String CLAIMVOUCHER_TERMINATED="����ֹ";
    public static final String CLAIMVOUCHER_RECHECK="������";
    public static final String CLAIMVOUCHER_PAID="�Ѵ��";

    /*��˶��*/
    public  static  final double LIMIT_CHECK =5000.00;

    /*�������������    ������ť*/
    public static final String DEAL_CREAT="����";
    public static final String DEAL_SUBMIT="�ύ";
    public static final String DEAL_UPDATE="�޸�";
    public static final String DEAL_BACK="���";
    public static final String DEAL_REJECT="�ܾ�";
    public static final String DEAL_PASS="ͨ��";
    public static final String DEAL_PAID="���";



}
