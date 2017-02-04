package com.cf360.mould;

import com.cf360.bean.AddScheduleBean;
import com.cf360.bean.AdviceBean;
import com.cf360.bean.AllCustomerBean;
import com.cf360.bean.BindBankCard;
import com.cf360.bean.ApplyContractBean;
import com.cf360.bean.AppointmentContentBean;
import com.cf360.bean.ApplyDeclarationBean;
import com.cf360.bean.ApplyToubaodanBean;
import com.cf360.bean.BindPhoneBean;
import com.cf360.bean.ChangePWDBean;
import com.cf360.bean.ChangePhoneBean;
import com.cf360.bean.CheckVersionBean;
import com.cf360.bean.ContractDetailsBean;
import com.cf360.bean.ContractListBean;
import com.cf360.bean.CustomerAddBean;
import com.cf360.bean.CustomerDeteleBean;
import com.cf360.bean.CustomerFinancialSaveBean;
import com.cf360.bean.CustomerInfoSaveBean;
import com.cf360.bean.CustomerInstestSaveBean;
import com.cf360.bean.CustomerIntentionSaveBean;
import com.cf360.bean.CustomerModifyBeizhuBean;
import com.cf360.bean.CustomerPhoneSaveBean;
import com.cf360.bean.DeclarationDetailsBean;
import com.cf360.bean.EcryptBean;
import com.cf360.bean.EditScheduleBean;
import com.cf360.bean.EquiteSaiXuanItemBean;
import com.cf360.bean.EquityDetailsItemBean;
import com.cf360.bean.EquityPaiXuItemBean;
import com.cf360.bean.FinancialBean;
import com.cf360.bean.FindPWDbyPhoneBean;
import com.cf360.bean.FindPWDbyPhoneVerifyBean;
import com.cf360.bean.FocusBean;
import com.cf360.bean.GenzongCustomerBean;
import com.cf360.bean.HisBaoDanBean;
import com.cf360.bean.HisScheduleBean;
import com.cf360.bean.InsuranceDetailsResultBean;
import com.cf360.bean.InsurancetempBean;
import com.cf360.bean.HelpDetailBean;
import com.cf360.bean.HelpListBean;
import com.cf360.bean.InfoDataBean;
import com.cf360.bean.InfoDetailDataBean;
import com.cf360.bean.InsuranceAgentBean;
import com.cf360.bean.InsuranceBean;
import com.cf360.bean.IsInsuranceBean;
import com.cf360.bean.IsLoginBean;
import com.cf360.bean.IsRegisterBean;
import com.cf360.bean.MessageDetailBean;
import com.cf360.bean.MessageListBean;
import com.cf360.bean.MyAccountBean;
import com.cf360.bean.MyBankList;
import com.cf360.bean.MyMessageCountBean;
import com.cf360.bean.NoticeListBean;
import com.cf360.bean.OrderDetailsBean;
import com.cf360.bean.OrderListBean;
import com.cf360.bean.PayAttentionToBean;
import com.cf360.bean.PostContractBean;
import com.cf360.bean.PostInsuranceResultBean;
import com.cf360.bean.SInsurancetempBean;
import com.cf360.bean.PostTouabodanBean;
import com.cf360.bean.SaveEditDeclarationBean;
import com.cf360.bean.ScheduleDetailsBean;
import com.cf360.bean.ScheduleDetailsDeleteBean;
import com.cf360.bean.SearchCommissionratioBean;
import com.cf360.bean.SelectCustomerBean;
import com.cf360.bean.SelectProductBean;
import com.cf360.bean.SelectProductReturnBean;
import com.cf360.bean.SearchProductBean;
import com.cf360.bean.SendSMSForApplyDeclarationBean;
import com.cf360.bean.SendSMSForApplyToubaodanBean;
import com.cf360.bean.SentSMSBean;
import com.cf360.bean.SentSMSForApplyContractBean;
import com.cf360.bean.SentSMSForAppointmentBean;
import com.cf360.bean.SignContractBean;
import com.cf360.bean.SignToubaodanBean;
import com.cf360.bean.SignUpOneBean;
import com.cf360.bean.SignUpTwoBean;
import com.cf360.bean.SunShineDetailsItemBean;
import com.cf360.bean.ToubaodanDetailsBean;
import com.cf360.bean.UnBindBankCard;
import com.cf360.bean.SunShineItemBean;
import com.cf360.bean.SunShineSaiXuanItemBean;
import com.cf360.bean.UserLoginBean;
import com.cf360.bean.WithDrawBean;
import com.cf360.bean.WithdrawVerifyBean;
import com.cf360.bean.XinDetailsItemBean;
import com.cf360.bean.XintuoEmailBean;
import com.cf360.bean.XintuoSaiItemBean;
import com.cf360.bean.XintuoSortItemBean;
import com.cf360.uitls.DESUtil;
import com.cf360.uitls.MD5;
import com.google.gson.Gson;

