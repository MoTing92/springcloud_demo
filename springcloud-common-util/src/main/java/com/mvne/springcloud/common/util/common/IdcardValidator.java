
package com.mvne.springcloud.common.util.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdcardValidator {

	private Logger logger = LoggerFactory.getLogger(getClass());

	// 每位加权因子
	private static final int[] POWER = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
	private static final String[] LAST_CODE = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };

	/**
	 * 验证所有的身份证的合法性
	 * 
	 * @param idcard
	 * @return
	 */
	public boolean isValidatedIdcard(String idcard) {

		if (null == idcard || idcard.length() != 18 ) {
			return false;
		}

		String strCard = idcard;
//		if (strCard.length() == 15) {
//			strCard = this.convertIdcardFrom15bit(strCard);
//		}
		return this.isValidated18Idcard(strCard);
	}

	/**
	 * 判断18位身份证的合法性
	 * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
	 * 排列顺序从左至右依次为：六位数字地址码（前1、2位数字表示：所在省份的代码； 第3、4位数字表示：所在城市的代码；
	 * 第5、6位数字表示：所在区县的代码；），八位数字出生日期码，三位数字顺序码和一位数字校验码。
	 * 
	 * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配
	 * 给女性，第15、16位数字表示：所在地的派出所的代码，第17位数字表示性别：奇数表示男性，偶数表示女性
	 * 
	 * 校检码，也有的说是个人信息码，一般是随计算机的随机产生，用来检验身份证的正确性。校检码可以是0~9的数字，有时也用x表示。计算方法为：
	 * 将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
	 * 将这17位数字和系数相乘的结果相加。用加出来和除以11，余数只可能有0 1 2 3 4 5 6 7 8 9
	 * 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3 2。
	 **/
	private boolean isValidated18Idcard(String idcard) {

		String idcard17 = idcard.substring(0, 17);
		String idcard18Code = idcard.substring(17, 18);

		if (!isDigital(idcard17)) {
			return false;
		}

		String checkCode = getCheckCode(idcard17);

		return idcard18Code.equalsIgnoreCase(checkCode);
	}

	// 将15位身份证转换为18位
	public String convertIdcardFrom15bit(String idcard) {

		if (!isDigital(idcard)) {
			return null;
		}

		String year = getYearFrom15idCard(idcard);
		if (year == null) {
			return null;
		}

		String idcard17 = idcard.substring(0, 6) + year + idcard.substring(8);
		return idcard17 + getCheckCode(idcard17);
	}

	private String getYearFrom15idCard(String idCard) {
		String birthday = idCard.substring(6, 12);
		Date birthdate = null;
		try {
			birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);
		} catch (ParseException e) {
			logger.error("解析身份证中出生年月出错：", e);
			return null;
		}
		Calendar cday = Calendar.getInstance();
		cday.setTime(birthdate);
		return String.valueOf(cday.get(Calendar.YEAR));
	}

	private String getCheckCode(String idcard17) {
		char[] c = idcard17.toCharArray();
		int[] bit = converCharToInt(c);
		int sum17 = getPowerSum(bit);
		return LAST_CODE[sum17 % 11];
	}

	private boolean isDigital(String str) {
		return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
	}

	/**
	 * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
	 **/
	private int getPowerSum(int[] bit) {
		int sum = 0;

		for (int i = 0; i < bit.length; i++) {
			sum += bit[i] * POWER[i];
		}
		return sum;
	}

	/**
	 * 将字符数组转为整型数组
	 */
	private int[] converCharToInt(char[] c) {
		int[] a = new int[c.length];
		int k = 0;
		for (char temp : c) {
			a[k++] = Integer.parseInt(String.valueOf(temp));
		}
		return a;
	}
}
