package com.company.tyxo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.EditText;

import com.company.tyxo.utils.log.HLog;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 字符串工具类
 * 
 * @author john
 * 
 */
public class StringUtils {

	/** 获取手机信息 */
	public static String getPhoneInfos(String str){
		String deviceType = android.os.Build.MODEL; // 手机型号 ATH-UL00
		String systemVersion = "Android "+android.os.Build.VERSION.RELEASE;//Android 5.1.1
		String VERSION_SDK = android.os.Build.VERSION.SDK; // 22
		String MANUFACTURER = android.os.Build.MANUFACTURER; // 手机厂商 HUAWEI

		HLog.i("tyxo", "deviceType: "+deviceType+", systemVersion: "+systemVersion+
				", VERSION_SDK: "+VERSION_SDK+", MANUFACTURER: "+MANUFACTURER);

		if (str.equals(deviceType)) {
			return deviceType;
		} else if (str.equals(systemVersion)) {
			return systemVersion;
		}else if (str.equals(VERSION_SDK)) {
			return VERSION_SDK;
		} else if (str.equals(MANUFACTURER)) {
			return MANUFACTURER;
		} else {
			return "deviceType: "+deviceType+", systemVersion: "+systemVersion+
					", VERSION_SDK: "+VERSION_SDK+", MANUFACTURER: "+MANUFACTURER;
		}
	}

	// 获取SdCard缓存路径
	public static String SDCardCachePath = Environment
			.getExternalStorageDirectory() + "/HengZhao/";

	public static boolean isEditTextEmpty(EditText editText) {
		String str = editText.getText().toString();
		return str == null || str.length() == 0;
	}

	public static String getEditTextString(EditText editText) {
		return editText.getText().toString();
	}

	/** 判断字符串是否为空 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/** 判断字符串是否为空 */
	public static boolean isNotEmpty(String str) {
		return !StringUtils.isEmpty(str);
	}

	/** 字符串去除头尾空格 */
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	/**
	 * 返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始，不区分大小。
	 * 
	 * @param subject
	 *            被查找字符串。
	 * @param search
	 *            要查找的子字符串。
	 * @return 指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始。
	 */
	public static int ignoreCaseIndexOf(String subject, String search) {
		return ignoreCaseIndexOf(subject, search, -1);
	}

	/**
	 * 返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始，不区分大小。
	 * 
	 * @param subject
	 *            被查找字符串。
	 * @param search
	 *            要查找的子字符串。
	 * @param fromIndex
	 *            开始查找的索引位置。其值没有限制，如果它为负，则与它为 0 的效果同样：将查找整个字符串。
	 *            如果它大于此字符串的长度，则与它等于此字符串长度的效果相同：返回 -1。
	 * @return 指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始。
	 */
	public static int ignoreCaseIndexOf(String subject, String search,
			int fromIndex) {

		// 当被查找字符串或查找子字符串为空时，抛出空指针异常。
		if (subject == null || search == null) {
			throw new NullPointerException("输入的参数为空");
		}

		fromIndex = fromIndex < 0 ? 0 : fromIndex;

		if (search.equals("")) {
			return fromIndex >= subject.length() ? subject.length() : fromIndex;
		}

		int index1 = fromIndex;
		int index2 = 0;

		char c1;
		char c2;

		loop1: while (true) {

			if (index1 < subject.length()) {
				c1 = subject.charAt(index1);
				c2 = search.charAt(index2);

			} else {
				break loop1;
			}

			while (true) {
				if (isEqual(c1, c2)) {

					if (index1 < subject.length() - 1
							&& index2 < search.length() - 1) {

						c1 = subject.charAt(++index1);
						c2 = search.charAt(++index2);
					} else if (index2 == search.length() - 1) {

						return fromIndex;
					} else {

						break loop1;
					}

				} else {

					index2 = 0;
					break;
				}
			}
			// 重新查找子字符串的位置
			index1 = ++fromIndex;
		}

		return -1;
	}

	/**
	 * 判断两个字符是否相等。
	 * 
	 * @param c1
	 *            字符1
	 * @param c2
	 *            字符2
	 * @return 若是英文字母，不区分大小写，相等true，不等返回false； 若不是则区分，相等返回true，不等返回false。
	 */
	private static boolean isEqual(char c1, char c2) {
		// 字母小写 字母大写
		if (((97 <= c1 && c1 <= 122) || (65 <= c1 && c1 <= 90))
				&& ((97 <= c2 && c2 <= 122) || (65 <= c2 && c2 <= 90))
				&& ((c1 - c2 == 32) || (c2 - c1 == 32))) {

			return true;
		} else if (c1 == c2) {
			return true;
		}

		return false;
	}

