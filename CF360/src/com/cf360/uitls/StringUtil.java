package com.cf360.uitls;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cf360.R;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.widget.Button;

public class StringUtil {
    public static String MD5Encode(byte[] bytes) {
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            byte[] digest = md.digest();
            String text;
            for (int i = 0; i < digest.length; i++) {
                text = Integer.toHexString(0xFF & digest[i]);
                if (text.length() < 2) {
                    text = "0" + text;
                }
                hexString.append(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }

    public static String MD5Encode(String text) {
        return MD5Encode(text.getBytes());
    }

    public static String eregi_replace(String strFrom, String strTo, String strTarget) {
        String strPattern = "(?i)" + strFrom;
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strTarget);
        if (m.find()) {
            return strTarget.replaceAll(strFrom, strTo);
        } else {
            return strTarget;
        }
    }

    /**
     * 把字符串的后n位用“*”号代替(11位手机号)
     *
     * @param str 要代替的字符串
     * @param n   代替的位数
     * @return
     */

    public static String replaceSubString(String str) {
        String sub = "";
        try {
            sub = str.substring(0, 3);
            String sub3 = str.substring(7, str.length());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 4; i++) {
                sb = sb.append("*");
            }
            sub = sub + sb.toString() + sub3;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sub;
    }

    /**
     * 把字符串的后n位用“*”号代替(只保留头尾两位)
     *
     * @param str 要代替的字符串
     * @param n   代替的位数
     * @return
     */

    public static String replaceSubStringName(String str) {
        String sub = "";
        try {
            sub = str.substring(0, 1);
            String sub3 = str.substring(str.length() - 1, str.length());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 4; i++) {
                sb = sb.append("*");
            }
            sub = sub + sb.toString() + sub3;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sub;
    }

    /***
     * 截取后 Length位
     *
     * @param str
     * @param length
     * @return
     */
    public static String subString(String str, int length) {
        String sub = "";
        try {
            if (str.length() > length) {
                sub = str.substring(length, str.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sub;
    }

    /**
     * 去除字符串中的特定字符
     *
     * @param str  原字符串
     * @param temp 需要去除的字符
     * @return
     */
    public static String subStringSpecial(String str, String temp) {
        String sub = str.replace(temp, "");
        return sub;
    }

    public static boolean isUserNameRight(String username) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9\u4e00-\u9fa5]+$");
        Matcher m = p.matcher(username);
        if (m.matches()) {
            return true;
        }
        return false;

    }

    public static void sendSms(Context context, String phone, String body) {
        body = trimCRLF(body);
        Uri uri = Uri.parse("smsto:" + phone);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", body);
        context.startActivity(it);
    }

    /**
     * @param format "yyyy-MM-dd HH:mm:ss"
     * @param time
     * @return
     */
    public static String formatDate(String format, long time) {
        if (time != -1) {
            try {
                Date date = new Date(time);
                SimpleDateFormat sfd = new SimpleDateFormat(format/* "yyyy-MM-dd" */);
                return sfd.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 多个连续换行符只留一个
     *
     * @param str
     * @return
     */
    public static String trimCRLF(String str) {
        if (str == null) {
            return null;
        }
        str = str.trim();
        str = str.replace("\r", "");
        char charc = '\0';
        char[] c = str.toCharArray();
        int l = c.length;
        int lrnSize = 0;
        for (int i = 0; i < l; i++) {
            if (c[i] == '\n') {
                lrnSize++;
            } else {
                lrnSize = 0;
            }
            if (lrnSize > 1) {
                c[i] = charc;
            }
        }
        // str = str.replace("\n", "");
        return new String(c);
    }

    /**
     * 半角转全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String truncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;
        strURL = strURL.trim().toLowerCase();
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }
        return strAllParam;
    }

    /**
     * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map<String, String> splitKeyValue(String URL) {
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit = null;
        String strUrlParam = truncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        // 每个键值为一组
        arrSplit = strUrlParam.split("&");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("=");
            // 解析出键值
            if (arrSplitEqual.length > 1) {
                // 正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (arrSplitEqual[0] != "") {
                    // 只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    public static String base64UrlEncode(String host, String url) {
        try {
            url = host + Base64.encodeToString(url.getBytes("utf-8"), Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // url = host + StringUtil.eregi_replace("(\r\n|\r|\n|\n\r)", "", url);
        return url;
    }

    // public static SpannableStringBuilder setStringStyle(){
    //
    // }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        // String telRegex = "[1][34578]\\d{9}";//
        // "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "^((145|147)|(15[^4])|(17[6-8])|((13|18)[0-9]))\\d{8}$";
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * 验证身份证号是否符合规则
     *
     * @param text 身份证号
     * @return
     */
    public static boolean personIdValidation(String text) {
        String regx = "[0-9]{17}xX";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }

    /***
     * 判断字符串中是否有空格
     *
     * @param str
     * @return
     */
    public static boolean hasBlank(String str) {
        if (str.startsWith(" ") || str.endsWith(" ")) {
            return true;
        } else {
            String s[] = str.split(" +");
            if (s.length == 1) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * 判断字符串中是否包含除_外特殊字符
     *
     * @param str
     * @return
     */
    public static boolean hasSpecialWord(String str) {
        boolean hasSymble = !str.matches("^[\\da-zA-Z_]*$");
        return hasSymble;
    }

    /***
     * 修改字符串样式
     *
     * @param context
     * @param str1
     * @param str2
     * @param str3
     * @param color1
     * @param color2
     * @param color3
     * @param size1
     * @param size2
     * @param size3
     * @param index1
     * @param index2
     * @param index3
     * @return
     */
    public static SpannableStringBuilder setTextStyle(Context context, String str1, String str2, String str3, int color1, int color2, int color3, int size1, int size2, int size3, int index1, int index2, int index3) {
        SpannableStringBuilder style = new SpannableStringBuilder(str3);
        // SpannableStringBuilder实现CharSequence接口
        style.setSpan(new AbsoluteSizeSpan(ViewUtils.sp2px(context, size1)), 0, str1.length() - index1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new AbsoluteSizeSpan(ViewUtils.sp2px(context, size2)), str1.length() - index1, str2.length() - index2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new AbsoluteSizeSpan(ViewUtils.sp2px(context, size3)), str2.length() - index2, str3.length() - index3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(context.getResources().getColor(color1)), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(context.getResources().getColor(color2)), str1.length(), str2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(context.getResources().getColor(color3)), str2.length(), str3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return style;
    }

    /**
     * 修改主页按钮方法
     *
     * @param btn
     * @param btn_hk
     * @param btn_tb
     * @param btn_jq
     * @param mResource
     */

    public static void changeButtonStyle(Button btn_hot_product, Button btn_recommend_product, int btn, Resources mResource) {
        if (btn == btn_hot_product.getId()) {
            btn_hot_product.setBackgroundResource(R.drawable.shape_left_orange_background);
            btn_hot_product.setTextColor(mResource.getColor(R.color.txt_white));
            btn_recommend_product.setBackgroundResource(R.drawable.shape_right_orange);
            btn_recommend_product.setTextColor(mResource.getColor(R.color.orange));
        } else if (btn == btn_recommend_product.getId()) {
            btn_hot_product.setBackgroundResource(R.drawable.shape_left_orange);
            btn_hot_product.setTextColor(mResource.getColor(R.color.orange));
            btn_recommend_product.setBackgroundResource(R.drawable.shape_right_orange_background);
            btn_recommend_product.setTextColor(mResource.getColor(R.color.txt_white));
        }

    }

    /**
     * 验证string是否是非负double类型
     *
     * @param str
     * @return
     */

    public static boolean isDouble(String str) {

        return str.matches("\\-?\\d+(\\.\\d+)?");

    }

    /**
     * 验证string是否保存小数点两位数
     *
     * @param str
     * @return
     */
    public static boolean isDoubleForTwoNumber(String str) {

        return str.matches("^[0-9]+(.[0-9]{1,2})?$");

    }

    /**
     * 验证string是否是正确的数字格式
     *
     * @param value
     * @return
     */
    public static boolean checkNumber(String value) {
        String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
        return value.matches(regex);
    }

    /**
     * 验证是否属于与email格式
     *
     * @param strEmail
     * @return
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }


}
