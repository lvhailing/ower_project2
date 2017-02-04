package com.cf360;


public class ApplicationConsts {
    public static final int REL_DEBUG = 1;// 线下服务器
    public static final int REL_OFFICIAL = 2;// 正式服务器

    public static int RELEASE_FLAG = REL_DEBUG; //测试环境
//    public static int RELEASE_FLAG = REL_OFFICIAL;  //正式环境

    // 线下服务器开关,线下地址优先级高于预发布地址
    public static final boolean DEBUG = RELEASE_FLAG == REL_DEBUG;

    // 正式服务器开关
    public static final boolean OFFICIAL = RELEASE_FLAG == REL_OFFICIAL;

    // public static final String URL_DEBUG = "http://192.168.1.176:9999/customerApp/";

    // 彩铃
//	 public static final String URL_DEBUG = "http://192.168.1.90:9999/customerApp/";

    // 苏冰雪IP
    //	public static final String URL_DEBUG = "http://114.113.238.90:50003/vjinke-customerApp/";

    // 李迎欣
    //public static final String URL_DEBUG = "http://192.168.1.162:9888/cf360-customerApp/";

    //	 测试环境IP
    public static final String URL_DEBUG = "http://192.168.1.82:8083/";

    //代树理
    //public static final String URL_DEBUG ="http://192.168.1.106:9999/cf360-customerApp/";

    //邢玉洁
    // public static final String URL_DEBUG = "http://192.168.1.125:9933/cf360-customerApp/";

    //	黄国虎
    // public static final String URL_DEBUG ="http://192.168.1.193:9994/cf360-customerApp/";

    //	李来锋
    //public static final String URL_DEBUG ="http://192.168.1.157:9999/cf360-customerApp/";
    //	常亚萍
    //public static final String URL_DEBUG ="http://192.168.1.170:9999/cf360-customerApp/";

    // 张殿洋
    //public static final String URL_DEBUG ="http://192.168.1.138:9999/customerApp/";


    //public static final String URL_DEBUG ="http://192.168.1.193:9991/cf360-customerApp/";


    // 正式环境IP
//	public static final String URL_OFFICIAL = "http://app.cf360.com/";
    public static final String URL_OFFICIAL = "https://app.cf360.com/";

    // 开发使用
    public static final String EC_HOST_T = URL_DEBUG;
    // 开发使用
    public static final String EC_HOST_P = URL_OFFICIAL;

    public static final String EC_HOST = DEBUG ? EC_HOST_T : EC_HOST_P;

    /**
     * 获取合同列表
     */
    public static final String URL_CONTRACT_LIST = EC_HOST + "android/contract/list";
    /**
     * 获取投保单列表
     */
    public static final String URL_TOUBAODAN_LIST = EC_HOST + "android/insurancePolicy/queryPolicylist";
    /**
     * 获取报单列表
     */
    public static final String URL_DECLARATION_LIST = EC_HOST + "android/order/list";
    /**
     * 选择产品
     */
    public static final String URL_SELECT_PRODUCT = EC_HOST + "productQuery/commonQuery";
    /**
     * 选择产品return
     */
    public static final String URL_SELECT_PRODUCT_RETURN = EC_HOST + "productQuery/selectProduct";
    /**
     * 获取合同详情
     */
    public static final String URL_CONTRACT_DETAILS = EC_HOST + "android/contract/detail";
    /**
     * 获取保单详情
     */
    public static final String URL_TOUBAODAN_DETAILS = EC_HOST + "insurancePolicy/queryPolicyOrderDetial";
    /**
     * 获取报单详情
     */
    public static final String URL_DECLARATION_DETAILS = EC_HOST + "order/detail";
    /**
     * 修改报单
     */
    public static final String URL_EDIT_DECLARATION_DETAILS = EC_HOST + "order/toEditOrder";
    /**
     * 保存修改报单
     */
    public static final String URL_SAVE_EDIT_DECLARATION_DETAILS = EC_HOST + "order/saveEditOrder";
    /**
     * 获取保险产品的返佣比例
     */
    public static final String URL_SEARCHCOMMISSIONRATIO = EC_HOST + "order/searchCommissionratio";
    /**
     * 获取预约详情
     */
    public static final String URL_ORDER_DETAILS = EC_HOST + "appointment/queryAppointmentById";
    /**
     * 寄回合同
     */
    public static final String URL_POST_CONTRACT = EC_HOST + "android/contract/saveExpressBack";
    /**
     * 寄回保单
     */
    public static final String URL_POST_TOUBAODAN = EC_HOST + "insurancePolicy/sendBackPolicyOrder";
    /**
     * 申请合同
     */
    public static final String URL_APPLY_CONTRACT = EC_HOST + "android/contract/save";
    /**
     * 申请投保单
     */
    public static final String URL_APPLY_TOUBAODAN = EC_HOST + "insurancePolicy/applyPolicy";
    /**
     * 申请报单
     */
    public static final String URL_APPLY_DECLARATION = EC_HOST + "order/addOrder";
    /**
     * 签收合同
     */
    public static final String URL_SIGN_CONTRACT = EC_HOST + "android/contract/sign";
    /**
     * 签收投保单
     */
    public static final String URL_SIGN_TOUBAODAN = EC_HOST + "insurancePolicy/singInsurancePolicy";
    /**
     * 获取预约列表
     */
    public static final String URL_ORDER_LIST = EC_HOST + "android/appointment/queryAppointmentList";
    /**
     * 取消预约
     */
    public static final String URL_CANCEL_ORDER = EC_HOST + "appointment/cancelAppointmentById";


