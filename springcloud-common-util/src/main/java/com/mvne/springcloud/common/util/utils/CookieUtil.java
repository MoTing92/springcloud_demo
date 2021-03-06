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

import com.mvne.springcloud.common.util.model.UserInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * 改工具类将用户名保存在cookie中，不同用户用“@”符号拼接，用户和密码之间用“#”符号拼接，
 * 获取cookie是返回List<Map<String,UserInfo>>
 * 设置cookie时需要传入最大保存的用户名数
 * @author MoTing
 *
 */
@Slf4j
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
    public static List<UserInfo> getCookieAsList(String cookieValue){
    	String[] loginInfoArray = cookieValue.split("@");
		List<UserInfo> list = new ArrayList<>();
		for (int i = 0; i < loginInfoArray.length; i++) {
			String[] loginInfo = loginInfoArray[i].split("#");
			try {
				UserInfo user = new UserInfo();
				if(loginInfo[0].isEmpty()) {
					System.err.println("cookie工具类-用户名为空");
					log.error("cookie工具类-用户名为空");
				}
				user.setUsername(loginInfo[0]);
				if(loginInfo[1].isEmpty()) {
					System.err.println("cookie工具类-密码为空");
					log.error("cookie工具类-密码为空");
				}
				user.setPassword(loginInfo[1]);
				list.add(user);
			}catch (ArrayIndexOutOfBoundsException e) {
				System.err.println("密码为空");
				return null;
			} catch (Exception e) {
				e.printStackTrace();;
				return null;
			}
		} 
    	return list;
    }
    
    private static String cookieListToString(List<UserInfo> list) {
		String cookieString = "";
    	for (int i = 0; i < list.size(); i++) {
    		if(i == 0 && list.size() == 1) {
    			cookieString += list.get(i).getUsername()+"#"+list.get(i).getPassword();
    			continue;
    		}
    		if(i == list.size()-1 && list.size() != 1) {
    			cookieString += list.get(i).getUsername()+"#"+list.get(i).getPassword();
    			break;
    		}
    		cookieString += list.get(i).getUsername()+"#"+list.get(i).getPassword() + "@";
		}
    	return cookieString;
    	
    }
    public static HttpServletResponse setCookie(HttpServletResponse response, String name, String value,int time,HttpServletRequest request,int cookieNumber) {
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
    		List<UserInfo> list = getCookieAsList(orgCookievalue);
    		for (UserInfo user : list) {
				if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
					return response;
				}else if(user.getUsername().equals(username) && !user.getPassword().equals(password)) {
					user.setPassword(password);
					String chgCookieString = cookieListToString(list);
    				cookie.setValue(chgCookieString);
    				cookie = cookieSetting(cookie, chgCookieString, time);
    				response.addCookie(cookie);
    				return response;
				}
			}
    		String chgCookieString = "";
			if(list.size() >= cookieNumber) {
    			List<UserInfo> overList = new ArrayList<>();
    			for (int i = list.size() - cookieNumber; i < list.size() - 1; i++) {
    				overList.add(list.get(i));
				}
    			chgCookieString  = cookieListToString(overList);
    		}else {
    			chgCookieString = cookieListToString(list);
    		}
    		if(orgCookievalue.isEmpty()) {
    			newCookieValue = value;
			}else {
				newCookieValue = value + "@" + chgCookieString ;
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
