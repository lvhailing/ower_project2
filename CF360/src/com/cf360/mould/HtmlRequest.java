package com.cf360.mould;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.cf360.ApplicationConsts;
import com.cf360.bean.PayAttentionContentBean;
import com.cf360.bean.ResultAddScheduleBean;
import com.cf360.bean.ResultAdvertiseBean;
import com.cf360.bean.ResultAdviceBean;
import com.cf360.bean.ResultApplyContractBean;
import com.cf360.bean.ResultApplyDeclarationBean;
import com.cf360.bean.ResultApplyToubaodanBean;
import com.cf360.bean.ResultBankListMessageBean;
import com.cf360.bean.ResultCancelOrderBean;
import com.cf360.bean.ResultCheckVersionBean;
import com.cf360.bean.ResultChoseBankListBean;
import com.cf360.bean.ResultContractListBean;
import com.cf360.bean.ResultContractTrackListBean;
import com.cf360.bean.ResultCustomerInfoBean;
import com.cf360.bean.ResultCustomerModifyBean;
import com.cf360.bean.ResultDeclarationDetailsListBean;
import com.cf360.bean.ResultDeclarationListBean;
import com.cf360.bean.ResultDeclarationSearchCommissionratioListBean;
import com.cf360.bean.ResultEditDeclarationListBean;
import com.cf360.bean.ResultEquityDetailItemBean;
import com.cf360.bean.ResultEquityItemBean;
import com.cf360.bean.ResultFinancialAgentAuditBean;
import com.cf360.bean.ResultFinancialAuditBean;
import com.cf360.bean.ResultFinancialToUserAuditBean;
import com.cf360.bean.ResultFindPWDbyPhoneBean;
import com.cf360.bean.ResultFocusBean;
import com.cf360.bean.ResultGenZongCustomerBean;
import com.cf360.bean.ResultHelpDetailBean;
import com.cf360.bean.ResultHelpListBean;
import com.cf360.bean.ResultHisBaoDanBean;
import com.cf360.bean.ResultHisScheduleBean;
import com.cf360.bean.ResultHotProductBean;
import com.cf360.bean.ResultInfoDataBean;
import com.cf360.bean.ResultInfoDetailDataBean;
import com.cf360.bean.ResultInsAppointContentBean;
import com.cf360.bean.ResultInsuranceDetialItemBean;
import com.cf360.bean.ResultInsuranceItemBean;
import com.cf360.bean.ResultIsInsuranceBean;
import com.cf360.bean.ResultIsLoginBean;
import com.cf360.bean.ResultIsRegisterBean;
import com.cf360.bean.ResultLoginOffBean;
import com.cf360.bean.ResultMessageListDataBean;
import com.cf360.bean.ResultMyAccountBean;
import com.cf360.bean.ResultMyBankListBean;
import com.cf360.bean.ResultMyCustomerBean;
import com.cf360.bean.ResultMyCustomerInfoDetailBean;
import com.cf360.bean.ResultMyMessageCountBean;
import com.cf360.bean.ResultMyPersonBean;
import com.cf360.bean.ResultNoticeListDataBean;
import com.cf360.bean.ResultOrderDetailListBean;
import com.cf360.bean.ResultOrderListBean;
import com.cf360.bean.ResultPostContractBean;
import com.cf360.bean.ResultRecommendProductBean;
import com.cf360.bean.ResultScheduleDetailsBean;
import com.cf360.bean.ResultSearchProductBean;
import com.cf360.bean.ResultSelectCustomerBean;
import com.cf360.bean.ResultSelectProductListBean;
import com.cf360.bean.ResultSelectProductReturnListBean;
import com.cf360.bean.ResultSentSMSBean;
import com.cf360.bean.ResultSignContractBean;
import com.cf360.bean.ResultSignupBean;
import com.cf360.bean.ResultSunShineItemBean;
import com.cf360.bean.ResultSunShineItemDetailBean;
import com.cf360.bean.ResultToubaodanListBean;
import com.cf360.bean.ResultToubaodanTrackListBean;
import com.cf360.bean.ResultUnBankMessageBean;
import com.cf360.bean.ResultWithdrawBean;
import com.cf360.bean.ResultWithdrawVerifyBean;
import com.cf360.bean.ResultXinDetailsItemBean;
import com.cf360.bean.ResultXinPopMeailBean;
import com.cf360.bean.ResultXinTuoItemBean;
import com.cf360.bean.ResutlPostContentBean;
import com.cf360.http.SimpleHttpClient;
import com.cf360.mould.types.IMouldType;
import com.cf360.uitls.DESUtil;
import com.cf360.uitls.PreferenceUtil;
import com.google.gson.Gson;

public class HtmlRequest extends BaseRequester {

    private static String url_two;