	public static SimpleDateFormat formatD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	// 获得年月日时间
	public static String getCurrentDate() {
		Date date = new Date(System.currentTimeMillis());
		// 获取当前时间
		String str = format.format(date);
		return str;
	}

	// 获得含时分秒的时间
	public static String getCurrentDateDetail() {
		Date date = new Date(System.currentTimeMillis());
		// 获取当前时间
		String str = formatD.format(date);
		return str;
	}

	// 获取当前时间往后一天
	public static String getCurrentDateDay1() {
		long day1 = 24*60*60*1000;
		Date date = new Date(System.currentTimeMillis() + day1);
		// 获取当前时间
		String str = format.format(date);
		return str;
	}

	public static String getCurrentDate(long timeMillis) {
		if (timeMillis == 0L)
			return null;
		Date date = new Date(timeMillis);
		// 获取当前时间
		String str = format.format(date);
		return str;
	}

	public static String getCurrentDate(String timeMillis) {
		if (isEmpty(timeMillis))
			return new String("");
		Date date = new Date(Long.parseLong(timeMillis));
		// 获取当前时间
		String str = format.format(date);
		return str;
	}

	public static String getCustomDate(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date(System.currentTimeMillis());
		// 获取当前时间
		String str = sdf.format(date);
		return str;
	}

