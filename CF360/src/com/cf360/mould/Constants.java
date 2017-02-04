package com.cf360.mould;

/**
 * 
 */
public class Constants {
	static int index = 1;
	/**
	 * 开户
	 */
	public static final String TASK_TYPE_CREATEACCOUNT = String
			.valueOf(index++);

	/**
	 * 检查版本
	 */
	public static final String TASK_TYPE_CHECKVERSION = String.valueOf(index++);

	/***
	 * 意见反馈
	 */
	public static final String TASK_TYPE_ADVICE = String.valueOf(index++);
	/**
	 * 验证手机号是否注册
	 */
	public static final String TASK_TYPE_ISREGISTER = String.valueOf(index++);

	/***
	 * 注册
	 */
	public static final String TASK_TYPE_SIGNUP = String.valueOf(index++);

	/***
	 * 我的账户
	 */
	public static final String TASK_TYPE_MYACCOUNT = String.valueOf(index++);
	/***
	 * 我的财富
	 */
	public static final String TASK_TYPE_MYPERSON = String.valueOf(index++);

	/***
	 * 开户状态
	 */
	public static final String TASK_TYPE_MYACCOUNTSTATE = String
			.valueOf(index++);

	/**
	 * 通过手机号找回密码
	 */
	public static final String TASK_TYPE_FINDPWDBYPHONE = String
			.valueOf(index++);

	/***
	 * 通过手机号找回密码验证码 验证
	 */
	public static final String TASK_TYPE_FINDPWDVERIFY = String
			.valueOf(index++);

	/**
	 * 修改手机号
	 */
	public static final String TASK_TYPE_BINDPHONE = String.valueOf(index++);

	/***
	 * 修改手机号验证码验证
	 */
	public static final String TASK_TYPE_CHANGEPHONEVERIFY = String
			.valueOf(index++);

	/**
	 * 修改密码
	 */
	public static final String TASK_TYPE_CHANGEPWD = String.valueOf(index++);
	/**
	 * 合同列表
	 */
	public static final String TASK_TYPE_CONTRACT_LIST = String.valueOf(index++);
	/**
	 * 投保单列表
	 */
	public static final String TASK_TYPE_TOUBAODAN_LIST = String.valueOf(index++);
	/**
	 * 报单列表
	 */
	public static final String TASK_TYPE_DECLARATION_LIST = String.valueOf(index++);
	/**
	 * 合同详情
	 */
	public static final String TASK_TYPE_CONTRACT_DETAIL = String.valueOf(index++);
	/**
	 * 保单详情
	 */
	public static final String TASK_TYPE_TOUBAODAN_DETAIL = String.valueOf(index++);
	/**
	 * 报单详情
	 */
	public static final String TASK_TYPE_DECLARATION_DETAIL = String.valueOf(index++);
	/**
	 * 获取保险产品的返佣比例 
	 */
	public static final String TASK_TYPE_SEARCHCOMMISSIONRATIO = String.valueOf(index++);
	/**
	 * 选择产品
	 */
	public static final String TASK_TYPE_SELECT_PRODUCT = String.valueOf(index++);
	/**
	 * 选择产品RETURN
	 */
	public static final String TASK_TYPE_SELECT_PRODUCT_RETURN = String.valueOf(index++);
	/**
	 * 预约详情
	 */
	public static final String TASK_TYPE_ORDER_DETAIL = String.valueOf(index++);
	/**
	 * 签收合同
	 */
	public static final String TASK_TYPE_SIGN_CONTRACT = String.valueOf(index++);
	/**
	 * 签收投保单
	 */
	public static final String TASK_TYPE_SIGN_TOUBAODAN = String.valueOf(index++);
	/**
	 * 取消预约
	 */
	public static final String TASK_TYPE_CANCEL_ORDER = String.valueOf(index++);
	/**
	 * 寄回合同
	 */
	public static final String TASK_TYPE_POST_CONTRACT = String.valueOf(index++);
	/**
	 * 寄回保单
	 */
	public static final String TASK_TYPE_POST_TOUBAODAN = String.valueOf(index++);
	/**
	 * 申请合同
	 */
	public static final String TASK_TYPE_APPLY_CONTRACT = String.valueOf(index++);
	/**
	 * 申请投保单
	 */
	public static final String TASK_TYPE_APPLY_TOUBAODAN = String.valueOf(index++);
	/**
	 * 申请报单
	 */
	public static final String TASK_TYPE_APPLY_DECLARATION = String.valueOf(index++);
	/**
	 * 预约列表
	 */
	public static final String TASK_TYPE_ORDER_LIST = String.valueOf(index++);
	/**
	 * 获取用户信息
	 */
	public static final String TASK_TYPE_GETUSERINFO = String.valueOf(index++);

	/***
	 * 确认提现
	 */
	public static final String TASK_TYPE_WITHDRAW = String.valueOf(index++);

	/**
	 * 充值
	 */
	public static final String TASK_TYPE_RECHARGE = String.valueOf(index++);

	/**
	 * 获取轮播图
	 */
	public static final String TASK_TYPE_GETADVERTISE = String.valueOf(index++);

	/**
	 * 登出
	 */
	public static final String TASK_TYPE_LOGINOFF = String.valueOf(index++);

	/**
	 * 发送手机短信
	 */
	public static final String TASK_TYPE_SMS = String.valueOf(index++);
	
