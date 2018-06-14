package com.mvne.springcloud.common.util.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvne.springcloud.common.util.model.UserInfo;

public class CookieUtil {

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
    public static List<Map<String,UserInfo>> getCookieAsList(String cookieValue){
    	String[] loginInfoArray = cookieValue.split("@");
		List<Map<String, UserInfo>> list = new ArrayList<>();
		for (int i = 0; i < loginInfoArray.length; i++) {
			String[] loginInfo = loginInfoArray[i].split("#");
			Map<String, UserInfo> map = new HashMap<>();
			try {
				UserInfo user = new UserInfo();
				user.setUsername(loginInfo[0]);
				user.setPassword(loginInfo[1]);
				map.put(loginInfo[0], user);
			}catch (ArrayIndexOutOfBoundsException e) {
				System.err.println("密码为空");
				return null;
			} catch (Exception e) {
				e.printStackTrace();;
				return null;
			}
			list.add(map);
		} 
    	return list;
    }
    
    private static String cookieListToString(List<Map<String, UserInfo>> list) {
		String coolieString = "";
    	for (Map<String, UserInfo> map : list) {
			for (Entry<String, UserInfo> userInfoMap : map.entrySet()) {
				UserInfo userInfo = userInfoMap.getValue();
				if(coolieString.isEmpty()) {
					coolieString = userInfo.getUsername()+"#"+userInfo.getPassword();
				}else {
					coolieString = coolieString+"@"+userInfo.getUsername()+"#"+userInfo.getPassword();
				}
			}
		}
    	return coolieString;
    	
    }
    public static HttpServletResponse setCookie(HttpServletResponse response, String name, String value,int time,HttpServletRequest request) {
    	// new一个Cookie对象,键值对为参数
    	String newCookieValue = "";
    	Cookie cookie = null;
    	String orgCookievalue = "";
    	try {
    		cookie = getCookieByName(request,name);
    		orgCookievalue = cookie.getValue();
    		String[] loginInfo = value.split("#");
    		String username = loginInfo[0];
    		String password = loginInfo[1];
    		List<Map<String, UserInfo>> list = getCookieAsList(orgCookievalue);
    		for (Map<String, UserInfo> map : list) {
    			UserInfo orgUser = map.get(username);
    			if(orgUser != null && !orgUser.getPassword().equals(password)) {
    				orgUser.setPassword(password);
    				String chgCookieString = cookieListToString(list);
    				cookie.setValue(chgCookieString);
    				cookie = cookieSetting(cookie, chgCookieString, time);
    				response.addCookie(cookie);
    				return response;
    			}
    			if(orgUser != null) {
    				return response;
    			}
			}
    		if(orgCookievalue.isEmpty()) {
    			newCookieValue = value;
			}else {
				newCookieValue = orgCookievalue + "@" + value;
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
    @SuppressWarnings("unused")
	public static void delectCookieByName(HttpServletRequest request, HttpServletResponse response,String deleteKey) throws NullPointerException {
    	Map<String, Cookie> cookieMap = ReadCookieMap(request);
    	for (String key : cookieMap.keySet()) {   
    		if(key == deleteKey && key.equals(deleteKey)) {
    			Cookie cookie = cookieMap.get(key);
    			cookie.setMaxAge(0);//设置cookie有效时间为0
    			cookie.setPath("/");//不设置存储路径
    			response.addCookie(cookie);
    		}
    	} 
    }
}
