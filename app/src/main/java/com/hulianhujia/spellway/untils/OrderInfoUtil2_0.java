package com.hulianhujia.spellway.untils;

import android.util.Log;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class OrderInfoUtil2_0 {
	
	/**
	 * 构造授权参数列表
	 * 
	 * @param pid
	 * @param app_id
	 * @param target_id
	 * @return
	 */
	public static Map<String, String> buildAuthInfoMap(String pid, String app_id, String target_id, boolean rsa2) {
		Map<String, String> keyValues = new HashMap<String, String>();

		// 商户签约拿到的app_id，如：2013081700024223
		keyValues.put("app_id", app_id);

		// 商户签约拿到的pid，如：2088102123816631
		keyValues.put("pid", pid);

		// 服务接口名称， 固定值
		keyValues.put("apiname", "com.alipay.account.auth");

		// 商户类型标识， 固定值
		keyValues.put("app_name", "mc");

		// 业务类型， 固定值
		keyValues.put("biz_type", "openservice");

		// 产品码， 固定值
		keyValues.put("product_id", "APP_FAST_LOGIN");

		// 授权范围， 固定值
		keyValues.put("scope", "kuaijie");

		// 商户唯一标识，如：kkkkk091125
		keyValues.put("target_id", target_id);

		// 授权类型， 固定值
		keyValues.put("auth_type", "AUTHACCOUNT");

		// 签名类型
		keyValues.put("sign_type", rsa2 ? "RSA2" : "RSA");

		return keyValues;
	}

	/**
	 * 构造支付订单参数列表
	 * @param
	 * @param
	 * @param
	 * @return
	 */
	public static Map<String,String> buildOrderParamMap(String timestamp,String bizcontent) {
		Map<String, String> keyValues = new TreeMap<>();
		keyValues.put("app_id","2017072607900811");
		keyValues.put("biz_content",bizcontent);
		keyValues.put("charset", "utf-8");
		keyValues.put("format", "JSON");
		keyValues.put("method", "alipay.trade.app.pay");
		keyValues.put("sign_type","RSA");
		keyValues.put("timestamp", timestamp);
		keyValues.put("version", "1.0");
		//keyValues.put("notify_url", "http://userapi.nongyinong.cn/pay/alipay_notify");// 正式
		keyValues.put("notify_url", "http://pintu.hlhjapp.com/home/AliPay/notify");// 测试

		return keyValues;
	}

	/**
	 * 构造业务参数
	 * @param body
	 * @param subject
	 * @param trade
     * @return
     */
	public static String builBizcontent(String body,String subject,String trade,String total_amount){
		Map<String,String> map = new TreeMap<>();
		map.put("body",body);
		map.put("subject",subject);
		map.put("out_trade_no",trade);
		map.put("total_amount",total_amount);
		map.put("seller_id","2233265022@qq.com");
		map.put("product_code","QUICK_MSECURITY_PAY");
		map.put("goods_type","0");
		JSONObject obj = new JSONObject(map);
		return obj.toString();
	}
	/**
	 * 构造支付订单参数信息
	 * 
	 * @param map
	 * 支付订单参数
	 * @return
	 */
	public static String buildOrderParam(Map<String, String> map) {
		List<String> keys = new ArrayList<String>(map.keySet());

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = null ;
			/*if (key.equals("biz_content")){
				Map<String,String> biz = (Map<String, String>) map.get(key);
				JSONObject obj = new JSONObject(biz);
				value = obj.toString();
			}else {*/
				value = (String) map.get(key);
			//}
			sb.append(buildKeyValue(key, value, true));
			sb.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = (String) map.get(tailKey);
		sb.append(buildKeyValue(tailKey, tailValue, true));

		return sb.toString();
	}
	
	/**
	 * 拼接键值对
	 * 
	 * @param key
	 * @param value
	 * @param isEncode
	 * @return
	 */
	private static String buildKeyValue(String key, String value, boolean isEncode) {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		sb.append("=");
		sb.append(value);
		/*if (isEncode) {
			try {
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				sb.append(value);
			}
		} else {
			sb.append(value);
		}*/
		return sb.toString();
	}
	
	/**
	 * 对支付参数信息进行签名
	 * 
	 * @param map
	 *            待签名授权信息
	 * 
	 * @return
	 */
	public static String getSign(Map<String, String> map, String rsaKey, boolean rsa2) {
		List<String> keys = new ArrayList<String>(map.keySet());
		// key排序
		Collections.sort(keys);

		StringBuilder authInfo = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = null ;
			/*if (key.equals("biz_content")){
				Map<String,String> biz = (Map<String, String>) map.get(key);
				JSONObject obj = new JSONObject(biz);
				value = obj.toString();
			}else {*/
				value = (String) map.get(key);
			//}
			authInfo.append(buildKeyValue(key, value, false));
			authInfo.append("&");
		}
		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		authInfo.append(buildKeyValue(tailKey, tailValue, false));

		Log.i("info","authinfo=="+authInfo+" rsakey="+rsaKey);
//		String oriSign = SignUtils.sign(authInfo.toString(), rsaKey, rsa2);
//		Log.i("info","oriSign="+oriSign);
//		String encodedSign = "";
//
//		try {
//			encodedSign = URLEncoder.encode(oriSign, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		return "sign=" + encodedSign;
		return "";
	}
	
	/**
	 * 要求外部订单号必须唯一。
	 * @return
	 */
	private static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

}