public class HtmlLoadUtil {

	private static String getResult(Object data) {
		Gson gson = new Gson();
		String md5 = MD5.stringToMD5(gson.toJson(data));
		String result = null;
		try {
			EcryptBean b = new EcryptBean(md5, data);
			String encrypt = gson.toJson(b);
			result = DESUtil.encrypt(encrypt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String getResultNoEncrypt(Object data) {
		Gson gson = new Gson();
		String md5 = MD5.stringToMD5(gson.toJson(data));
		String result = null;
		try {
			EcryptBean b = new EcryptBean(md5, data);
			result = gson.toJson(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 验证手机号是否注册
	 * 
	 * @return
	 */
	public static String isRegister(String mobile) {
		IsRegisterBean b = new IsRegisterBean(mobile);
		return getResult(b);
	}

	/**
	 * 获取短信
	 * 
	 * @param userMobile
	 * @param busiType
	 * @return
	 */
	public static String getSMS(String userMobile, String busiType,
			String mathRandom) {
		SentSMSBean b = new SentSMSBean(userMobile, busiType, mathRandom);
		return getResult(b);
	}

	/**
	 * 获取短信for申请合同
	 * 
	 * @param busiType
	 * @return
	 */
	public static String getSMSForApplyContract(String mobile, String busiType,
			String contractId, String productName) {
		SentSMSForApplyContractBean b = new SentSMSForApplyContractBean(mobile,
				busiType, contractId, productName);
		return getResult(b);
	}

	/**
	 * 获取短信for申请投保单
	 * 
	 * @param busiType
	 * @return
	 */
	public static String getSMSForApplyTouBaoDan(String busiType,
			String policyId, String productName,String token) {
		SendSMSForApplyToubaodanBean b = new SendSMSForApplyToubaodanBean(
				busiType, policyId, productName,token);
		return getResult(b);
	}

	/**
	 * 获取短信for申请报单
	 * 
	 * @param busiType
	 * @return
	 */
	public static String getSMSForApplyDeclaration(String busiType,
			String orderId, String productName,String token) {
		SendSMSForApplyDeclarationBean b = new SendSMSForApplyDeclarationBean(
				busiType, orderId, productName,token);
		return getResult(b);
	}

	/**
	 * 获取短信for我要预约
	 * 
	 * @param busiType
	 * @return
	 */
	public static String getSMSForAppointment(String productName,
			String amount, String appoitmentId, String busiType) {
		SentSMSForAppointmentBean b = new SentSMSForAppointmentBean(
				productName, amount, appoitmentId, busiType);
		return getResult(b);
	}

	/**
	 * 获取我的合同列表
	 * 
	 * @param page
	 * @return
	 */
	public static String getContractList(String page,String token) {
		ContractListBean b = new ContractListBean(page,token);
		return getResult(b);
	}

	/**
	 * 获取我的报单列表
	 * 
	 * @param page
	 * @return
	 */
	public static String getDeclarationList(String page,String token) {
		ContractListBean b = new ContractListBean(page,token);
		return getResult(b);
	}

	/**
	 * 获取我的合同详情
	 * 
	 * @param contractId
	 * @param status
	 * @param type
	 * @return
	 */
	public static String getContractDetails(String contractId, String status,
			String type,String token) {
		ContractDetailsBean b = new ContractDetailsBean(contractId, status,
				type,token);
		return getResult(b);
	}

	/**
	 * 获取我的投保单详情
	 * 
	 * @param status
	 * @return
	 */
	public static String getToubaodanDetails(String policyOrderId, String status,String token) {
		ToubaodanDetailsBean b = new ToubaodanDetailsBean(policyOrderId, status,token);
		return getResult(b);
	}




	/**
	 * 获取报单详情
	 * 
	 * @return
	 */
	public static String getDeclarationDetails(String id,String token) {
		DeclarationDetailsBean b = new DeclarationDetailsBean(id,token);
		return getResult(b);
	}

	/**
	 * 获取保险产品的返佣比例
	 * 
	 * @return
	 */
	public static String getSearchCommissionratio(String productId,
			String deputyInsuranceName, String ageCoverage, String payLimitTime,String token) {
		SearchCommissionratioBean b = new SearchCommissionratioBean(productId,
				deputyInsuranceName, ageCoverage, payLimitTime,token);
		return getResult(b);
	}

	/**
	 * 选择产品
	 * 
	 * @return
	 */
	public static String getSelectProduct(String productName, String category,
			String selectType,String token) {
		SelectProductBean b = new SelectProductBean(productName, category,
				selectType,token);
		return getResult(b);
	}

	/**
	 * 选择产品Return
	 * 
	 * @return
	 */
	public static String getSelectProductReturn(String id, String category,
			String appoId,String token) {
		SelectProductReturnBean b = new SelectProductReturnBean(id, category,
				appoId,token);
		return getResult(b);
	}

	/**
	 * 获取我的预约详情 // 取消预约
	 * 
	 * @param id
	 * @return
	 */
	public static String getOrderDetails(String id,String token) {
		OrderDetailsBean b = new OrderDetailsBean(id,token);
		return getResult(b);
	}

	/**
	 * 我的关注
	 * 
	 * @param category
	 * @param pageNo
	 * @return
	 */
	public static String getFocus(String category, String pageNo,String token) {
		FocusBean b = new FocusBean(category, pageNo,token);
		return getResult(b);
	}

	/**
	 * 签收合同
	 * 
	 * @param contractId
	 * @return
	 */
	public static String getSignContract(String contractId,String token) {
		SignContractBean b = new SignContractBean(contractId,token);
		return getResult(b);
	}

	/**
	 * 签收投保单
	 * 
	 * @return
	 */
	public static String getSignToubaodan(String policyOrderId, String status,String token) {
		SignToubaodanBean b = new SignToubaodanBean(policyOrderId, status,token);
		return getResult(b);
	}

	/**
	 * 寄回合同
	 * 
	 * @param contractId
	 * @return
	 */
	public static String getPostContract(String contractId,
			String expressNameBack, String expressCodeBack,
			String expressUrlBack,String token) {
		PostContractBean b = new PostContractBean(contractId, expressNameBack,
				expressCodeBack, expressUrlBack,token);
		return getResult(b);
	}

	/**
	 * 寄回保单
	 * 
	 * @return
	 */
	public static String getPostToubaodan(String policyOrderId,
			String expressFiles, String expressName, String expressCode,
			String expressIP,String token) {
		PostTouabodanBean b = new PostTouabodanBean(policyOrderId,
				expressFiles, expressName, expressCode, expressIP,token);
		return getResult(b);
	}

	/**
	 * 申请合同
	 * 
	 * @return
	 */
	public static String getApplyContract(String productId, String productName,
			String productCategory, String customerName, String userName,
			String userPhone, String accepter, String acceptPhone,
			String acceptAddress, String policyOrderId,
			String deputyInsuranceName, String payLimitTime, String ageCoverage,String token) {
		ApplyContractBean b = new ApplyContractBean(productId, productName,
				productCategory, customerName, userName, userPhone, accepter,
				acceptPhone, acceptAddress, policyOrderId, deputyInsuranceName,
				payLimitTime, ageCoverage,token);
		return getResult(b);
	}

	/**
	 * 申请投保单
	 * 
	 * @return
	 */
	public static String getApplyToubaodan(String appId, String productId,
			String productName, String customerName, String userName,
			String userPhone, String accepter, String acceptPhone,
			String acceptAddress, String deputyInsuranceName,
			String payLimitTime, String ageCoverage, String age,String token) {
		ApplyToubaodanBean b = new ApplyToubaodanBean(appId, productId,
				productName, customerName, userName, userPhone, accepter,
				acceptPhone, acceptAddress, deputyInsuranceName, payLimitTime,
				ageCoverage, age,token);
		return getResult(b);
	}

	/**
	 * 申请报单
	 * 
	 * @param contractId
	 * @return
	 */
	public static String getApplyDeclaration(String appoitmentId,
			String productId, String contractId, String productCategory,
			String productName, String customerName, String customerPhone,
			String amount, String paymoneyTime, String sfzzmFileName,
			String sfzfmFileName, String cardFileName, String dkptFileName,
			String qzyFileName, String remark, String userName,
			String userPhone, String deputyInsuranceName, String ageCoverage,
			String payLimitTime, String rebateProportion,
			String deputyInsuranceAmount, String insuranceRebateProportion,
			String customerIDcard, String deputyInsuranceId,String costAmount,String token) {
		ApplyDeclarationBean b = new ApplyDeclarationBean(ageCoverage, amount,
				appoitmentId, cardFileName, contractId, customerIDcard,
				customerName, customerPhone, deputyInsuranceAmount,
				deputyInsuranceName, dkptFileName, insuranceRebateProportion,
				payLimitTime, paymoneyTime, productCategory, productId,
				productName, qzyFileName, rebateProportion, remark,
				sfzfmFileName, sfzzmFileName, userName, userPhone,
				deputyInsuranceId,costAmount,token);
		return getResult(b);
	}

	/**
	 * 保存修改报单
	 * 
	 * @param contractId
	 * @return
	 */
	public static String getSaveEditDeclaration(String id, String appoitmentId,
			String productId, String contractId, String productCategory,
			String productName, String customerName, String customerPhone,
			String amount, String paymoneyTime, String sfzzmFileName,
			String sfzfmFileName, String cardFileName, String dkptFileName,
			String qzyFileName, String remark, String userName,
			String userPhone, String deputyInsuranceName, String ageCoverage,
			String payLimitTime, String rebateProportion,
			String deputyInsuranceAmount, String insuranceRebateProportion,
			String customerIDcard,String deputyInsuranceId,String costAmount,String token) {
		SaveEditDeclarationBean b = new SaveEditDeclarationBean(id,
				appoitmentId, productId, contractId, productCategory,
				productName, customerName, customerPhone, amount, paymoneyTime,
				sfzzmFileName, sfzfmFileName, cardFileName, dkptFileName,
				qzyFileName, remark, userName, userPhone, deputyInsuranceName,
				ageCoverage, payLimitTime, rebateProportion,
				deputyInsuranceAmount, insuranceRebateProportion,
				customerIDcard,deputyInsuranceId,costAmount,token);
		return getResult(b);
	}

	/**
	 * 获取我的预约列表
	 * 
	 * @param page
	 * @return
	 */
	public static String getOrderList(String category, String page,
			String status,String token) {
		OrderListBean b = new OrderListBean(category, page, status,token);
		return getResult(b);
	}

	/**
	 * 检查版本
	 * 
	 * @param type
	 * @return
	 */
	public static String checkVersion(String type) {
		CheckVersionBean b = new CheckVersionBean(type);
		return getResultNoEncrypt(b);
	}

	/**
	 * 登陆
	 * 
	 * @param userName
	 * @param password
	 * @param token
	 * @return
	 */
	public static String frontLogin(String userName, String password,
			String token, String appid) {
		UserLoginBean bean = new UserLoginBean(userName, password, token, appid);
		return getResult(bean);
	}

	/**
	 * 注册一
	 * 
	 * @param validateCode
	 * @return
	 */
	public static String signUpOne(String mobile, String validateCode) {
		SignUpOneBean b = new SignUpOneBean(mobile, validateCode);
		return getResult(b);
	}

	/**
	 * 注册二
	 * 
	 * @param password
	 * @param nickName
	 * @return
	 */
	public static String signUpTwo(String mobile, String password,
			String nickName, String channelManagerPhone) {
		SignUpTwoBean b = new SignUpTwoBean(channelManagerPhone, mobile,
				nickName, password);
		return getResult(b);
	}

	/**
	 * 确认提现
	 * 
	 * @return
	 */
	public static String withdraw(String userId, String appType,String token) {
		WithDrawBean b = new WithDrawBean(userId, appType,token);
		return getResult(b);
	}

	/**
	 * 修改手机号
	 * 
	 * @param userName
	 * @param phone
	 * @param validateCode
	 * @return
	 */
	public static String bindPhone(String userName, String phone,
			String validateCode,String token) {
		BindPhoneBean b = new BindPhoneBean(userName, phone, validateCode,token);
		return getResult(b);
	}

	/**
	 * 修改手机号验证码验证
	 * 
	 * @param userName
	 * @param validateCode
	 * @return
	 */
	public static String changePhoneVerify(String userName, String validateCode,String token) {
		ChangePhoneBean b = new ChangePhoneBean(userName, validateCode,token);
		return getResult(b);
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	public static String changePassword(String mobile, String oldPassword,
			String password, String rePassword,String token) {
		ChangePWDBean b = new ChangePWDBean(mobile, oldPassword, password,
				rePassword,token);
		return getResult(b);
	}

	/**
	 * 通过手机号找回密码
	 * 
	 * @param validateCode
	 * @param password
	 * @return
	 */
	public static String findPWDbyPhone(String mobile, String password,
			String rePassword, String validateCode,String token) {
		FindPWDbyPhoneBean b = new FindPWDbyPhoneBean(mobile, password,
				rePassword, validateCode,token);
		return getResult(b);
	}

	/**
	 * 通过手机号找回密码 验证
	 * 
	 * @param userName
	 * @param validateCode
	 * @return
	 */
	public static String findPWDbyPhoneVerify(String userName,
			String validateCode,String token) {
		FindPWDbyPhoneVerifyBean b = new FindPWDbyPhoneVerifyBean(userName,
				validateCode,token);
		return getResult(b);
	}

	/**
	 * 我的银行卡
	 * 
	 * @return
	 */
	public static String getMyBankList() {
		// MyBankList b = new MyBankList();
		String b = "";
		return getResult(b);
	}

	/**
	 * 我的账户
	 * 
	 * @return
	 */
	public static String getMyAccount(String token) {
		// MyBankList b = new MyBankList();
		// MyAccout b = new MyAccout();
//		String b = "";
		MyAccountBean b = new MyAccountBean(token);

		return getResult(b);
	}

	/**
	 * 我的财富
	 * 
	 * @return
	 */
	public static String getMyPerson() {
		// MyBankList b = new MyBankList();
		// MyAccout b = new MyAccout();
		String b = "";

		return getResult(b);
	}

	/**
	 * 绑定银行卡
	 * 
	 * @return
	 */
	public static String getBindBankCard(String remoteValidateCode,
			String openBankId, String usrCustId, String openAcctId,
			String userName,String token) {
		BindBankCard b = new BindBankCard(remoteValidateCode, openBankId,
				usrCustId, openAcctId, userName,token);
		return getResult(b);
	}

	/**
	 * 解绑银行卡
	 * 
	 * @return
	 */
	public static String getUnBindBankCard(String remoteValidateCode,
			String bankId,String token) {
		UnBindBankCard b = new UnBindBankCard(remoteValidateCode, bankId,token);
		return getResult(b);
	}

	/**
	 * 理财师认证
	 * 
	 * @return
	 */
	public static String getFinancialAudit(String mobile, String realName,
			String regionaProvince, String regionaCity, String companyName,
			String email, String businessCardName,String token) {
		FinancialBean b = new FinancialBean(mobile, realName, regionaProvince,
				regionaCity, companyName, email, businessCardName,token);
		return getResult(b);
	}

	/**
	 * 保险代理认证
	 * 
	 * @return
	 */
	public static String getInsuranceAudit(String insuranceAgentCode,String idNo,String insuranceAgentType,String token) {
		InsuranceBean b = new InsuranceBean(insuranceAgentCode,idNo,insuranceAgentType,token);
		return getResult(b);
	}

	/**
	 * 保险代理信息验证
	 * 
	 * @return
	 */
	public static String getIsInsuranceAudit(String userId,String token) {
		IsInsuranceBean b = new IsInsuranceBean(userId,token);
		return getResult(b);
	}

	/**
	 * 是否登录状态
	 *
	 * @return
	 */
	public static String getIsLogin(String userId,String token) {
		IsLoginBean b = new IsLoginBean(userId,token);
		return getResult(b);
	}

	/**
	 * 机构认证
	 * 
	 * @return
	 */
	public static String getInsuranceAgentAudit(String orgId) {
		InsuranceAgentBean b = new InsuranceAgentBean(orgId);
		return getResult(b);
	}

	/**
	 * 确认提现
	 * 
	 * @return
	 */
	public static String getWithdrawVerify(String bankCardId, String validCode,
			String transAmount,String token) {
		WithdrawVerifyBean b = new WithdrawVerifyBean(bankCardId, validCode,
				transAmount,token);
		return getResult(b);
	}

	/**
	 * 产品搜索
	 * 
	 * @return
	 */
	public static String getSearchProduct(String productName, String pageNo) {
		SearchProductBean b = new SearchProductBean(productName, pageNo);
		return getResult(b);
	}

	/**
	 * 收支明细
	 * 
	 * @return
	 */
	public static String getInfoData(String CATEGORY, String pageNo,String token) {
		InfoDataBean b = new InfoDataBean(CATEGORY, pageNo,token);
		return getResult(b);
	}

	/**
	 * 收支明细详情
	 * 
	 * @return
	 */
	public static String getInfoDetailData(String id, String ptype,String token) {
		InfoDetailDataBean b = new InfoDetailDataBean(id, ptype,token);
		return getResult(b);
	}

	/**
	 * 帮助中心列表
	 * 
	 * @return
	 */
	public static String helpListData(int page) {
		HelpListBean b = new HelpListBean(page);
		return getResult(b);
	}

	/**
	 * 帮助详情
	 * 
	 * @return
	 */
	public static String helpDetailData(String id) {
		HelpDetailBean b = new HelpDetailBean(id);
		return getResult(b);
	}

	/**
	 * 意见反馈
	 * 
	 * @return
	 */
	public static String adviceData(String content, String mobile, String email,String token) {
		AdviceBean b = new AdviceBean(content, mobile, email,token);
		return getResult(b);
	}

	/**
	 * 我的消息
	 * 
	 * @return
	 */
	public static String getMessageListData(String pageNo,String token) {
		MessageListBean b = new MessageListBean(pageNo,token);
		return getResult(b);
	}

	/**
	 * 我的消息数量
	 * 
	 * @return
	 */
	public static String getMyMessageCountData(String Mobile,String token) {
		MyMessageCountBean b = new MyMessageCountBean(Mobile,token);
		return getResult(b);
	}

	/**
	 * 我的消息详情
	 * 
	 * @return
	 */
	public static String getMessageDetailData(String messageId,String token) {
		MessageDetailBean b = new MessageDetailBean(messageId,token);
		return getResult(b);
	}

	/**
	 * 公告列表
	 * 
	 * @return
	 */
	public static String getNoticeListData(int page,String token) {
		NoticeListBean b = new NoticeListBean(page,token);
		return getResult(b);
	}

	/**
	 * 信托筛选
	 * 
	 * @param category
	 * @param defaultType
	 * @param filterType
	 * @param pageNo
	 * @param investmentField
	 * @param issuer
	 * @param commission
	 * @param annualRate
	 * @return
	 */

	public static String XintuoSaiXuanItem(String annualRate, String category,
			String commission, String defaultType, String filterType,
			String investmentField, String issuer, String pageNo,String token) {
		XintuoSaiItemBean b = new XintuoSaiItemBean(annualRate, category,
				commission, defaultType, filterType, investmentField, issuer,
				pageNo,token);
		return getResult(b);
	}

	/**
	 * 信托排序
	 * 
	 * @param category
	 * @param defaultType
	 * @param filterType
	 * @param pageNo
	 * @return
	 */
	public static String XintuoSortItem(String category, String defaultType,
			String filterType, String pageNo,String token) {
		XintuoSortItemBean b = new XintuoSortItemBean(category, defaultType,
				filterType, pageNo,token);
		return getResult(b);
	}

	/**
	 * 信托详情
	 * 
	 * @return
	 */
	public static String xintuoDetailsItem(String id,String token) {
		// , String defaultType, int filterType, int pageNo
		XinDetailsItemBean b = new XinDetailsItemBean(id,token);
		// , defaultType, filterType, pageNo
		return getResult(b);
	}

	/**
	 * 信托发送邮件
	 * 
	 * @param email
	 * @param id
	 * @return
	 */
	public static String xintuoSendEmail(String email, String id,String token) {
		// , String defaultType, int filterType, int pageNo
		XintuoEmailBean b = new XintuoEmailBean(email, id,token);
		// , defaultType, filterType, pageNo
		return getResult(b);
	}

	/**
	 * 关注
	 * 
	 * @param category
	 * @param productId
	 */
	public static String mPayAttentionTo(String category, String productId,String token) {
		// , String defaultType, int filterType, int pageNo
		PayAttentionToBean b = new PayAttentionToBean(category, productId,token);
		// , defaultType, filterType, pageNo
		return getResult(b);
	}

	/**
	 * 阳光私募排序
	 * 
	 */
	public static String SunShinePaixuItem(String category, String filterType,
			String pageNo, String sortWay,String token) {
		SunShineItemBean b = new SunShineItemBean(category, filterType, pageNo,
				sortWay,token);
		return getResult(b);
	}

	/**
	 * 阳光私募筛选
	 * 
	 */
	public static String SunShineSaiXuanItem(String category,
			String companyList, String filterType, String fundList,
			String investmentTypeList, String sortWay, String statusList,
			String pageNo,String token) {
		SunShineSaiXuanItemBean b = new SunShineSaiXuanItemBean(category,
				companyList, filterType, fundList, investmentTypeList, sortWay,
				statusList, pageNo,token);
		return getResult(b);
	}

	/**
	 * 阳光私募详情
	 * 
	 */
	public static String SunShineDetailsItem(String id,String token) {
		SunShineDetailsItemBean b = new SunShineDetailsItemBean(id, "ygsm",token);
		return getResult(b);
	}

	/**
	 * 私募股权排序
	 * 
	 */
	public static String EquityPaiXuResult(String category, String filterType,
			String pageNo, String sortWay,String token) {
		EquityPaiXuItemBean b = new EquityPaiXuItemBean(category, filterType,
				pageNo, sortWay,token);
		return getResult(b);
	}

	/**
	 * 私募股权筛选
	 * 
	 */
	public static String EquiteSaiXuanItem(String category, String filterType,
			String fundList, String investmentTypeSMList, String sortWay,
			String statusList, String pageNo,String token) {
		EquiteSaiXuanItemBean b = new EquiteSaiXuanItemBean(category,
				filterType, fundList, investmentTypeSMList, sortWay,
				statusList, pageNo,token);
		return getResult(b);
	}

	/**
	 * 私募股权详情
	 * 
	 */
	public static String EquityDetailsItem(String id,String token) {
		EquityDetailsItemBean b = new EquityDetailsItemBean(id, "smgq",token);
		return getResult(b);
	}

	/**
	 * 我要预约
	 * 
	 */
	public static String AppointmentContent(String ageCoverage, String amount,
			String customerName, String customerPhone,
			String deputyInsuranceName, String payLimitTime,
			String productCategory, String productId, String productName,
			String rebateProportion, String remark) {
		AppointmentContentBean b = new AppointmentContentBean(ageCoverage,
				amount, customerName, customerPhone, deputyInsuranceName,
				payLimitTime, productCategory, productId, productName,
				rebateProportion, remark);
		return getResult(b);
	}

	/**
	 * 保险
	 * 
	 */
	public static String mInsuranceResult(String filterType, String pageNo,
			String sortType,String token) {
		InsurancetempBean b = new InsurancetempBean(filterType, pageNo,
				sortType,token);
		return getResult(b);
	}

	/**
	 * 保险筛选
	 * 
	 */
	public static String SInsuranceResult(String category,
			String companyShortName, String filterType, String pageNo,
			String sortType,String token) {
		SInsurancetempBean b = new SInsurancetempBean(category,
				companyShortName, filterType, pageNo, sortType,token);
		return getResult(b);
	}

	/**
	 * 保险详情
	 * 
	 */
	public static String InsuranceDetailsResult(String id,String token) {
		InsuranceDetailsResultBean b = new InsuranceDetailsResultBean(id,token);
		return getResult(b);
	}

	/**
	 * 保险详情提交
	 * 
	 */
	public static String PostInsurance(String ageCoverage, String amount,
			String currency, String customerName, String customerPhone,
			String deputyInsuranceAmount, String deputyInsuranceName,
			String deputyRebateProportion, String payLimitTime,
			String productCategory, String productId, String productName,
			String rebateProportion, String remark, String totalamount,
			String userName, String userPhone,String token) {
		PostInsuranceResultBean b = new PostInsuranceResultBean(ageCoverage,
				amount, currency, customerName, customerPhone,
				deputyInsuranceAmount, deputyInsuranceName,
				deputyRebateProportion, payLimitTime, productCategory,
				productId, productName, rebateProportion, remark, totalamount,
				userName, userPhone,token);
		return getResult(b);
	}

	/**
	 * 修改备注信息
	 * 
	 */
	public static String getCustomerModifyBeizhu(String remark,
			String customerId,String token) {
		CustomerModifyBeizhuBean b = new CustomerModifyBeizhuBean(customerId,
				remark,token);

		return getResult(b);
	}

	/**
	 * Ta成交的报单
	 * 
	 */
	public static String getHisBaoDan(String idcard, String page,String token) {
		HisBaoDanBean b = new HisBaoDanBean(idcard, page,token);

		return getResult(b);
	}

	/**
	 * Ta的日程
	 * 
	 */
	public static String getHisSchedule(String customerId, String page,String token) {
		HisScheduleBean b = new HisScheduleBean(customerId, page,token);

		return getResult(b);
	}

	/**
	 * 他的日程
	 * 
	 */
	public static String getHisScedule() {
		// MyBankList b = new MyBankList();
		// MyAccout b = new MyAccout();
		String b = "";

		return getResult(b);
	}

	/**
	 * 新增客户
	 * 
	 */
	public static String getCustomerAdd(String operation, String customerType,
			String code, String name, String sex, String idcard,
			String mobilePhone, String valueAssessment,String token) {
		CustomerAddBean b = new CustomerAddBean(operation, customerType, code,
				name, sex, idcard, mobilePhone, valueAssessment,token);

		return getResult(b);
	}

	/**
	 * 删除客户
	 * 
	 */
	public static String getCustomerDetele(String id,String token) {
		CustomerDeteleBean b = new CustomerDeteleBean(id,token);
		return getResult(b);
	}

	/**
	 * 修改用户基本信息
	 * 
	 */
	public static String getCustomerBaseInfoSave(String id, String operation,
			String customerType, String code, String name, String sex,
			String age, String idcard, String nation, String company,
			String position, String profession,String token) {
		CustomerInfoSaveBean b = new CustomerInfoSaveBean(id, operation,
				customerType, code, name, sex, age, idcard, nation, company,
				position, profession,token);
		return getResult(b);
	}

	/**
	 * 修改用户联系方式
	 * 
	 */
	public static String getCustomerPhoneSave(String id, String operation,
			String homePhone, String mobilePhone, String prePhone,
			String qqNumber, String fax, String email, String birthProvince,
			String birthCity, String liveProvince, String liveCity,String token) {
		CustomerPhoneSaveBean b = new CustomerPhoneSaveBean(id, operation,
				homePhone, mobilePhone, prePhone, qqNumber, fax, email,
				birthProvince, birthCity, liveProvince, liveCity,token);
		return getResult(b);
	}

	/**
	 * 修改用户意向
	 * 
	 */
	public static String getCustomerIntentionSave(String id, String operation,
			String investType, String investTrade, String investMoney,
			String investYear, String investIncome,String token) {
		CustomerIntentionSaveBean b = new CustomerIntentionSaveBean(id,
				operation, investType, investTrade, investMoney, investYear,
				investIncome,token);
		return getResult(b);
	}

	/**
	 * 修改用户财务状况
	 * 
	 */
	public static String getCustomerFinancialSave(String id, String operation,
			String car, String house, String valueAssessment, String yearSalary,String token) {
		CustomerFinancialSaveBean b = new CustomerFinancialSaveBean(id,
				operation, car, house, valueAssessment, yearSalary,token);
		return getResult(b);
	}

	/**
	 * 修改用户兴趣爱好
	 * 
	 */
	public static String getCustomerInstestSave(String id, String operation,
			String interestSport, String interestArt, String interestArder,String token) {
		CustomerInstestSaveBean b = new CustomerInstestSaveBean(id, operation,
				interestSport, interestArt, interestArder,token);
		return getResult(b);
	}

	/**
	 * 选择客户
	 * 
	 */
	public static String getSelectCustomer(String customerName,String token) {
		SelectCustomerBean b = new SelectCustomerBean(customerName,token);
		return getResult(b);
	}

	/**
	 * 跟踪客户
	 * 
	 */
	public static String getGenzongCustomer(String customerId, String status,
			String timeType, String startTime,String page,String token) {
		GenzongCustomerBean b = new GenzongCustomerBean(customerId, status,
				timeType, startTime,page,token);
		return getResult(b);
	}

	/**
	 * 新建日程
	 * 
	 */
	public static String getAddSchedule(String customerName, String customerId,
			String type, String topic, String status, String endTime,
			String startTime, String scheduleAmind, String amindTime,
			String scheduleDesc,String token) {
		AddScheduleBean b = new AddScheduleBean(customerName, customerId, type,
				topic, status, endTime, startTime, scheduleAmind, amindTime,
				scheduleDesc,token);
		return getResult(b);
	}
	/**
	 * 修改日程
	 * 
	 */
	public static String getEditSchedule(String id,String customerName, String customerId,
			String type, String topic, String status, String endTime,
			String startTime, String scheduleAmind, String amindTime,
			String scheduleDesc,String token) {
		EditScheduleBean b = new EditScheduleBean(id,customerName, customerId, type,
				topic, status, endTime, startTime, scheduleAmind, amindTime,
				scheduleDesc,token);
		return getResult(b);
	}
	/**
	 * 获取全部客户
	 * 
	 */
	public static String getAllCustomer() {
		AllCustomerBean b = new AllCustomerBean();
		return getResult(b);
	}
	/**
	 * 日程详情
	 * 
	 */
	public static String getScheduleDetails(String scheduleId,String token) {
		ScheduleDetailsBean b = new ScheduleDetailsBean(scheduleId,token);
		return getResult(b);
	}
	/**
	 * 删除日程
	 * 
	 */
	public static String getScheduleDetailsDelete(String id,String token) {
		ScheduleDetailsDeleteBean b = new ScheduleDetailsDeleteBean(id,token);
		return getResult(b);
	}

}
