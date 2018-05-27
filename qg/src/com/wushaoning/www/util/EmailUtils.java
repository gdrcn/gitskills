package com.wushaoning.www.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.wushaoning.www.dao.LoadingDao;
import com.wushaoning.www.dao.UserDao;

/**
 * 忘记密码的邮箱工具具
 * @author 10620
 *
 */
public class EmailUtils {

	/**
	 * 发送邮件
	 * @param remail
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public static void sendemail(String remail) throws AddressException, MessagingException {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", "smtp.qq.com");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.ssl.enable", "true");
		props.setProperty("mail.debug", "true");

		Session session = Session.getInstance(props);
		
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress("1062081835@qq.com"));
		message.setRecipients(RecipientType.TO, new InternetAddress[] { new InternetAddress(remail) });
		String rpwd = RandomNumberUtils.getRandomNum(6);
		message.setSubject("您好，您已成功修改密码");
		message.setText(" 您修改的6位密码为" + rpwd + "，请尽快修改个人信息");
		String password = DigestUtils.md5(rpwd);
		Connection con = LoadingDao.getCon();
		try {
			UserDao.updatePwd(remail, password, con);
		} catch (SQLException e) { 
		} finally {
			LoadingDao.close(con);
		}

		
		Transport transport = session.getTransport();
		transport.connect("1062081835@qq.com", "xvkyqdwrpvkcbegd");
		transport.sendMessage(message, message.getAllRecipients());

	}
}