    /**
     * 通过手机号找回密码
     */
    public static final String URL_FINDPWDBYPHONE = EC_HOST + "android/user/password/retrieve";

    /***
     * 修改手机号验证码验证
     */
    public static final String URL_CHANGEPHONEVERIFY = EC_HOST + "account/mobile/verify";

    /**
     * 修改密码
     */
    public static final String URL_CHANGEPWD = EC_HOST + "android/user/password/modify";


    /***
     * 注册一
     */
    public static final String URL_SIGNUP_ONE = EC_HOST + "android/user/registerOne";
    /***
     * 注册二
     */
    public static final String URL_SIGNUP_TWO = EC_HOST + "android/user/registerFinish";

    /**
     * 登陆
     */
    public static final String URL_LOGIN = EC_HOST + "android/user/login";


    /**
     * 热销产品
     */
    public static final String URL_HOTPRODUCT = EC_HOST + "index/hotProduct";
    /**
     * 我的消息数量
     */
    public static final String URL_MYMESSAGE = EC_HOST + "index/unReadMessage";
    /**
     * 推荐产品
     */
    public static final String URL_RECOMMENDPRODUCT = EC_HOST + "index/recommendProduct";
    /**
     * 我的关注
     */
    public static final String URL_FOCUS = EC_HOST + "attention/attentionedProductList";
    /**
     * 我的银行卡
     */
    public static final String URL_MYBANKLIST = EC_HOST + "android/userBankCard/list";
    /**
     * 获取银行信息
     */
    public static final String URL_BANKLISTMESSAGE = EC_HOST + "android/userBankcard/openBank";
    /**
     * 绑定银行卡
     */
    public static final String URL_BINDBANKCARD = EC_HOST + "android/userBankcard/bindBank";
    /**
     * 解绑银行卡
     */
    public static final String URL_UNBINDBANKCARD = EC_HOST + "android/userBankCard/unBindBankCard";
    /**
     * 我的账户
     */
    public static final String URL_MYACCOUNT = EC_HOST + "member/myAccount";
    /**
     * 是否登陆
     */
    public static final String URL_IS_LOGIN = EC_HOST + "android/user/isLogin";

    /**
     * 上传名片
     */
    public static final String URL_FINANCIALCART = EC_HOST + "auditUser/android/uploadFile";
    /**
     * 理财师认证
     */
    public static final String URL_FINANCIALAUDIT = EC_HOST + "android/user/toAudit";
    /**
     * 理财师认证信息显示判断
     */
    public static final String URL_FINANCIALTOUSERAUDIT = EC_HOST + "android/user/toUserAudit";
    /**
     * 保险代理认证
     */
    public static final String URL_INSURANCEAUDIT = EC_HOST + "insuranceAgentAudit/applyInsuranceAgent";
    /**
     * 保险代理信息验证
     */
    public static final String URL_ISINSURANCEAUDIT = EC_HOST + "insuranceAgentAudit/isInsuranceAgentAudited";
    /**
     * 机构认证
     */
    public static final String URL_INSURANCEAGENTAUDIT = EC_HOST + "insuranceAgentAudit/insuranceAgentOrgDetail";
    /**
     * 产品搜索
     */
    public static final String URL_SEARCHPRODUCT = EC_HOST + "productQuery/query";
    /**
     * 选择提现账户
     */
    public static final String URL_CHOSEBANKLIST = EC_HOST + "account/shroffAccount";
    /**
     * 确认提现
     */
    public static final String URL_WITHDRAWVERIFY = EC_HOST + "account/addWithdraw";
    /**
     * 收支明细
     */
    public static final String URL_INFODATA = EC_HOST + "incomeStatement/list";
    /**
     * 收支明细详情
     */
    public static final String URL_INFODETAILDATA = EC_HOST + "incomeStatement/detail";