	// 获取前一个月的时间
	public static String getMonthDate(){
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.MONTH, -1);
		Date lastMonth = ca.getTime();
		String str = format.format(lastMonth);
		return str;
	}

	/**判断日期是否符合规则,1为生产日期,2为失效日期:生产不能晚于当前,失效不能早于当前*/
	public static String dteMatchRule(String date,int nu){
		switch (nu) {
			case 1:
				if (date!=null && date!="" && date.length()==7) {
					date = date+"-01";
				}else {}
				return date;
			case 2:
				if (date!=null && date!="" && date.length()==7) {
					String strs [] = date.split("-");
					int year = Integer.valueOf(strs[0]);
					int mon = Integer.valueOf(strs[1]);
					date = getMonMaxDay(year, mon);
				}else {}
				return date;
		}
		return date;
	}

	/** 获取 传入月 最后一天*/
	public static String getMonMaxDay(int year,int mon){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, mon);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDate = cal.getTime();
		return format.format(lastDate);
	}

	/** 验证手机格式 */
	public static boolean isMobileNO(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或7或8，其他位置的可以为0-9
		 */
		String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex);
	}
	/**
	 * 验证电话格式
	 * userTelephone
	 * "^(010|021|022|023|024|025|026|027|028|029|852)\\d{8}$"
	 *
	 */
	public static boolean isTelNO(String mobiles) {

		String telRegex1 = "^(010|021|022|023|024|025|026|027|028|029|852)\\d{8}$";
		String telRegex2 = "^(0[3-9][0-9]{2})\\d{7,8}$";
		if (TextUtils.isEmpty(mobiles))
			return false;
		else
			return mobiles.matches(telRegex1) ||mobiles.matches(telRegex2);
	}
	/** 验证数字^[0-9]*$ */
	public static boolean isNO(String number) {
		
		String telRegex = "[0-9]*";
		if (TextUtils.isEmpty(number))
			return false;
		else
			return number.matches(telRegex);
	}
	/** 验证小数^[0-9]{1,10}(\\.[0-9]{0,2})?$ */
	public static boolean isDouble(String number) {
		
		String telRegex = "[0-9]{1,10}(\\.[0-9]{0,2})?";
		if (TextUtils.isEmpty(number))
			return false;
		else
			return number.matches(telRegex);
	}

	/**
	 * 获取订单号 <a href=/S_Order/SupplierOrderItemListEdit?orderId=
	 * plat_ent_yd0000000000316>点击查看：plat_ent_yd0000000000316</a>
	 * 
	 * @return
	 */
	public static String getOrderId(String content) {

		String result = "";

		String regEx_id = "<a.*?orderId=(.*?)>"; // 图片链接地址
		Pattern p_image = Pattern.compile(regEx_id, Pattern.CASE_INSENSITIVE);
		Matcher m_image = p_image.matcher(content);
		int i = 0;
		while (m_image.find()) {
			result = m_image.group(1);
			System.out.println("result = " + result + " i = " + i++);

		}
		return result;
	}

	/** 拼接WCF服务地址 */
	public static String combineUrl(String baseUrl, String relativeUrl) {

		if (baseUrl.endsWith("/")) {
			baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
		}

		if (relativeUrl.startsWith("1")) {
			relativeUrl = relativeUrl.substring(1);
		}

		return String.format("%s/%s", baseUrl, relativeUrl);
	}

	/**
	 * 拼接WCF服务地址
	 * 
	 * @param context
	 * @param resId
	 *            表示WCF服务接口的资源id
	 * @return
	 */
	public static String combineUrl(Context context, String baseUrl, int resId) {
		String relativeUrl = context.getResources().getString(resId);
		return combineUrl(baseUrl, relativeUrl);
	}
	public static String combineURl(String baseUrl, String relativeUrl){

		return String.format("%s%s", baseUrl, relativeUrl);
	}
	/** 将短时间格式字符串转换为时间 yyyy-MM-dd */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	public static String stringFilter(String text, String regEx)
			throws PatternSyntaxException {

		// String regEx = "[^a-zA-Z0-9]";

		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(text);
		return m.replaceAll("").trim();
	}
	
	 // 校验Tag Alias 只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

	/** 比较俩个日期大小 */
	@SuppressLint("SimpleDateFormat")
	public static boolean compare_date(String DATEFROM, String DATETO) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = df.parse(DATEFROM);
			Date dt2 = df.parse(DATETO);
			if (dt1.getTime() > dt2.getTime()) {
//				System.out.println("dt1 在dt2前");
				return false;
			} else if (dt1.getTime() < dt2.getTime()) {
//				System.out.println("dt1在dt2后");
				return true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return true;
	}

	/**
	 * 判空字符串
	 * @param str  字符串
	 * @return 如果str不为null，返回str，否则返回 "";
	 */
	public static  String emptyStr(String str){
		return str != null?str:"";
	}


	/** 过滤字符串,只允许字母,数字和汉字 */
	public static String stringFilter(String str) throws PatternSyntaxException {
		// 只允许字母、数字和汉字
		String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static String isNullString(String string) {
		if (string == null || string.equals("null")) {
			string = "";
		}
		return string;
	}

	/** 字符串转换成时间 */
	public static Date stringToDate(String string){
		/*string = string.substring(0, string.indexOf(" "));
		String[] split = string.split("/");
		if (split.length >= 3) {
			return new Date(Integer.parseInt(split[0])-1900, Integer.parseInt(split[1]), Integer.parseInt(split[2]));
		} else {
			return null;
		}*/
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy/M/d HH:mm:ss" );
		try {
			return sdf.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String formatDate(String string) {
		Date date = stringToDate(string);
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(date);
	}

	// // 判断设置的失效日期是否大于当前日期
	public static boolean isDateValueable(String setDate) {
		boolean isValueable = false;
		String currentDate = StringUtils.getCurrentDate();
		String [] strsCur = currentDate.split("-");
		String [] strsSet = setDate.split("-");
		int yearCur = Integer.valueOf(strsCur[0]);
		int yearSet = Integer.valueOf(strsSet[0]);
		int monCur = Integer.valueOf(strsCur[1]);
		int monSet = Integer.valueOf(strsSet[1]);
		int dayCur = Integer.valueOf(strsCur[2]);
		int daySet = Integer.valueOf(strsSet[2]);
		boolean isYear = yearSet > yearCur;
		boolean isMon = (!(yearSet < yearCur))&&(monSet > monCur);
		boolean isDay = (!(yearSet < yearCur))&&(!(monSet < monCur)) && (daySet > dayCur);
//		HLog.v("lytime","当前月: "+monCur+"; 当前日: "+dayCur+"; 设置月:" +monSet+"; 设置日: "+daySet);
		if (isYear||isMon||isDay) {
			isValueable = true;
		}else {
			isValueable = false;
		}
//		HLog.v("lytime","isMon: "+isMon+"; isDay: "+isDay+"; isValueable: "+isValueable);
		return isValueable;
	}

	// 判断日期是否是YY mm dd
	public static String getDateFromate(String indate){
		HLog.v("lytime","传入的时间: "+indate);
		String [] strsSet = indate.split("-");
		int yearSet = Integer.valueOf(strsSet[0]);
		int monSet = Integer.valueOf(strsSet[1]);
		int daySet = Integer.valueOf(strsSet[2]);
		String mon = "";
		String day = "";
		if (monSet > 9) {
			mon = ""+monSet;
		} else {
			mon = "0"+monSet;
//			indate = indate.replace(monSet+"",mon); // 会替代年中数字 如2016-6--->20106-06
//			HLog.v("lytime","monSet: "+monSet+"; mon : "+mon+"; indate: "+indate);
		}
		if (daySet > 9) {
			day = ""+daySet;
		} else {
			day = "0"+daySet;
//			indate = indate.replace(daySet+"",day);
//			HLog.v("lytime","daySet: "+daySet+"; day : "+day+"; indate: "+indate);
		}
		indate = ""+yearSet+"-"+mon+"-"+day;
		HLog.v("lytime","传出的时间: "+indate);
		return indate;
	}
}