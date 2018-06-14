package com.mvne.springcloud.common.util.utils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.springframework.web.multipart.MultipartFile;

import com.mvne.springcloud.common.util.model.Recipient;
/**
 * 
 * @author MoTing
 * 
 */
public class SendEmailUtil {
	
	//测试
	public static void main(String[] args) {
		// String a = CommonUtils.getVerificationCode();
		// List<Integer> list = new ArrayList<Integer>();
		Recipient recipient = new Recipient();
		recipient.setEmail("18209202332@163.com");
		recipient.setRecipienName("moting");
		try {
			String content = getEmailContent(null, null, null);
			SendEmailUtil.sendMsg("yidong", recipient,null, null,"title", content , "13534142722@163.com", "xiejin123456");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	

	public static String myEmailSMTPHost = "smtp.163.com";

	/**
	 * 
	 * @param senderName 发件人姓名
	 * @param recipient 收件人
	 * @param cc 抄送人
	 * @param bcc 密送人
	 * @param title 邮件标题
	 * @param content 邮件内容
	 * @param myEmailAccount 发件人邮箱
	 * @param myEmailPassword 发件人邮箱设置的客户端授权密码（非邮箱登陆密码）
	 * @throws Exception
	 */
	public static void sendMsg(String senderName, Recipient recipient,Recipient cc,Recipient bcc, String title, String content, String myEmailAccount,
			String myEmailPassword) throws Exception {

		// 1. 创建参数配置, 用于连接邮件服务器的参数配置
		Properties properties = new Properties(); // 参数配置
		properties.put("mail.transport.protocol", "smtp");// 连接协议
		properties.put("mail.smtp.host", myEmailSMTPHost); // 主机名
		// properties.put("mail.smtp.port", 465); // 端口号
		properties.put("mail.smtp.auth", "true");
		// properties.put("mail.smtp.ssl.enable", "true"); // 设置是否使用ssl安全连接
		// ---一般都使用
		properties.put("mail.debug", "true");

		Session session = Session.getDefaultInstance(properties);
		session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log

		// 3. 创建一封邮件
		MimeMessage message = SendEmailUtil.createMimeMessage(senderName,title, content, session, myEmailAccount, recipient,cc,bcc);

		// 4. 根据 Session 获取邮件传输对象
		Transport transport = session.getTransport();

		transport.connect(myEmailAccount, myEmailPassword);

		// 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人,
		// 抄送人, 密送人
		transport.sendMessage(message, message.getAllRecipients());

		// 7. 关闭连接
		transport.close();
	}
	
	public static void sendMsgWithAppendix(String senderName, Recipient recipient,Recipient cc,Recipient bcc, String title, String content, String myEmailAccount,
			String myEmailPassword,MultipartFile emailFile) throws Exception {

		// 1. 创建参数配置, 用于连接邮件服务器的参数配置
		Properties properties = new Properties(); // 参数配置
		properties.put("mail.transport.protocol", "smtp");// 连接协议
		properties.put("mail.smtp.host", myEmailSMTPHost); // 主机名
		// properties.put("mail.smtp.port", 465); // 端口号
		properties.put("mail.smtp.auth", "true");
		// properties.put("mail.smtp.ssl.enable", "true"); // 设置是否使用ssl安全连接
		// ---一般都使用
		properties.put("mail.debug", "true");

		Session session = Session.getDefaultInstance(properties);
		session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log

		// 3. 创建一封邮件
		MimeMessage message = SendEmailUtil.createAppendixMessage(senderName,title, content, session, myEmailAccount, recipient,cc,bcc,emailFile);

		// 4. 根据 Session 获取邮件传输对象
		Transport transport = session.getTransport();

		transport.connect(myEmailAccount, myEmailPassword);

		// 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人,
		// 抄送人, 密送人
		transport.sendMessage(message, message.getAllRecipients());

		// 7. 关闭连接
		transport.close();
	}

	/**
	 * 创建一封只包含文本的简单邮件
	 *
	 * @param session
	 *            和服务器交互的会话
	 * @param sendMail
	 *            发件人邮箱
	 * @param receiveMail
	 *            收件人邮箱
	 * @return
	 * @throws Exception
	 */
	private static MimeMessage createMimeMessage(String senderName,String title, String content, Session session, String sendMail,
			Recipient recipient,Recipient cc,Recipient bcc) throws Exception {
		// 1. 创建一封邮件
		MimeMessage message = new MimeMessage(session);

		// 2. From: 发件人
		message.setFrom(new InternetAddress(sendMail, senderName, "UTF-8"));

		// 3. To: 收件人（可以增加多个收件人、抄送、密送）
		message.setRecipient(MimeMessage.RecipientType.TO,
				new InternetAddress(recipient.getEmail(), recipient.getRecipienName(), "UTF-8"));
		if(cc != null) {
			// 抄送人
			message.setRecipient(MimeMessage.RecipientType.CC, 
					new InternetAddress(cc.getEmail(), cc.getRecipienName(), "UTF-8"));
		}
		if(bcc != null) {
			// 抄送人
			message.setRecipient(MimeMessage.RecipientType.BCC, 
					new InternetAddress(bcc.getEmail(), bcc.getRecipienName(), "UTF-8"));
		}
		// 4. Subject: 邮件主题
		
		message.setSubject(title, "UTF-8");

		// 5. Content: 邮件正文（可以使用html标签）
		message.setContent(content, "text/html;charset=UTF-8");

		// 6. 设置发件时间
		message.setSentDate(new Date());

		// 7. 保存设置
		message.saveChanges();

		return message;
	}
	
	private static MimeMessage createAppendixMessage(String senderName,String title, String content, Session session, String sendMail,
			Recipient recipient,Recipient cc,Recipient bcc,MultipartFile emailFile) throws Exception {
		// 1. 创建一封邮件
		MimeMessage message = new MimeMessage(session);

		// 2. From: 发件人
		message.setFrom(new InternetAddress(sendMail, senderName, "UTF-8"));

		// 3. To: 收件人（可以增加多个收件人、抄送、密送）
		message.setRecipient(MimeMessage.RecipientType.TO,
				new InternetAddress(recipient.getEmail(), recipient.getRecipienName(), "UTF-8"));
		if(cc != null) {
			// 抄送人
			message.setRecipient(MimeMessage.RecipientType.CC, 
					new InternetAddress(cc.getEmail(), cc.getRecipienName(), "UTF-8"));
		}
		if(bcc != null) {
			// 抄送人
			message.setRecipient(MimeMessage.RecipientType.BCC, 
					new InternetAddress(bcc.getEmail(), bcc.getRecipienName(), "UTF-8"));
		}
		// 4. Subject: 邮件主题
		message.setSubject(title, "UTF-8");
		// 创建消息部分
		BodyPart messageBodyPart = new MimeBodyPart();
		 // 消息
		messageBodyPart.setText(content);
		// 创建多重消息
		Multipart multipart = new MimeMultipart();
		// 设置文本消息部分 
		multipart.addBodyPart(messageBodyPart);
		// 附件部分 
		messageBodyPart = new MimeBodyPart();
		File file = multipartFileToFile(emailFile);
		DataSource source = new FileDataSource(file);
		messageBodyPart.setDataHandler(new DataHandler(source)); 
	     
	    //messageBodyPart.setFileName(filename); 
	    //处理附件名称中文（附带文件路径）乱码问题 
	    messageBodyPart.setFileName(MimeUtility.encodeText(file.getName())); 
	    multipart.addBodyPart(messageBodyPart); 
	   
	    // 发送完整消息 
	    message.setContent(multipart ); 

		// 5. Content: 邮件正文（可以使用html标签）
//		message.setContent(content, "text/html;charset=UTF-8");

		// 6. 设置发件时间
		message.setSentDate(new Date());

		// 7. 保存设置
		message.saveChanges();

		return message;
	}
	
	public static File multipartFileToFile(MultipartFile multfile) {
        // 获取文件名
        String fileName = multfile.getOriginalFilename();
        // 获取文件后缀
        String prefix=fileName.substring(fileName.lastIndexOf("."));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String tempFileName = sdf.format(new Date());
       
        File excelFile = null;
		try {
			excelFile = File.createTempFile(tempFileName, prefix);
			// MultipartFile to File
			multfile.transferTo(excelFile);
			System.err.println("临时文件路径："+excelFile.getAbsolutePath());
			return excelFile;
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			//程序结束时，删除临时文件
//	        deleteFile(excelFile);
		}
        return null;
     }

    /**  
     * 删除  
     *   
     * @param files  
     */  
    @SuppressWarnings("unused")
	private static void deleteFile(File... files) {  
        for (File file : files) {  
            if (file.exists()) {  
                file.delete();  
            }  
        }  
    }


	public static String getEmailTitle(String superChannelName,String lowerChannelName,String yearMonth) {
		String title = superChannelName + "渠道商与" + lowerChannelName + "渠道商" +""+yearMonth+"的结算结果";
		return title;
	}
	
	public static String getEmailContent(String superChannelName,String lowerChannelName,String yearMonth) {
		String content = "<table>\r\n" + 
				"				<thead>\r\n" + 
				"					<tr>\r\n" + 
				"						<td>序号</td>\r\n" + 
				"						<td>渠道商编号</td>\r\n" + 
				"						<td>渠道商名称</td>\r\n" + 
				"						<td>状态</td>\r\n" + 
				"						<td>操作组织</td>\r\n" + 
				"						<td>法人姓名</td>\r\n" + 
				"						<td>法人身份证号</td>\r\n" + 
				"						<td>法人电话</td>\r\n" + 
				"						<td>法人邮箱</td>\r\n" + 
				"						<td>操作</td>\r\n" + 
				"					</tr>\r\n" + 
				"				</thead>\r\n" + 
				"				<tbody>\r\n" + 
				"					<tr>\r\n" + 
				"					<td>1</td>\r\n" + 
				"					<td>XXX001</td>\r\n" + 
				"					<td>北京渠道</td>\r\n" + 
				"					<td>有效</td>\r\n" + 
				"					<td>北京渠道测试1</td>\r\n" + 
				"					<td>张三</td>\r\n" + 
				"					<td>44011119901201XXXX</td>\r\n" + 
				"					<td>13900000000</td>\r\n" + 
				"					<td>XXX@XXX.com</td>\r\n" + 
				"					<td><span class=\"amend\">修改</span><span class=\"delete\">删除</span></td>\r\n" + 
				"					</tr>\r\n" + 
				"					<tr>\r\n" + 
				"					<td>1</td>\r\n" + 
				"					<td>XXX001</td>\r\n" + 
				"					<td>北京渠道</td>\r\n" + 
				"					<td>有效</td>\r\n" + 
				"					<td>北京渠道测试1</td>\r\n" + 
				"					<td>张三</td>\r\n" + 
				"					<td>44011119901201XXXX</td>\r\n" + 
				"					<td>13900000000</td>\r\n" + 
				"					<td>XXX@XXX.com</td>\r\n" + 
				"					<td><span class=\"amend\" onclick=\"showBg()\">修改</span><span class=\"delete\" onclick=\"showdbg()\">删除</span></td>\r\n" + 
				"					</tr>\r\n" + 
				"				</tbody>\r\n" + 
				"			</table>";
		return content;
	}
}