    /**
     * 我的消息列表
     */
    public static final String URL_MESSAGELISTDATA = EC_HOST + "android/userMessage/list";

    /**
     * 我的消息详情
     */
    public static final String URL_MESSAGEDETAILDATA = EC_HOST + "android/userMessage/detail";

    /**
     * 公告
     */
    public static final String URL_NOTICELISTDATA = EC_HOST + "android/webSiteBulletin/list";

    /**
     * 检查版本
     */
    public static final String URL_CHECKVERSION = EC_HOST + "version/check";

    /**
     * 获取轮播图
     */
    public static final String URL_GETADVERTISE = EC_HOST + "turn/advertise";

    /**
     * 登出
     */
    public static final String URL_LOGINOFF = EC_HOST + "android/user/logoff";

    /**
     * 发送手机短信
     */
    public static final String URL_SMS = EC_HOST + "android/user/mobile/send/verifycode";
    /**
     * 发送手机短信For申请合同
     */
    public static final String URL_SMS_APPLY_CONTRACT = EC_HOST + "android/user/submitcontract/send/verifycode";
    /**
     * 发送手机短信我要预约
     */
    public static final String URL_SMS_APPOINTMENT = EC_HOST + "android/user/submitappointment/send/verifycode";
    /**
     * 发送手机短信For申请投保单
     */
    public static final String URL_SMS_APPLY_TOUBAODAN = EC_HOST + "android/user/submitpolicy/send/verifycode";
    /**
     * 发送手机短信For申请报单
     */
    public static final String URL_SMS_APPLY_DECLARATION = EC_HOST + "android/user/submitorder/send/verifycode";


    /**
     * 注册服务协议
     **/
    public static final String URL_REGISTERAGREEMENT = EC_HOST + "registerAgreement";

    /***
     * 下载图片
     */
    public static final String URL_GETUPDATEPHOTOSTATE = EC_HOST + "order/android/uploadFile";


    /**
     * 帮助中心列表
     */
    public static final String URL_HELPLISTDATA = EC_HOST + "android/help/list";

    /**
     * 帮助详情
     */
    public static final String URL_HELPDETAILDATA = EC_HOST + "help/detail";
    /**
     * 意见反馈
     */
    public static final String URL_ADVICEDATA = EC_HOST + "problem/reply/save";

    /**
     * 信托 资管 列表
     */
    public static final String URL_TRUSTPAIXU = EC_HOST + "android/productTrust/list";

    /**
     * 信托 资管 详情
     */
    public static final String URL_TRUSTXANGQING = EC_HOST + "productTrust/queryProductTrustById";

    /**
     * 阳光
     */
    public static final String URL_SUNSHINE = EC_HOST + "android/productPrivate/list";
    /**
     * 阳光详情
     */
    public static final String URL_SUNSHINEXIANGQING = EC_HOST + "android/productPrivate/detail";

    /**
     * 保险
     */
    public static final String URL_INSURANCE = EC_HOST + "insuranceProduct/queryInsuranceProductList";

    /**
     * 保险详情
     */
    public static final String URL_PRODUCTINSURANCE = EC_HOST + "productInsurance/productInsuranceDetail";

    /**
     * 预约
     */

    public static final String URL_ATTENTION = EC_HOST + "attention/changeAttentionStatus";
    /**
     * 我要预约
     */

    public static final String URL_APPOINTMENT = EC_HOST + "appointment/addAppointment";

    /**
     * 我的客户
     */
    public static final String URL_USERCUSTOMER = EC_HOST + "customer/userCustomerList";
    /**
     * 修改备注信息
     */
    public static final String URL_CUSTOMERMODIFYBEIZHU = EC_HOST + "customer/userCustomerModifyBeizhu";
    /**
     * Ta成交的报单
     */
    public static final String URL_HIS_BAODAN = EC_HOST + "android/customer/orderList";
    /**
     * Ta的日程
     */
    public static final String URL_HIS_SCHEDULE = EC_HOST + "android/userCustomerSchedule/scheduleList";