	/**
	 * 发送手机短信for申请合同
	 */
	public static final String TASK_TYPE_SMS_FOR_APPLY_CONTRACT= String.valueOf(index++);
	
	/**
	 * 热销产品
	 */
	public static final String TASK_TYPE_HOTPRODUCT = String.valueOf(index++);
	/**
	 * 我的消息数量
	 */
	public static final String TASK_TYPE_MYMESSAGECOUNT = String.valueOf(index++);
	/**
	 * 推荐产品
	 */
	public static final String TASK_TYPE_RECOMMENDPRODUCT = String.valueOf(index++);
	/**
	 * 我的银行卡
	 */
	public static final String TASK_TYPE_MYBANKLIST = String.valueOf(index++);
	/**
	 * 获取银行信息
	 */
	public static final String TASK_TYPE_BANKLISTMESSAGE = String.valueOf(index++);
	/**
	 * 绑定银行卡
	 */
	public static final String TASK_TYPE_BINDBANKCARD = String.valueOf(index++);
	/**
	 * 解绑银行卡
	 */
	public static final String TASK_TYPE_UNBINDBANKCARD = String.valueOf(index++);
	/**
	 * 理财师认证
	 */
	public static final String TASK_TYPE_FINANCIALAUDIT = String.valueOf(index++);
	/**
	 * 理财师认证信息判断显示
	 */
	public static final String TASK_TYPE_FINANCIALTOUSERAUDIT = String.valueOf(index++);
	/**
	 * 保险代理认证
	 */
	public static final String TASK_TYPE_INSURANCEAUDIT = String.valueOf(index++);
	/**
	 * 保险代理信息验证
	 */
	public static final String TASK_TYPE_ISINSURANCEAUDIT = String.valueOf(index++);
	/**
	 * 机构认证
	 */
	public static final String TASK_TYPE_INSURANCEAGENTAUDIT = String.valueOf(index++);
	/**
	 * 选择提现账户
	 */
	public static final String TASK_TYPE_CHOSEBANKLIST = String.valueOf(index++);
	/**
	 * 确认提现
	 */
	public static final String TASK_TYPE_WITHDRAWVERIFY = String.valueOf(index++);
	/**
	 * 理财师认证
	 */
	public static final String TASK_TYPE_SEARCHPRODUCT = String.valueOf(index++);
	/**
	 * 收支明细
	 */
	public static final String TASK_TYPE_INFODATA = String.valueOf(index++);
	/**
	 * 收支明细详情
	 */
	public static final String TASK_TYPE_INFODETAILDATA = String.valueOf(index++);
	/**
	 * 帮助中心列表
	 */
	public static final String TASK_TYPE_HELPLISTDATA = String.valueOf(index++);
	/**
	 * 帮助详情
	 */
	public static final String TASK_TYPE_HELPDETAILDATA = String.valueOf(index++);
	/**
	 * 意见反馈
	 */
	public static final String TASK_TYPE_ADVICEDATA = String.valueOf(index++);
	/**
	 * 我的消息列表
	 */
	public static final String TASK_TYPE_MESSAGELIST = String.valueOf(index++);
	/**
	 * 我的消息详情
	 */
	public static final String TASK_TYPE_MESSAGEDETAIL = String.valueOf(index++);
	/**
	 * 公告列表
	 */
	public static final String TASK_TYPE_NOTICELIST = String.valueOf(index++);
	/**
	 * 信托推荐产品
	 */
	public static final String TASK_TYPE_RECOMMENDTRUSTXT = String.valueOf(index++);
	/**
	 * 资管推荐产品
	 */
	public static final String TASK_TYPE_RECOMMENDTRUSTZG = String.valueOf(index++);
	/**
	 * 阳光私募推荐产品
	 */
	public static final String TASK_TYPE_RECOMMENDTRUSTYGSM = String.valueOf(index++);
	/**
	 * 私募股权推荐产品
	 */
	public static final String TASK_TYPE_RECOMMENDPRIVATESMGQ = String.valueOf(index++);
	/**
	 * 保险推荐产品
	 */
	public static final String TASK_TYPE_RECOMMENDINSURANCE = String.valueOf(index++);

	/***
	 * 修改备注信息
	 */
	public static final String TASK_TYPE_USERCUSTOMERMODIFYBEIZHU = String.valueOf(index++);
	/***
	 * Ta成交的报单
	 */
	public static final String TASK_TYPE_HIS_BAODAN = String.valueOf(index++);
	
	/***
	 * 他的日程
	 */
	public static final String TASK_TYPE_CUSTOMERSCHEDULE = String.valueOf(index++);
	/***
	 * 新增客户
	 */
	public static final String TASK_TYPE_CUSTOMERSADD = String.valueOf(index++);
	/***
	 * 删除客户
	 */
	public static final String TASK_TYPE_CUSTOMERSDETELE = String.valueOf(index++);
	/***
	 * 获取客户详细信息
	 */
	public static final String TASK_TYPE_CUSTOMERSINFO = String.valueOf(index++);
	/***
	 * 获取客户基本信息修改
	 */
	public static final String TASK_TYPE_CUSTOMERSINFOSAVE = String.valueOf(index++);
	/***
	 * 判断用户是否登录
	 */
	public static final String TASK_TYPE_IS_LOGIN = String.valueOf(index++);
	
}
