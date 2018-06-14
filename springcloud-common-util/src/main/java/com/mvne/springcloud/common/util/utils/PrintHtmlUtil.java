package com.mvne.springcloud.common.util.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.mvne.springcloud.common.util.model.Recipient;

public class PrintHtmlUtil {

	public static String saveHtml(String htmlAddress,String savePath){  
        try{  
            URLConnection conn = new URL(htmlAddress).openConnection();  
            conn.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");  
            InputStream is = conn.getInputStream();  
            FileOutputStream out = new FileOutputStream(savePath);  
            int a=0;  
            while((a = is.read()) != -1){  
                    out.write(a);  
            }
            is.close();  
            out.close();
            System.out.println("保存HTML成功");
            return savePath;
        }catch(Exception e){  
            System.out.println(e);
            return null;
        }  
	}
	
	public static void main(String[] args) throws Exception {
		String path = PrintHtmlUtil.saveHtml("https://blog.csdn.net/havedream_one/article/details/44965101", "E:\\html3.html");
//		String path = PrintHtmlUtil.saveHtml("http://localhost:6006/channel/html/login/login.html", "E:\\html2.html");
		File file = new File(path);
		FileInputStream in = new FileInputStream(new File(path));
		StringBuffer contentBuffer = new StringBuffer();
		int size =  (int)file.length();
		int len = 0;  
			byte[] buf = new byte[size];  
		    while((len=in.read(buf))!=-1){  
		        contentBuffer.append(new String(buf,0,len));
//		    	System.out.println(new String(buf,0,len));  
		    }  
		      
		    //关资源  
		in.close();
		String content = contentBuffer.toString();
		Recipient recipient = new Recipient();
		recipient.setEmail("837913368@qq.com");
		recipient.setRecipienName("测试人员");
		SendEmailUtil.sendMsg("sds", recipient, null, null, "ceshi", content, "13534142722@163.com", "xiejin123456");
//		System.out.println(contentBuffer.toString());
	}
}
