package com.artist.myworld.domain;

import com.dili.utils.domain.BaseDomain;
import io.swagger.annotations.ApiParam;

import java.util.Date;

/**
 *  <br />
 * @createTime 2016-12-8 17:54:28
 * @author template
 */public class BankCard extends BaseDomain {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 添加时间
     */
    @ApiParam(value = "添加时间",required = false)
    private Date addTime;

    /**
     * 默认选中储蓄卡，目前仅“储蓄卡”一个项
     */
    private Integer cardType;

    /**
     * 单选（法人银行账户、企业银行卡账户），默认为“企业银行卡账户”，下方的开户名和营业执照号根据此处选择的不同显示不同内容
     */
    private Integer accountType;

    /**
     * 法人银行账户：显示认证时填写的法人姓名，企业
     */
    private String accountName;

    /**
     * 法人银行账户：显示认证时填写的法人身份证号，
     */
    private String idNumber;

    /**
     * 地址控件选择省、市二级
     */
    private Long bankCity;

    /**
     * 选填，允许5-50位汉字、字母和数字，不允许特殊
     */
    private String subbranch;

    /**
     * 允许填写8-30位纯数字
     */
    private String cardNumber;

    /**
     * 数据字典:中国银行，建设银行，招商银行
     */
    private Integer bank;

    /**
     * 1:是, 0：否
     */
    private Integer isDefault;

    public void setUserId (Long userId){
        this.userId = userId;
    }
    public Long getUserId(){
        return this.userId;
    }
    public void setCardType (Integer cardType){
        this.cardType = cardType;
    }
    public Integer getCardType(){
        return this.cardType;
    }
    public void setAccountType (Integer accountType){
        this.accountType = accountType;
    }
    public Integer getAccountType(){
        return this.accountType;
    }
    public void setAccountName (String accountName){
        this.accountName = accountName;
    }
    public String getAccountName(){
        return this.accountName;
    }
    public void setIdNumber (String idNumber){
        this.idNumber = idNumber;
    }
    public String getIdNumber(){
        return this.idNumber;
    }
    public void setBankCity (Long bankCity){
        this.bankCity = bankCity;
    }
    public Long getBankCity(){
        return this.bankCity;
    }
    public void setSubbranch (String subbranch){
        this.subbranch = subbranch;
    }
    public String getSubbranch(){
        return this.subbranch;
    }
    public void setCardNumber (String cardNumber){
        this.cardNumber = cardNumber;
    }
    public String getCardNumber(){
        return this.cardNumber;
    }
    public void setBank (Integer bank){
        this.bank = bank;
    }
    public Integer getBank(){
        return this.bank;
    }
    public void setIsDefault (Integer isDefault){
        this.isDefault = isDefault;
    }
    public Integer getIsDefault(){
        return this.isDefault;
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("BankCard [");
        sb.append("id = ");
        sb.append(id);
        sb.append(", userId = ");
        sb.append(userId);
        sb.append(", cardType = ");
        sb.append(cardType);
        sb.append(", accountType = ");
        sb.append(accountType);
        sb.append(", accountName = ");
        sb.append(accountName);
        sb.append(", idNumber = ");
        sb.append(idNumber);
        sb.append(", bankCity = ");
        sb.append(bankCity);
        sb.append(", subbranch = ");
        sb.append(subbranch);
        sb.append(", cardNumber = ");
        sb.append(cardNumber);
        sb.append(", bank = ");
        sb.append(bank);
        sb.append(", isDefault = ");
        sb.append(isDefault);
        sb.append("]");
        return sb.toString();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}