    /**
     * 新增客户
     */
    public static final String URL_CUSTOMERADD = EC_HOST + "android/customer/addOrEditCustomer";
    /**
     * 删除客户
     */
    public static final String URL_CUSTOMERDETELE = EC_HOST + "android/customer/deleteCustomer";
    /**
     * 获取客户详细信息
     */
    public static final String URL_CUSTOMERINFO = EC_HOST + "android/customer/toAddOrEditCustomer";
    /**
     * 修改客户基本信息
     */
    public static final String URL_CUSTOMERINFOSAVE = EC_HOST + "android/customer/addOrEditCustomer";
    /**
     * 选择客户
     */
    public static final String URL_SELECT_CUSTOMER = EC_HOST + "android/customer/selectCustomerByName";
    /**
     * 获取客户跟踪中的全部客户
     */
    public static final String URL_ALL_CUSTOMER = EC_HOST + "android/customer/selectCustomerByName";
    /**
     * 客户跟踪select
     */
    public static final String URL_SELECT_GENZONG_CUSTOMER = EC_HOST + "android/userCustomerSchedule/genZongCustomerList";
    /**
     * 新建日程
     */
    public static final String URL_ADD_SCHEDULE = EC_HOST + "android/userCustomerSchedule/addCustomerSchedule";
    /**
     * 修改日程
     */
    public static final String URL_EDIT_SCHEDULE = EC_HOST + "android/userCustomerSchedule/updatCustomerSchedule";
    /**
     * 日程详情
     */
    public static final String URL_SCHEDULE_DETAILS = EC_HOST + "android/userCustomerSchedule/scheduleDetail";
    /**
     * 删除日程
     */
    public static final String URL_SCHEDULE_DETAILS_DETELE = EC_HOST + "android/userCustomerSchedule/deleteCustomerSchedule";
    /**
     * 信托项目亮点
     */
    public static final String URL_WEB_RODUCT_ADVANTAGE = EC_HOST + "productTrust/toAdvertageDetailForApp";
    /**
     * 私募股权产品介绍
     */
    public static final String URL_WEB_EQUIT_RODUCT_ADVANTAGE = EC_HOST + "productPrivate/toGraphicDetailForApp";
    /**
     * 保险个人合同协议
     */
    public static final String URL_WEB_EQUIT_INSRANCER = EC_HOST + "insuranceAgentAgreement";
    /**
     * 关于我们
     */
    public static final String URL_WEB_EQUIT_ABOUTUS = EC_HOST + "android/aboutUs";
    /*
     * 发送邮件
	 * */

    public static final String URL_XINPOSTMAIL = EC_HOST + "android/xtzg/emailSendForApp";
    public static final String URL_ZIPOSTMAIL = EC_HOST + "android/xtzg/emailSendForApp";
    public static final String URL_SUNPOSTMAIL = EC_HOST + "android/ygsm/emailSendForApp";
    public static final String URL_SMGQPOSTMAIL = EC_HOST + "android/smgq/emailSendForApp";
    public static final String URL_INSURANCEMAIL = EC_HOST + "android/bx/emailSendForApp";
    // *//* 手势密码点的状态*/
    public static final int POINT_STATE_NORMAL = 0; // 正常状态

    public static final int POINT_STATE_SELECTED = 1; // 按下状态

    public static final int POINT_STATE_WRONG = 2; // 错误状态
    // */

    /**
     * register:用户注册
     */
    public static final String REGISTER = "register";
    /**
     * submitcontract:申请合同发短信
     */
    public static final String APPLY_CONTRACT = "submitcontract";
    /**
     * submitorder:申请报单发短信
     */
    public static final String APPLY_DECLARATION = "submitorder";
    /**
     * submitpolicy:申请投保单发短信
     */
    public static final String APPLY_TOUBAODAN = "submitpolicy";
    /**
     * loginRet:登录密码找回
     */
    public static final String LOGINRET = "loginRet";


    public static final String TENDERCOMPLETE = "success";
    public static final String PREPAYED = "prepayed";

    public static final String NEWERPRODUCT = "yes";
    public static final String FINISHEDPRODUCT = "no";

    public static final String SELLING = "selling";
    public static final String SOLDUNREPAY = "soldUnRepay";
    public static final String SOLDREPAYED = "soldRepayed";

    public static final String GUARANTEETYPE_NO = "no";
    public static final String GUARANTEETYPE_PRINCIPAL = "principal";
    public static final String GUARANTEETYPE_PRINCIPALANDINTEREST = "principalAndInterest";

    public static final String SUCCESS = "0000";
    public static final String FAIL_NOLOGIN = "1000";

    public static final String ACTIVITY_SPLASH = "activity_splash";
    public static final String ACTIVITY_GESEDIT = "activity_gesedit";
    public static final String ACTIVITY_GESVERIFY = "activity_gesverify";
    public static final String ACTIVITY_ACCOUNTSET = "activity_accountset";

}
