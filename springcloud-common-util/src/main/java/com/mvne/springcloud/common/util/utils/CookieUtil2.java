package com.mvne.springcloud.common.util.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 改工具类将用户名保存在cookie中，不同用户用“@”符号拼接，
 * 获取cookie是返回List<String>
 * 设置cookie时需要传入最大保存的用户名数
 * @author MoTing
 *
 */
public class CookieUtil2 {

	 /**
     * 根据名字获取cookie
     * 
     * @param request
     * @param name
     *            cookie名字
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    /**
     * 将cookie封装到Map里面
     * 
     * @param request
     * @return
     */
    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 保存Cookies
     * 
     * @param response
     *            servlet请求
     * @param value
     *            保存值
     * @author jxf
     */
    public static List<String> getCookieAsList(String cookieValue){
    	String[] loginInfoArray = cookieValue.split("@");
		List<String> list = new ArrayList<>();
		for (int i = 0; i < loginInfoArray.length; i++) {
			list.add(loginInfoArray[i]);
		} 
    	return list;
    }
    /**
     * 
     * @param list 要转换成字符串的List集合
     * @return 将集合元素用@符号拼接返回
     */
    private static String cookieListToString(List<String> list) {
		String cookieString = "";
    	for (String username : list) {
    		if(cookieString.isEmpty()) {
				cookieString = username;
			}else {
				cookieString = cookieString+"@"+username;
			}
		}
    	return cookieString;
    	
    }
    /**
     * 
     * @param response 响应
     * @param name cookie名称
     * @param value 要添加到cookie中的值
     * @param time 设置cookie的有效时间
     * @param request 请求
     * @param cookieNumber 设置保存到cookie中的值的数量
     * @return 将修改后的cookie放进响应中返回响应response
     */
    public static HttpServletResponse setCookie(HttpServletResponse response, String name, String value,int time,HttpServletRequest request,int cookieNumber) {
    	// new一个Cookie对象,键值对为参数
    	String newCookieValue = "";
    	Cookie cookie = null;
    	String orgCookievalue = "";
    	try {
    		cookie = getCookieByName(request,name);
    		orgCookievalue = cookie.getValue();
    		List<String> list = getCookieAsList(orgCookievalue);
    		String chgCookieString = "";
    		for (String string : list) {
				if(string.equals(value)) {
					return response;
				}
			}
    		if(list.size() >= cookieNumber) {
    			List<String> overList = new ArrayList<>();
    			for (int i = list.size() - cookieNumber; i < list.size() - 1; i++) {
    				overList.add(list.get(i));
				}
    			chgCookieString = cookieListToString(overList);
    		}else {
    			chgCookieString = cookieListToString(list);
    		}
    		if(orgCookievalue.isEmpty()) {
    			newCookieValue = value;
			}else {
				newCookieValue = value + "@" + chgCookieString;
			}
    		cookie.setValue(newCookieValue);
		} catch (NullPointerException e) {
			cookie = new Cookie(name, value);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    	
        cookie = cookieSetting(cookie, newCookieValue, time);
        // 将Cookie添加到Response中,使之生效
        response.addCookie(cookie); // addCookie后，如果已经存在相同名字的cookie，则最新的覆盖旧的cookie
        return response;
    }
    /**
     * 
     * @param cookie 要进行属性配置的cookie
     * @param value cookie值
     * @param time 有效时间
     * @return 返回修改后的cookie
     */
    private static Cookie cookieSetting(Cookie cookie,String value,int time) {
    	cookie.setPath("/");
        // 如果cookie的值中含有中文时，需要对cookie进行编码，不然会产生乱码
        try {
            URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        cookie.setMaxAge(time);
    	return cookie;
    	
    }
    
    /**
     * <p>删除无效cookie</p>
     * <p>无效☞1.过时 2.未发布</p>
     * @param request
     * @param response
     * @param list
    */
	public static HttpServletResponse delectCookieByName(HttpServletRequest request, HttpServletResponse response,String deleteKey) throws NullPointerException {
    	Map<String, Cookie> cookieMap = ReadCookieMap(request);
    	for (String key : cookieMap.keySet()) {   
    		if(key.equals(deleteKey)) {
    			Cookie cookie = cookieMap.get(key);
    			cookie.setValue(null);
    			cookie.setMaxAge(-1);//设置cookie有效时间为0
    			cookie.setPath("/");//不设置存储路径
    			response.addCookie(cookie);
    			return response;
    		}
    	}
		return response; 
    }
}
