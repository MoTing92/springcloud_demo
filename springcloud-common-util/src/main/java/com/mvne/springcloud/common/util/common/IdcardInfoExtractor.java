
package com.mvne.springcloud.common.util.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IdcardInfoExtractor {
	
	private static  final Logger logger = LoggerFactory.getLogger(IdcardInfoExtractor.class);

	// 省份
	private String province;
	// 城市
	private String city;
	// 区县
	private String region;
	// 年份
	private int year;
	// 月份
	private int month;
	// 日期
	private int day;
	// 性别
	private String gender;
	// 出生日期
	private Date birthday;
	// 年龄
	private int age;

	private static final Map<String, String> cityCodeMap = new HashMap<>();

	static {
		cityCodeMap.put("11", "北京");
		cityCodeMap.put("12", "天津");
		cityCodeMap.put("14", "山西");
		cityCodeMap.put("15", "内蒙古");
		cityCodeMap.put("21", "辽宁");
		cityCodeMap.put("22", "吉林");
		cityCodeMap.put("23", "黑龙江");
		cityCodeMap.put("31", "上海");
		cityCodeMap.put("32", "江苏");
		cityCodeMap.put("33", "浙江");
		cityCodeMap.put("34", "安徽");
		cityCodeMap.put("35", "福建");
		cityCodeMap.put("36", "江西");
		cityCodeMap.put("37", "山东");
		cityCodeMap.put("41", "河南");
		cityCodeMap.put("42", "湖北");
		cityCodeMap.put("43", "湖南");
		cityCodeMap.put("44", "广东");
		cityCodeMap.put("45", "广西");
		cityCodeMap.put("46", "海南");
		cityCodeMap.put("50", "重庆");
		cityCodeMap.put("51", "四川");
		cityCodeMap.put("52", "贵州");
		cityCodeMap.put("53", "云南");
		cityCodeMap.put("54", "西藏");
		cityCodeMap.put("61", "陕西");
		cityCodeMap.put("62", "甘肃");
		cityCodeMap.put("63", "青海");
		cityCodeMap.put("64", "宁夏");
		cityCodeMap.put("65", "新疆");
		cityCodeMap.put("71", "台湾");
		cityCodeMap.put("81", "香港");
		cityCodeMap.put("82", "澳门");
		cityCodeMap.put("91", "国外");
	}

	private static final IdcardValidator validator =  new IdcardValidator();

	public void initByIdCard(String idcard) {
		init(idcard);
	}

	private boolean init(String idcard) {
		try {
		String strIdCard = idcard;
		if (strIdCard.length() == 15) {
			strIdCard = validator.convertIdcardFrom15bit(strIdCard);
		}
		this.province = cityCodeMap.get(strIdCard.substring(0, 2));
		this.gender= Integer.parseInt(strIdCard.substring(16, 17)) % 2 != 0?"1":"0";
		initBirthDay(strIdCard);
		}catch(Exception e) {
			logger.error("身份证信息有误", e);
			return false;
		}
		return true;
	}

	private void initBirthDay(String strIdCard) throws ParseException {
		String strBirthday = strIdCard.substring(6, 14);
		Date birthDate = new SimpleDateFormat("yyyyMMdd").parse(strBirthday);
		this.birthday = birthDate;
		GregorianCalendar currentDay = new GregorianCalendar();
		currentDay.setTime(birthDate);
		this.year = currentDay.get(Calendar.YEAR);
		this.month = currentDay.get(Calendar.MONTH) + 1;
		this.day = currentDay.get(Calendar.DAY_OF_MONTH);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
		String currentYear = simpleDateFormat.format(new Date());
		this.age = Integer.parseInt(currentYear) - this.year;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "省份：" + this.province + ",性别：" + this.gender + ",出生日期：" + this.birthday;
	}

}