    /**
     * 同步一下cookie
     */
    public static void synCookies(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        // cookieManager.removeSessionCookie();// 移除
        try {
            cookieManager.setCookie(url, DESUtil.decrypt(PreferenceUtil.getCookie()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        CookieSyncManager.getInstance().sync();
    }


    /**
     * 发送手机短信
     *
     * @param context    上下文
     * @param userMobile 用户手机号
     * @param busiType   类型
     * @param mathRandom 随机数
     * @param listener   监听
     * @return 返回数据
     */


    public static String sentSMS(final Context context, String userMobile, String busiType, String mathRandom, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getSMS(userMobile, busiType, mathRandom);
        final String url = ApplicationConsts.URL_SMS;
        String tid = registerId(Constants.TASK_TYPE_SMS, url);
        // HtmlRequest.synCookies(context, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SMS, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultSentSMSBean b = json.fromJson(data, ResultSentSMSBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 发送手机短信(申请合同)
     *
     * @param context     上下文
     * @param mobile      手机
     * @param busiType    类型
     * @param contractId  合同id
     * @param productName 产品名称
     * @param listener    监听
     * @return 返回类型
     */

    public static String sentSMSForApplyContract(final Context context, String mobile, String busiType, String contractId, String productName, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getSMSForApplyContract(mobile, busiType, contractId, productName);
        final String url = ApplicationConsts.URL_SMS_APPLY_CONTRACT;
        String tid = registerId(Constants.TASK_TYPE_SMS_FOR_APPLY_CONTRACT, url);
        // HtmlRequest.synCookies(context, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SMS_FOR_APPLY_CONTRACT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultSentSMSBean b = json.fromJson(data, ResultSentSMSBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }


    /**
     * 发送手机短信(我要预约成功)
     *
     * @param context      上下文
     * @param productName  产品名称
     * @param amount       数量
     * @param appoitmentId 预约id
     * @param busiType     类型
     * @param listener     监听
     * @return 返回数据
     */
    public static String sentSMSForAppointment(final Context context, String productName, String amount, String appoitmentId, String busiType, OnRequestListener listener) {


        final String data = HtmlLoadUtil.getSMSForAppointment(productName, amount, appoitmentId, busiType);
        final String url = ApplicationConsts.URL_SMS_APPOINTMENT;
        String tid = registerId(Constants.TASK_TYPE_SMS_FOR_APPLY_CONTRACT, url);
        // HtmlRequest.synCookies(context, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SMS_FOR_APPLY_CONTRACT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultSentSMSBean b = json.fromJson(data, ResultSentSMSBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 发送手机短信(申请投保单)
     *
     * @param context  1
     * @param listener 1
     * @return 1
     */
    public static String sentSMSForApplyTouBaoDan(final Context context, String busiType, String policyId, String productName, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getSMSForApplyTouBaoDan(busiType, policyId, productName, token);
        final String url = ApplicationConsts.URL_SMS_APPLY_TOUBAODAN;
        String tid = registerId(Constants.TASK_TYPE_SMS_FOR_APPLY_CONTRACT, url);
        // HtmlRequest.synCookies(context, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SMS_FOR_APPLY_CONTRACT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultSentSMSBean b = json.fromJson(data, ResultSentSMSBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 发送手机短信(申请报单)
     *
     * @param context  1
     * @param listener 1
     * @return 1
     */
    public static String sentSMSForApplyDeclaration(final Context context, String busiType, String orderId, String productName, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getSMSForApplyDeclaration(busiType, orderId, productName, token);
        final String url = ApplicationConsts.URL_SMS_APPLY_DECLARATION;
        String tid = registerId(Constants.TASK_TYPE_SMS_FOR_APPLY_CONTRACT, url);
        // HtmlRequest.synCookies(context, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SMS_FOR_APPLY_CONTRACT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultSentSMSBean b = json.fromJson(data, ResultSentSMSBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 登出
     *
     * @param context  1
     * @param listener 1
     * @return 1
     */
    public static String loginoff(final Context context, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String data = HtmlLoadUtil.getMyAccount(token);
        final String url = ApplicationConsts.URL_LOGINOFF;
        String tid = registerId(Constants.TASK_TYPE_LOGINOFF, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_LOGINOFF, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        ResultLoginOffBean b = json.fromJson(result, ResultLoginOffBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 修改手机号 验证码1验证
     *
     * @param context      1
     * @param validateCode 1
     * @param listener     1
     * @return 1
     */
    public static String changePhoneVerify(final Context context, String userId, String validateCode, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.changePhoneVerify(userId, validateCode, token);
        final String url = ApplicationConsts.URL_CHANGEPHONEVERIFY;
        String tid = registerId(Constants.TASK_TYPE_CHANGEPHONEVERIFY, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHANGEPHONEVERIFY, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultFindPWDbyPhoneBean b = json.fromJson(data, ResultFindPWDbyPhoneBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 修改密码
     *
     * @param context  1
     * @param listener 1
     * @return 1
     */
    public static String changePassword(final Context context, String mobile, String oldPassword, String password, String rePassword, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.changePassword(mobile, oldPassword, password, rePassword, token);
        final String url = ApplicationConsts.URL_CHANGEPWD;
        String tid = registerId(Constants.TASK_TYPE_CHANGEPWD, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHANGEPWD, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultFindPWDbyPhoneBean b = json.fromJson(data, ResultFindPWDbyPhoneBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 获取合同列表
     *
     * @param context  1
     * @param listener 1
     * @return 1
     */
    public static String getContractList(final Context context, int page, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String data = HtmlLoadUtil.getContractList(page + "", token);
        final String url = ApplicationConsts.URL_CONTRACT_LIST;
        String tid = registerId(Constants.TASK_TYPE_CONTRACT_LIST, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CONTRACT_LIST, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultContractListBean b = json.fromJson(data, ResultContractListBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 获取投保单列表
     *
     * @param context  1
     * @param listener 1
     * @return 1
     */
    public static String getToubaodanList(final Context context, int page, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getContractList(page + "", token);
        final String url = ApplicationConsts.URL_TOUBAODAN_LIST;
        String tid = registerId(Constants.TASK_TYPE_TOUBAODAN_LIST, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_TOUBAODAN_LIST, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultToubaodanListBean b = json.fromJson(data, ResultToubaodanListBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 获取报单列表
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getDeclarationList(final Context context, int page, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getDeclarationList(page + "", token);
        final String url = ApplicationConsts.URL_DECLARATION_LIST;
        String tid = registerId(Constants.TASK_TYPE_DECLARATION_LIST, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_DECLARATION_LIST, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultDeclarationListBean b = json.fromJson(data, ResultDeclarationListBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 获取合同详情
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getContractDetails(final Context context, String contractId, String status, String type, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getContractDetails(contractId, status, type, token);
        final String url = ApplicationConsts.URL_CONTRACT_DETAILS;
        String tid = registerId(Constants.TASK_TYPE_CONTRACT_DETAIL, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CONTRACT_DETAIL, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultContractTrackListBean b = json.fromJson(data, ResultContractTrackListBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 获取保单详情
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getToubaodanDetails(final Context context, String policyOrderId, String status, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getToubaodanDetails(policyOrderId, status, token);
        final String url = ApplicationConsts.URL_TOUBAODAN_DETAILS;
        String tid = registerId(Constants.TASK_TYPE_TOUBAODAN_DETAIL, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_TOUBAODAN_DETAIL, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultToubaodanTrackListBean b = json.fromJson(data, ResultToubaodanTrackListBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 获取报单详情
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getDeclarationDetails(final Context context, String id, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getDeclarationDetails(id, token);
        final String url = ApplicationConsts.URL_DECLARATION_DETAILS;
        String tid = registerId(Constants.TASK_TYPE_DECLARATION_DETAIL, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_DECLARATION_DETAIL, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultDeclarationDetailsListBean b = json.fromJson(data, ResultDeclarationDetailsListBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 修改报单
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getEditDeclaration(final Context context, String id, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String data = HtmlLoadUtil.getDeclarationDetails(id, token);
        final String url = ApplicationConsts.URL_EDIT_DECLARATION_DETAILS;
        String tid = registerId(Constants.TASK_TYPE_DECLARATION_DETAIL, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_DECLARATION_DETAIL, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultEditDeclarationListBean b = json.fromJson(data, ResultEditDeclarationListBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 保存修改报单
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getSaveEditDeclaration(final Context context, String id, String appoitmentId, String productId, String contractId, String productCategory, String productName, String customerName, String customerPhone, String amount, String paymoneyTime, String sfzzmFileName, String sfzfmFileName, String cardFileName, String dkptFileName, String qzyFileName, String remark, String userName, String userPhone, String deputyInsuranceName, String ageCoverage, String payLimitTime, String rebateProportion, String deputyInsuranceAmount, String insuranceRebateProportion, String customerIDcard, String deputyInsuranceId, String costAmount, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getSaveEditDeclaration(id, appoitmentId, productId, contractId, productCategory, productName, customerName, customerPhone, amount, paymoneyTime, sfzzmFileName, sfzfmFileName, cardFileName, dkptFileName, qzyFileName, remark, userName, userPhone, deputyInsuranceName, ageCoverage, payLimitTime, rebateProportion, deputyInsuranceAmount, insuranceRebateProportion, customerIDcard, deputyInsuranceId, costAmount, token);
        final String url = ApplicationConsts.URL_SAVE_EDIT_DECLARATION_DETAILS;
        String tid = registerId(Constants.TASK_TYPE_DECLARATION_DETAIL, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_DECLARATION_DETAIL, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;

                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultApplyDeclarationBean b = json.fromJson(data, ResultApplyDeclarationBean.class);
                        return b;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 获取保险产品的返佣比例
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getSearchCommissionratio(final Context context, String productId, String deputyInsuranceName, String ageCoverage, String payLimitTime, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String data = HtmlLoadUtil.getSearchCommissionratio(productId, deputyInsuranceName, ageCoverage, payLimitTime, token);
        final String url = ApplicationConsts.URL_SEARCHCOMMISSIONRATIO;
        String tid = registerId(Constants.TASK_TYPE_SEARCHCOMMISSIONRATIO, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SEARCHCOMMISSIONRATIO, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultDeclarationSearchCommissionratioListBean b = json.fromJson(data, ResultDeclarationSearchCommissionratioListBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 选择产品
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getSelectProduct(final Context context, String productName, String category, String selectType, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getSelectProduct(productName, category, selectType, token);
        final String url = ApplicationConsts.URL_SELECT_PRODUCT;
        String tid = registerId(Constants.TASK_TYPE_SELECT_PRODUCT, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SELECT_PRODUCT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultSelectProductListBean b = json.fromJson(data, ResultSelectProductListBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 选择产品返回
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getSelectProductReturn(final Context context, String id, String category, String appoId, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getSelectProductReturn(id, category, appoId, token);
        final String url = ApplicationConsts.URL_SELECT_PRODUCT_RETURN;
        String tid = registerId(Constants.TASK_TYPE_SELECT_PRODUCT_RETURN, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SELECT_PRODUCT_RETURN, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultSelectProductReturnListBean b = json.fromJson(data, ResultSelectProductReturnListBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 签收合同
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getSignContract(final Context context, String contractId, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getSignContract(contractId, token);
        final String url = ApplicationConsts.URL_SIGN_CONTRACT;
        String tid = registerId(Constants.TASK_TYPE_SIGN_CONTRACT, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SIGN_CONTRACT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultSignContractBean b = json.fromJson(data, ResultSignContractBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 签收保单
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getSignToubaodan(final Context context, String policyOrderId, String status, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String data = HtmlLoadUtil.getSignToubaodan(policyOrderId, status, token);
        final String url = ApplicationConsts.URL_SIGN_TOUBAODAN;
        String tid = registerId(Constants.TASK_TYPE_SIGN_TOUBAODAN, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SIGN_TOUBAODAN, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultSignContractBean b = json.fromJson(data, ResultSignContractBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 寄回合同
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getPostContract(final Context context, String contractId, String expressNameBack, String expressCodeBack, String expressUrlBack, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getPostContract(contractId, expressNameBack, expressCodeBack, expressUrlBack, token);
        final String url = ApplicationConsts.URL_POST_CONTRACT;
        String tid = registerId(Constants.TASK_TYPE_POST_CONTRACT, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_POST_CONTRACT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultPostContractBean b = json.fromJson(data, ResultPostContractBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 寄回保单
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getPostToubaodan(final Context context, String policyOrderId, String expressFiles, String expressName, String expressCode, String expressIP, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getPostToubaodan(policyOrderId, expressFiles, expressName, expressCode, expressIP, token);
        final String url = ApplicationConsts.URL_POST_TOUBAODAN;
        String tid = registerId(Constants.TASK_TYPE_POST_TOUBAODAN, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_POST_TOUBAODAN, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultPostContractBean b = json.fromJson(data, ResultPostContractBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 申请合同
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getApplyContract(final Context context, String productId, String productName, String productCategory, String customerName, String userName, String userPhone, String accepter, String edt_acceptPhone, String edt_acceptAddress, String policyOrderId, String deputyInsuranceName, String payLimitTime, String ageCoverage, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getApplyContract(productId, productName, productCategory, customerName, userName, userPhone, accepter, edt_acceptPhone, edt_acceptAddress, policyOrderId, deputyInsuranceName, payLimitTime, ageCoverage, token);
        final String url = ApplicationConsts.URL_APPLY_CONTRACT;
        String tid = registerId(Constants.TASK_TYPE_APPLY_CONTRACT, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_APPLY_CONTRACT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultApplyContractBean b = json.fromJson(data, ResultApplyContractBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 申请投保单
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getApplyToubaodan(final Context context, String appId, String productId, String productName, String customerName, String userName, String userPhone, String accepter, String acceptPhone, String acceptAddress, String deputyInsuranceName, String payLimitTime, String ageCoverage, String age, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getApplyToubaodan(appId, productId, productName, customerName, userName, userPhone, accepter, acceptPhone, acceptAddress, deputyInsuranceName, payLimitTime, ageCoverage, age, token);
        final String url = ApplicationConsts.URL_APPLY_TOUBAODAN;
        String tid = registerId(Constants.TASK_TYPE_APPLY_TOUBAODAN, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_APPLY_TOUBAODAN, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultApplyToubaodanBean b = json.fromJson(data, ResultApplyToubaodanBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 申请报单
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getApplyDeclaration(final Context context, String appoitmentId, String productId, String contractId, String productCategory, String productName, String customerName, String customerPhone, String amount, String paymoneyTime, String sfzzmFileName, String sfzfmFileName, String cardFileName, String dkptFileName, String qzyFileName, String remark, String userName, String userPhone, String deputyInsuranceName, String ageCoverage, String payLimitTime, String rebateProportion, String deputyInsuranceAmount, String insuranceRebateProportion, String customerIDcard, String deputyInsuranceId, String costAmount, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getApplyDeclaration(appoitmentId, productId, contractId, productCategory, productName, customerName, customerPhone, amount, paymoneyTime, sfzzmFileName, sfzfmFileName, cardFileName, dkptFileName, qzyFileName, remark, userName, userPhone, deputyInsuranceName, ageCoverage, payLimitTime, rebateProportion, deputyInsuranceAmount, insuranceRebateProportion, customerIDcard, deputyInsuranceId, costAmount, token);
        final String url = ApplicationConsts.URL_APPLY_DECLARATION;
        String tid = registerId(Constants.TASK_TYPE_APPLY_DECLARATION, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_APPLY_DECLARATION, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultApplyDeclarationBean b = json.fromJson(data, ResultApplyDeclarationBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 获取预约列表
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getOrderList(final Context context, String category, String page, String status, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getOrderList(category, page, status, token);
        final String url = ApplicationConsts.URL_ORDER_LIST;
        String tid = registerId(Constants.TASK_TYPE_ORDER_LIST, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_ORDER_LIST, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultOrderListBean b = json.fromJson(data, ResultOrderListBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 通过手机号找回密码
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String findPWDbyPhone(final Context context, String mobile, String password, String rePassword, String validateCode, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String data = HtmlLoadUtil.findPWDbyPhone(mobile, password, rePassword, validateCode, token);
        final String url = ApplicationConsts.URL_FINDPWDBYPHONE;
        String tid = registerId(Constants.TASK_TYPE_FINDPWDBYPHONE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_FINDPWDBYPHONE, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                // HtmlRequest.synCookies(context, url);
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultFindPWDbyPhoneBean b = json.fromJson(data, ResultFindPWDbyPhoneBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 注册一
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String signUpOne(final Context context, String mobile, String validateCode, OnRequestListener listener) {
        final String data = HtmlLoadUtil.signUpOne(mobile, validateCode);
        final String url = ApplicationConsts.URL_SIGNUP_ONE;
        String tid = registerId(Constants.TASK_TYPE_SIGNUP, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SIGNUP, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultSignupBean b = json.fromJson(data, ResultSignupBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 注册二
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String signUpTwo(final Context context, String mobile, String password, String nickName, String channelManagerPhone, OnRequestListener listener) {
        final String data = HtmlLoadUtil.signUpTwo(mobile, password, nickName, channelManagerPhone);
        final String url = ApplicationConsts.URL_SIGNUP_TWO;
        String tid = registerId(Constants.TASK_TYPE_SIGNUP, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SIGNUP, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultSignupBean b = json.fromJson(data, ResultSignupBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 检查版本
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String checkVersion(final Context context, String type, OnRequestListener listener) {
        final String data = HtmlLoadUtil.checkVersion(type);
        final String url = ApplicationConsts.URL_CHECKVERSION;
        String tid = registerId(Constants.TASK_TYPE_CHECKVERSION, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHECKVERSION, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultCheckVersionBean b = json.fromJson(data, ResultCheckVersionBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 得到轮播图
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getAdvertise(final Context context, OnRequestListener listener) {
        final String url = ApplicationConsts.URL_GETADVERTISE;
        String tid = registerId(Constants.TASK_TYPE_GETADVERTISE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_GETADVERTISE, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                client.get(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        ResultAdvertiseBean b = json.fromJson(result, ResultAdvertiseBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 热销产品
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getHotProduct(final Context context, OnRequestListener listener) {
        // final String data = HtmlLoadUtil.signUpTwo(mobile, password,
        // nickName,
        // channelManagerPhone);
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String data = HtmlLoadUtil.getMyAccount(token);
        final String url = ApplicationConsts.URL_HOTPRODUCT;
        String tid = registerId(Constants.TASK_TYPE_HOTPRODUCT, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_HOTPRODUCT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
//						client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultHotProductBean b = json.fromJson(data, ResultHotProductBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 我的消息数量
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getMyMessageCount(final Context context, String Mobile, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getMyMessageCountData(Mobile, token);
        final String url = ApplicationConsts.URL_MYMESSAGE;
        String tid = registerId(Constants.TASK_TYPE_MYMESSAGECOUNT, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_MYMESSAGECOUNT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultMyMessageCountBean b = json.fromJson(data, ResultMyMessageCountBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 推荐产品
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getRecommendProduct(final Context context, OnRequestListener listener) {
        // final String data = HtmlLoadUtil.signUpTwo(mobile, password,
        // nickName,
        // channelManagerPhone);

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String data = HtmlLoadUtil.getMyAccount(token);
        final String url = ApplicationConsts.URL_RECOMMENDPRODUCT;
        String tid = registerId(Constants.TASK_TYPE_RECOMMENDPRODUCT, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_RECOMMENDPRODUCT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
//						client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultRecommendProductBean b = json.fromJson(data, ResultRecommendProductBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }


    /**
     * 信托产品排序
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String xinItemResult(final Context context, String category, String defaultType, String filterType, int pageNo, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.XintuoSortItem(category, defaultType, filterType, pageNo + "", token);
        final String url = ApplicationConsts.URL_TRUSTPAIXU;
        // final String url =
        // "http://192.168.1.92:9999/cf360-customerApp/android/productTrust/list";
        String tid = registerId(Constants.TASK_TYPE_CHANGEPHONEVERIFY, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHANGEPHONEVERIFY, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
//						Header[] mHeaders = client.getHeaders();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultXinTuoItemBean b = json.fromJson(data, ResultXinTuoItemBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 我的银行卡
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getMyBankList(final Context context, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getMyAccount(token);
        final String url = ApplicationConsts.URL_MYBANKLIST;
        String tid = registerId(Constants.TASK_TYPE_MYBANKLIST, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_MYBANKLIST, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultMyBankListBean b = json.fromJson(data, ResultMyBankListBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 获取银行信息
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getBankListMessage(final Context context, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getMyAccount(token);
        final String url = ApplicationConsts.URL_BANKLISTMESSAGE;
        String tid = registerId(Constants.TASK_TYPE_BANKLISTMESSAGE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_BANKLISTMESSAGE, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultBankListMessageBean b = json.fromJson(data, ResultBankListMessageBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 绑定银行卡
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getBindBankCard(final Context context, String remoteValidateCode, String openBankId, String usrCustId, String openAcctId, String userName, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getBindBankCard(remoteValidateCode, openBankId, usrCustId, openAcctId, userName, token);
        final String url = ApplicationConsts.URL_BINDBANKCARD;
        String tid = registerId(Constants.TASK_TYPE_BINDBANKCARD, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_BINDBANKCARD, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultSentSMSBean b = json.fromJson(data, ResultSentSMSBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 理财师认证
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getFinancialAudit(final Context context, String mobile, String realName, String regionaProvince, String regionaCity, String companyName, String email, String businessCardName, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getFinancialAudit(mobile, realName, regionaProvince, regionaCity, companyName, email, businessCardName, token);
        final String url = ApplicationConsts.URL_FINANCIALAUDIT;
        String tid = registerId(Constants.TASK_TYPE_FINANCIALAUDIT, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_FINANCIALAUDIT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultFinancialAuditBean b = json.fromJson(data, ResultFinancialAuditBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 理财师认证状态判断显示
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getFinancialToUserAudit(final Context context, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String data = HtmlLoadUtil.getMyAccount(token);
        final String url = ApplicationConsts.URL_FINANCIALTOUSERAUDIT;
        String tid = registerId(Constants.TASK_TYPE_FINANCIALTOUSERAUDIT, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_FINANCIALTOUSERAUDIT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultFinancialToUserAuditBean b = json.fromJson(data, ResultFinancialToUserAuditBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 保险代理认证
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getInsuranceAudit(final Context context, String insuranceAgentCode, String idNo, String insuranceAgentType, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String data = HtmlLoadUtil.getInsuranceAudit(insuranceAgentCode, idNo, insuranceAgentType, token);
        final String url = ApplicationConsts.URL_INSURANCEAUDIT;
        String tid = registerId(Constants.TASK_TYPE_INSURANCEAUDIT, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_INSURANCEAUDIT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultFinancialAuditBean b = json.fromJson(data, ResultFinancialAuditBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 保险代理信息验证
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getIsInsuranceAudit(final Context context, String userId, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getIsInsuranceAudit(userId, token);
        final String url = ApplicationConsts.URL_ISINSURANCEAUDIT;
        String tid = registerId(Constants.TASK_TYPE_ISINSURANCEAUDIT, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_ISINSURANCEAUDIT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultIsInsuranceBean b = json.fromJson(data, ResultIsInsuranceBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 机构认证
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getInsuranceAgentAudit(final Context context, OnRequestListener listener) {
        // final String data = HtmlLoadUtil.getInsuranceAgentAudit(orgId);
        final String url = ApplicationConsts.URL_INSURANCEAGENTAUDIT;
        String tid = registerId(Constants.TASK_TYPE_INSURANCEAGENTAUDIT, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_INSURANCEAGENTAUDIT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                // HttpEntity entity = null;
                // try {
                // entity = new StringEntity(data);
                // } catch (UnsupportedEncodingException e1) {
                // e1.printStackTrace();
                // }

                // client.post(url, entity);
                client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultFinancialAgentAuditBean b = json.fromJson(data, ResultFinancialAgentAuditBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 选择提款账户
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getChoseBankList(final Context context, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getMyAccount(token);
        final String url = ApplicationConsts.URL_CHOSEBANKLIST;


        String tid = registerId(Constants.TASK_TYPE_CHOSEBANKLIST, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHOSEBANKLIST, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultChoseBankListBean b = json.fromJson(data, ResultChoseBankListBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 产品搜索
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getSearchProduct(final Context context, String productName, String pageNo, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getSearchProduct(productName, pageNo);
        final String url = ApplicationConsts.URL_SEARCHPRODUCT;
        String tid = registerId(Constants.TASK_TYPE_SEARCHPRODUCT, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SEARCHPRODUCT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultSearchProductBean b = json.fromJson(data, ResultSearchProductBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 收支明细
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getInfoData(final Context context, String CATEGORY, String pageNo, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String data = HtmlLoadUtil.getInfoData(CATEGORY, pageNo, token);
        final String url = ApplicationConsts.URL_INFODATA;
        String tid = registerId(Constants.TASK_TYPE_INFODATA, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_INFODATA, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultInfoDataBean b = json.fromJson(data, ResultInfoDataBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 收支明细详情
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getInfoDetailData(final Context context, String id, String ptype, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getInfoDetailData(id, ptype, token);
        final String url = ApplicationConsts.URL_INFODETAILDATA;
        String tid = registerId(Constants.TASK_TYPE_INFODETAILDATA, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_INFODETAILDATA, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultInfoDetailDataBean b = json.fromJson(data, ResultInfoDetailDataBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 信托筛选
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String xinSaiItemResult(final Context context, String category, String defaultType, String filterType, int pageNo, String annualRate, String commission, String investmentField, String issuer, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.XintuoSaiXuanItem(annualRate, category, commission, defaultType, filterType, investmentField, issuer, pageNo + "", token);
        final String url = ApplicationConsts.URL_TRUSTPAIXU;
        // final String url =
        // "http://192.168.1.92:9999/cf360-customerApp/android/productTrust/list";
        String tid = registerId(Constants.TASK_TYPE_CHANGEPHONEVERIFY, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHANGEPHONEVERIFY, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultXinTuoItemBean b = json.fromJson(data, ResultXinTuoItemBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 信托详情
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String xinItemDetailsResult(final Context context, String id, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.xintuoDetailsItem(id, token);
        final String url = ApplicationConsts.URL_TRUSTXANGQING;
        // final String url =
        // "http://192.168.1.92:9999/cf360-customerApp/productTrust/queryProductTrustById";
        String tid = registerId(Constants.TASK_TYPE_CHANGEPHONEVERIFY, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHANGEPHONEVERIFY, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultXinDetailsItemBean b = json.fromJson(data, ResultXinDetailsItemBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 信托发送邮件
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String PupXinDetails(final Context context, int type, String email, String id, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.xintuoSendEmail(email, id, token);
        switch (type) {
            case 0:
                url_two = ApplicationConsts.URL_XINPOSTMAIL;
                // url =
                // "http://192.168.1.176:9992/cf360-customerApp/android/xtzg/emailSendForApp";
                break;
            case 1:
                // url =
                // "http://192.168.1.176:9992/cf360-customerApp/android/xtzg/emailSendForApp";
                url_two = ApplicationConsts.URL_ZIPOSTMAIL;
                break;
            case 2:
                // url =
                // "http://192.168.1.176:9992/cf360-customerApp/android/ygsm/emailSendForApp";
                url_two = ApplicationConsts.URL_SUNPOSTMAIL;
                break;
            case 3:
                // url =
                // "http://192.168.1.176:9992/cf360-customerApp/android/smgq/emailSendForApp";
                url_two = ApplicationConsts.URL_SMGQPOSTMAIL;
                break;
            case 4:
                // url =
                // "http://192.168.1.176:9992/cf360-customerApp/android/bx/emailSendForApp";
                url_two = ApplicationConsts.URL_INSURANCEMAIL;
                break;
            default:
                break;
        }

        String tid = registerId(Constants.TASK_TYPE_CHANGEPHONEVERIFY, url_two);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHANGEPHONEVERIFY, context, listener, url_two, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url_two, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultXinPopMeailBean b = json.fromJson(data, ResultXinPopMeailBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 阳光私募排序
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String SunShineResult(final Context context, String category, String filterType, String pageNo, String sortWay, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.SunShinePaixuItem(category, filterType, pageNo, sortWay, token);
        final String url = ApplicationConsts.URL_SUNSHINE;
        // final String url =
        // "http://192.168.1.92:9999/cf360-customerApp/android/productPrivate/list";
        String tid = registerId(Constants.TASK_TYPE_CHANGEPHONEVERIFY, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHANGEPHONEVERIFY, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultSunShineItemBean b = json.fromJson(data, ResultSunShineItemBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 阳光私募筛选
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String SunShineSaiXuanResult(final Context context, String category, String companyList, String filterType, String fundList, String investmentTypeList, String sortWay, String statusList, String pageNo, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }


        final String data = HtmlLoadUtil.SunShineSaiXuanItem(category, companyList, filterType, fundList, investmentTypeList, sortWay, statusList, pageNo, token);
        final String url = ApplicationConsts.URL_SUNSHINE;
        // final String url =
        // "http://192.168.1.163:9999/cf360-customerApp/android/productPrivate/list";
        String tid = registerId(Constants.TASK_TYPE_CHANGEPHONEVERIFY, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHANGEPHONEVERIFY, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultSunShineItemBean b = json.fromJson(data, ResultSunShineItemBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 阳光私募详情
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String SunShineItemResult(final Context context, String id, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.SunShineDetailsItem(id, token);
        final String url = ApplicationConsts.URL_SUNSHINEXIANGQING;
        // final String url =
        // "http://192.168.1.92:9999/cf360-customerApp/android/productPrivate/detail";
        String tid = registerId(Constants.TASK_TYPE_CHANGEPHONEVERIFY, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHANGEPHONEVERIFY, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultSunShineItemDetailBean b = json.fromJson(data, ResultSunShineItemDetailBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 私募股权
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String EquityPaiXuResult(final Context context, String category, String filterType, String pageNo, String sortWay, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.EquityPaiXuResult(category, filterType, pageNo, sortWay, token);
        final String url = ApplicationConsts.URL_SUNSHINE;
        // final String url =
        // "http://192.168.1.92:9999/cf360-customerApp/android/productPrivate/list";
        String tid = registerId(Constants.TASK_TYPE_CHANGEPHONEVERIFY, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHANGEPHONEVERIFY, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultEquityItemBean b = json.fromJson(data, ResultEquityItemBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 私募股权筛选
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String EquiteSaiXuanResult(final Context context, String category, String companyList, String filterType, String fundList, String investmentTypeSMList, String sortWay, String statusList, String pageNo, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.EquiteSaiXuanItem(category, filterType, fundList, investmentTypeSMList, sortWay, statusList, pageNo, token);
        final String url = ApplicationConsts.URL_SUNSHINE;
        // final String url =
        // "http://192.168.1.163:9999/cf360-customerApp/android/productPrivate/list";
        String tid = registerId(Constants.TASK_TYPE_CHANGEPHONEVERIFY, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHANGEPHONEVERIFY, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultEquityItemBean b = json.fromJson(data, ResultEquityItemBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 私募股权详情
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String EquityItemResult(final Context context, String id, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.EquityDetailsItem(id, token);
        final String url = ApplicationConsts.URL_SUNSHINEXIANGQING;
        // final String url =
        // "http://192.168.1.92:9999/cf360-customerApp/android/productPrivate/detail";
        String tid = registerId(Constants.TASK_TYPE_CHANGEPHONEVERIFY, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHANGEPHONEVERIFY, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultEquityDetailItemBean b = json.fromJson(data, ResultEquityDetailItemBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 保险排序
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String InsuranceResult(final Context context, String filterType, String pageNo, String sortType, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.mInsuranceResult(filterType, pageNo, sortType, token);
        final String url = ApplicationConsts.URL_INSURANCE;
        // final String url =
        // "http://192.168.1.92:9999/cf360-customerApp/insuranceProduct/queryInsuranceProductList";
        String tid = registerId(Constants.TASK_TYPE_CHANGEPHONEVERIFY, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHANGEPHONEVERIFY, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultInsuranceItemBean b = json.fromJson(data, ResultInsuranceItemBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 保险筛选
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String InsuranceShaiXuanResult(final Context context, String category, String companyShortName, String filterType, String pageNo, String sortType, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.SInsuranceResult(category, companyShortName, filterType, pageNo, sortType, token);
        final String url = ApplicationConsts.URL_INSURANCE;
        // final String url =
        // "http://192.168.1.92:9999/cf360-customerApp/insuranceProduct/queryInsuranceProductList";
        String tid = registerId(Constants.TASK_TYPE_CHANGEPHONEVERIFY, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHANGEPHONEVERIFY, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultInsuranceItemBean b = json.fromJson(data, ResultInsuranceItemBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 保险详情
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String InSuranceDetailsResult(final Context context, String id, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.InsuranceDetailsResult(id, token);
        final String url = ApplicationConsts.URL_PRODUCTINSURANCE;
        String tid = registerId(Constants.TASK_TYPE_CHANGEPHONEVERIFY, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHANGEPHONEVERIFY, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultInsuranceDetialItemBean b = json.fromJson(data, ResultInsuranceDetialItemBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 预约
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String mPayAttentionTo(final Context context, String category, String productId, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.mPayAttentionTo(category, productId, token);
        final String url = ApplicationConsts.URL_ATTENTION;
        String tid = registerId(Constants.TASK_TYPE_CHANGEPHONEVERIFY, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CHANGEPHONEVERIFY, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        PayAttentionContentBean b = json.fromJson(data, PayAttentionContentBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 解绑银行卡
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getUnBindBankCard(final Context context, String remoteValidateCode, String bankId, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getUnBindBankCard(remoteValidateCode, bankId, token);
        final String url = ApplicationConsts.URL_UNBINDBANKCARD;
        String tid = registerId(Constants.TASK_TYPE_UNBINDBANKCARD, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_UNBINDBANKCARD, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultUnBankMessageBean b = json.fromJson(data, ResultUnBankMessageBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 我的账户
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getMyAccount(final Context context, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getMyAccount(token);
        final String url = ApplicationConsts.URL_MYACCOUNT;
        String tid = registerId(Constants.TASK_TYPE_MYACCOUNT, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_MYACCOUNT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                HtmlRequest.synCookies(context, url);
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultMyAccountBean b = json.fromJson(data, ResultMyAccountBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 是否登陆
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getIsLogin(final Context context, String userId, String token, OnRequestListener listener) {
        final String data = HtmlLoadUtil.getIsLogin(userId, token);
        final String url = ApplicationConsts.URL_IS_LOGIN;
        String tid = registerId(Constants.TASK_TYPE_IS_LOGIN, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_MYACCOUNT, context, listener, url, 0)) {
            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data, "UTF-8");
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultIsLoginBean b = json.fromJson(data, ResultIsLoginBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }


    /**
     * 确认提现
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getWithdrawVerify(final Context context, String bankCardId, String validCode, String transAmount, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getWithdrawVerify(bankCardId, validCode, transAmount, token);
        final String url = ApplicationConsts.URL_WITHDRAWVERIFY;
        String tid = registerId(Constants.TASK_TYPE_WITHDRAWVERIFY, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_WITHDRAWVERIFY, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultWithdrawVerifyBean b = json.fromJson(data, ResultWithdrawVerifyBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 帮助中心列表
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getHelpList(final Context context, int page, OnRequestListener listener) {
        final String data = HtmlLoadUtil.helpListData(page);
        final String url = ApplicationConsts.URL_HELPLISTDATA;
        String tid = registerId(Constants.TASK_TYPE_HELPLISTDATA, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_HELPLISTDATA, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultHelpListBean b = json.fromJson(data, ResultHelpListBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 帮助详情
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getHelpDetail(final Context context, String id, OnRequestListener listener) {
        final String data = HtmlLoadUtil.helpDetailData(id);
        final String url = ApplicationConsts.URL_HELPDETAILDATA;
        String tid = registerId(Constants.TASK_TYPE_HELPDETAILDATA, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_HELPDETAILDATA, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);

                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultHelpDetailBean b = json.fromJson(data, ResultHelpDetailBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 意见反馈
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getAdviceData(final Context context, String content, String mobile, String email, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.adviceData(content, mobile, email, token);
        final String url = ApplicationConsts.URL_ADVICEDATA;
        String tid = registerId(Constants.TASK_TYPE_ADVICEDATA, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_ADVICEDATA, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultAdviceBean b = json.fromJson(data, ResultAdviceBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 消息管理
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getMessageListData(final Context context, String pageNo, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getMessageListData(pageNo, token);
        final String url = ApplicationConsts.URL_MESSAGELISTDATA;
        String tid = registerId(Constants.TASK_TYPE_MESSAGELIST, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_MESSAGELIST, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultMessageListDataBean b = json.fromJson(data, ResultMessageListDataBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 消息详情
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getNoticeMessageDetail(final Context context, String messageId, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }


        final String data = HtmlLoadUtil.getMessageDetailData(messageId, token);
        final String url = ApplicationConsts.URL_MESSAGEDETAILDATA;
        String tid = registerId(Constants.TASK_TYPE_MESSAGEDETAIL, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_MESSAGEDETAIL, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultSentSMSBean b = json.fromJson(data, ResultSentSMSBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 公告列表
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getNoticeListData(final Context context, int page, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getNoticeListData(page, token);
        final String url = ApplicationConsts.URL_NOTICELISTDATA;
        String tid = registerId(Constants.TASK_TYPE_NOTICELIST, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_NOTICELIST, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultNoticeListDataBean b = json.fromJson(data, ResultNoticeListDataBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 取消预约
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getCancelOrder(final Context context, String id, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getOrderDetails(id, token);
        final String url = ApplicationConsts.URL_CANCEL_ORDER;
        String tid = registerId(Constants.TASK_TYPE_CANCEL_ORDER, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CANCEL_ORDER, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultCancelOrderBean b = json.fromJson(data, ResultCancelOrderBean.class);
                        return b;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 获取预约详情
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getOrdertDetails(final Context context, String id, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getOrderDetails(id, token);
        final String url = ApplicationConsts.URL_ORDER_DETAILS;
        String tid = registerId(Constants.TASK_TYPE_ORDER_DETAIL, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_ORDER_DETAIL, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultOrderDetailListBean b = json.fromJson(data, ResultOrderDetailListBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 我的关注
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getFocusProduct(final Context context, String category, int pageNo, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getFocus(category, pageNo + "", token);
        final String url = ApplicationConsts.URL_FOCUS;
        String tid = registerId(Constants.TASK_TYPE_RECOMMENDPRODUCT, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_RECOMMENDPRODUCT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultFocusBean b = json.fromJson(data, ResultFocusBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 获取保险预约返佣比例
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getAppointment(final Context context, String productId, String deputyInsuranceName, String ageCoverage, String payLimitTime, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getSearchCommissionratio(productId, deputyInsuranceName, ageCoverage, payLimitTime, token);
        final String url = ApplicationConsts.URL_SEARCHCOMMISSIONRATIO;
        String tid = registerId(Constants.TASK_TYPE_SEARCHCOMMISSIONRATIO, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SEARCHCOMMISSIONRATIO, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultInsAppointContentBean b = json.fromJson(data, ResultInsAppointContentBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 我要预约
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */

    public static String PostInsuranceYuyue(final Context context, String ageCoverage, String amount, String currency, String customerName, String customerPhone, String deputyInsuranceAmount, String deputyInsuranceName, String deputyRebateProportion, String payLimitTime, String productCategory, String productId, String productName, String rebateProportion, String remark, String totalamount, String userName, String userPhone, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.PostInsurance(ageCoverage, amount, currency, customerName, customerPhone, deputyInsuranceAmount, deputyInsuranceName, deputyRebateProportion, payLimitTime, productCategory, productId, productName, rebateProportion, remark, totalamount, userName, userPhone, token);
        final String url = ApplicationConsts.URL_APPOINTMENT;
        String tid = registerId(Constants.TASK_TYPE_SEARCHCOMMISSIONRATIO, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_SEARCHCOMMISSIONRATIO, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResutlPostContentBean b = json.fromJson(data, ResutlPostContentBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 我的客户
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getUserCustomerList(final Context context, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getMyAccount(token);
        final String url = ApplicationConsts.URL_USERCUSTOMER;
        String tid = registerId(Constants.TASK_TYPE_MYACCOUNT, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_MYACCOUNT, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultMyCustomerBean b = json.fromJson(data, ResultMyCustomerBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 修改备注信息
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getCustomerModifyBeizhu(final Context context, String remark, String customerId, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getCustomerModifyBeizhu(remark, customerId, token);
        final String url = ApplicationConsts.URL_CUSTOMERMODIFYBEIZHU;
        String tid = registerId(Constants.TASK_TYPE_USERCUSTOMERMODIFYBEIZHU, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_USERCUSTOMERMODIFYBEIZHU, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultCustomerModifyBean b = json.fromJson(data, ResultCustomerModifyBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * Ta成交的报单
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getHisBaoDan(final Context context, String idcard, String page, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getHisBaoDan(idcard, page, token);
        final String url = ApplicationConsts.URL_HIS_BAODAN;
        String tid = registerId(Constants.TASK_TYPE_HIS_BAODAN, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_HIS_BAODAN, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultHisBaoDanBean b = json.fromJson(data, ResultHisBaoDanBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * Ta的日程
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getHisSchedule(final Context context, String customerId, String page, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String data = HtmlLoadUtil.getHisSchedule(customerId, page, token);
        final String url_one = ApplicationConsts.URL_HIS_SCHEDULE;
        String tid = registerId(Constants.TASK_TYPE_HIS_BAODAN, url_one);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_HIS_BAODAN, context, listener, url_one, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url_one, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultHisScheduleBean b = json.fromJson(data, ResultHisScheduleBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 新增客户
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getCustomerAdd(final Context context, String operation, String customerType, String code, String name, String sex, String idcard, String mobilePhone, String valueAssessment, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String data = HtmlLoadUtil.getCustomerAdd(operation, customerType, code, name, sex, idcard, mobilePhone, valueAssessment, token);
        final String url = ApplicationConsts.URL_CUSTOMERADD;

        String tid = registerId(Constants.TASK_TYPE_CUSTOMERSADD, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CUSTOMERSADD, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultCustomerInfoBean b = json.fromJson(data, ResultCustomerInfoBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 删除客户
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getCustomerDetele(final Context context, String id, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getCustomerDetele(id, token);
        final String url = ApplicationConsts.URL_CUSTOMERDETELE;

        String tid = registerId(Constants.TASK_TYPE_CUSTOMERSDETELE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CUSTOMERSDETELE, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultCustomerInfoBean b = json.fromJson(data, ResultCustomerInfoBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 获取客户详情信息
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getCustomerInfo(final Context context, String id, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getCustomerDetele(id, token);
        final String url = ApplicationConsts.URL_CUSTOMERINFO;

        String tid = registerId(Constants.TASK_TYPE_CUSTOMERSINFO, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CUSTOMERSINFO, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);

                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultMyCustomerInfoDetailBean b = json.fromJson(data, ResultMyCustomerInfoDetailBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 保存客户基本信息
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getCustomerInfoSave(final Context context, String id, String operation, String customerType, String code, String name, String sex, String age, String idcard, String nation, String company, String position, String profession, String mobilePhone, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getCustomerBaseInfoSave(id, operation, customerType, code, name, sex, age, idcard, nation, company, position, profession, token);
        final String url = ApplicationConsts.URL_CUSTOMERINFOSAVE;

        String tid = registerId(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultCustomerInfoBean b = json.fromJson(data, ResultCustomerInfoBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 保存客户联系方式
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getCustomerPhoneSave(final Context context, String id, String operation, String homePhone, String mobilePhone, String prePhone, String qqNumber, String fax, String email, String birthProvince, String birthCity, String liveProvince, String liveCity, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getCustomerPhoneSave(id, operation, homePhone, mobilePhone, prePhone, qqNumber, fax, email, birthProvince, birthCity, liveProvince, liveCity, token);
        final String url = ApplicationConsts.URL_CUSTOMERINFOSAVE;

        String tid = registerId(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultCustomerInfoBean b = json.fromJson(data, ResultCustomerInfoBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 保存客户意向
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getCustomerIntentionSave(final Context context, String id, String operation, String investType, String investTrade, String investMoney, String investYear, String investIncome, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }


        final String data = HtmlLoadUtil.getCustomerIntentionSave(id, operation, investType, investTrade, investMoney, investYear, investIncome, token);
        final String url = ApplicationConsts.URL_CUSTOMERINFOSAVE;

        String tid = registerId(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultCustomerInfoBean b = json.fromJson(data, ResultCustomerInfoBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 保存客户财务状况
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getCustomerFinancialSave(final Context context, String id, String operation, String car, String house, String valueAssessment, String yearSalary, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String data = HtmlLoadUtil.getCustomerFinancialSave(id, operation, car, house, valueAssessment, yearSalary, token);
        final String url = ApplicationConsts.URL_CUSTOMERINFOSAVE;

        String tid = registerId(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultCustomerInfoBean b = json.fromJson(data, ResultCustomerInfoBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 保存客户兴趣爱好
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getCustomerInstestSave(final Context context, String id, String operation, String interestSport, String interestArt, String interestArder, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getCustomerInstestSave(id, operation, interestSport, interestArt, interestArder, token);
        final String url = ApplicationConsts.URL_CUSTOMERINFOSAVE;

        String tid = registerId(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultCustomerInfoBean b = json.fromJson(data, ResultCustomerInfoBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 选择客户
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getSelectCustomer(final Context context, String customerName, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getSelectCustomer(customerName, token);
        final String url = ApplicationConsts.URL_SELECT_CUSTOMER;

        String tid = registerId(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);

                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                // client.post(url);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultSelectCustomerBean b = json.fromJson(data, ResultSelectCustomerBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 获取客户跟踪中全部客户接口
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getAllCustomer(final Context context, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getMyAccount(token);
        final String url = ApplicationConsts.URL_ALL_CUSTOMER;
        String tid = registerId(Constants.TASK_TYPE_TOUBAODAN_DETAIL, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_TOUBAODAN_DETAIL, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        String data = DESUtil.decrypt(result);
                        Gson json = new Gson();
                        ResultSelectCustomerBean b = json.fromJson(data, ResultSelectCustomerBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 客户跟踪Select
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getGenzongCustomer(final Context context, String customerId, String status, String timeType, String startTime, String page, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getGenzongCustomer(customerId, status, timeType, startTime, page, token);
        final String url = ApplicationConsts.URL_SELECT_GENZONG_CUSTOMER;
        String tid = registerId(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultGenZongCustomerBean b = json.fromJson(data, ResultGenZongCustomerBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 新建日程
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getAddSchedule(final Context context, String customerName, String customerId, String type, String topic, String status, String endTime, String startTime, String scheduleAmind, String amindTime, String scheduleDesc, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getAddSchedule(customerName, customerId, type, topic, status, endTime, startTime, scheduleAmind, amindTime, scheduleDesc, token);
        final String url = ApplicationConsts.URL_ADD_SCHEDULE;
        String tid = registerId(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultAddScheduleBean b = json.fromJson(data, ResultAddScheduleBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 修改日程
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getEditSchedule(final Context context, String id, String customerName, String customerId, String type, String topic, String status, String endTime, String startTime, String scheduleAmind, String amindTime, String scheduleDesc, OnRequestListener listener) {

        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getEditSchedule(id, customerName, customerId, type, topic, status, endTime, startTime, scheduleAmind, amindTime, scheduleDesc, token);
        final String url = ApplicationConsts.URL_EDIT_SCHEDULE;
        String tid = registerId(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultAddScheduleBean b = json.fromJson(data, ResultAddScheduleBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 日程详情
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */
    public static String getScheduleDetails(final Context context, String scheduleId, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getScheduleDetails(scheduleId, token);
        final String url = ApplicationConsts.URL_SCHEDULE_DETAILS;
        String tid = registerId(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultScheduleDetailsBean b = json.fromJson(data, ResultScheduleDetailsBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

    /**
     * 删除日程
     *
     * @param context  上下文
     * @param listener 监听
     * @return 返回数据
     */

    public static String getScheduleDetailsDelete(final Context context, String scheduleId, OnRequestListener listener) {
        String token = null;
        try {
            token = DESUtil.decrypt(PreferenceUtil.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String data = HtmlLoadUtil.getScheduleDetailsDelete(scheduleId, token);
        final String url = ApplicationConsts.URL_SCHEDULE_DETAILS_DETELE;
        String tid = registerId(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, url);
        if (tid == null) {
            return null;
        }
        getTaskManager().addTask(new MouldAsyncTask(tid, buildParams(Constants.TASK_TYPE_CUSTOMERSINFOSAVE, context, listener, url, 0)) {

            @Override
            public IMouldType doTask(BaseParams params) {
                SimpleHttpClient client = new SimpleHttpClient(context, SimpleHttpClient.RESULT_STRING);
                HttpEntity entity = null;
                try {
                    entity = new StringEntity(data);
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                client.post(url, entity);
                String result = (String) client.getResult();
                try {
                    if (isCancelled()) {
                        return null;
                    }
                    if (result != null) {
                        Gson json = new Gson();
                        String data = DESUtil.decrypt(result);
                        ResultCustomerInfoBean b = json.fromJson(data, ResultCustomerInfoBean.class);
                        return b.getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    unRegisterId(getTaskId());
                }
                return null;
            }

            @Override
            public void onPostExecute(IMouldType result, BaseParams params) {
                params.result = result;
                params.sendResult();
            }
        });
        return tid;
    }

}