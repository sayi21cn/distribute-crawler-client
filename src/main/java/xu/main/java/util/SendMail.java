package xu.main.java.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import xu.main.java.config.CommonConfig;
import xu.main.java.config.MailConfig;

/**
 * 
 * @author xu
 * 
 */
public class SendMail {
	private static Logger logger = Logger.getLogger(SendMail.class);
	private static String username = "";
	private static String password = "";
	private static String smtpServer = "";
	private static String fromMailAddress = "";
	private static String version = CommonConfig.VERSION;
	private static String mailNeck = "mail";
	private static InternetAddress[] toMailAddress = new InternetAddress[1];

	private static Authenticator authorticator = null;
	private static Properties props = null;
	static {
		String mailU = MailConfig.MAIL_USER_NAME;
		String mailP = MailConfig.MAIL_PASS_WORD;
		String mailTo = MailConfig.MAIL_TO;
		String tempSmtpServer = MailConfig.SMTP_SERVER;

		if (!StringHandler.isNullOrEmpty(mailU) && !StringHandler.isNullOrEmpty(mailP)) {
			username = mailU;
			password = mailP;
			fromMailAddress = mailU;
		}
		if (!StringHandler.isNullOrEmpty(tempSmtpServer)) {
			smtpServer = tempSmtpServer;
		}

		if (!StringHandler.isNullOrEmpty(mailTo)) {
			String[] mailArray = mailTo.split(",");
			toMailAddress = new InternetAddress[mailArray.length];
			for (int mailIndex = 0; mailIndex < mailArray.length; mailIndex++) {
				try {
					toMailAddress[mailIndex] = new InternetAddress(mailArray[mailIndex]);
				} catch (AddressException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				toMailAddress[0] = new InternetAddress("54786654@qq.com");
			} catch (AddressException e) {
				e.printStackTrace();
			}
		}

		authorticator = new SmtpAuthenticator(username, password);
		props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", smtpServer);
	}

	public static void sendErrorMail(String title, String message, Throwable aThrowable) {
		try {
			sendHtmlMail(title, message + "<br/>" + getHtmlStackTrack(aThrowable));
		} catch (Exception e1) {
			logger.error("SendMail Exception ", e1);
		}
	}

	public static void sendHtmlMail(String title, String mailContent) throws Exception {
		sendHtmlMail(title, mailContent, toMailAddress);
	}

	public static void sendHtmlMail(String title, String mailContent, InternetAddress[] address) throws AddressException, MessagingException, UnsupportedEncodingException {

		// 获得邮件会话对象
		Session session = Session.getDefaultInstance(props, authorticator);
		/** *************************************************** */
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String dateTime = df.format(new Date());// new Date()为获取当前系统时间
		// 创建MIME邮件对象
		MimeMessage mimeMessage = new MimeMessage(session);
		if (!StringHandler.isNullOrEmpty(MailConfig.MAIL_NICK)) {
			mimeMessage.setFrom(new InternetAddress(fromMailAddress, MailConfig.MAIL_NICK));
		} else {
			mimeMessage.setFrom(new InternetAddress(fromMailAddress, mailNeck));// 发件人
		}
		mimeMessage.setRecipients(Message.RecipientType.TO, address);// 收件人
		mimeMessage.setSubject(version + title, "utf-8");
		mimeMessage.setSentDate(new Date());// 发送日期
		Multipart mp = new MimeMultipart("related");// related意味着可以发送html格式的邮件
		/** *************************************************** */

		BodyPart bodyPart = new MimeBodyPart();// 正文

		mailContent += "<br/>" + dateTime;

		bodyPart.setDataHandler(new DataHandler(mailContent, "text/html;charset=GBK"));// 网页格式
		/** *************************************************** */
		// BodyPart attachBodyPart = new MimeBodyPart();// 普通附件
		// FileDataSource fds = new FileDataSource("c:/boot.ini");
		// attachBodyPart.setDataHandler(new DataHandler(fds));
		// attachBodyPart.setFileName("=?GBK?B?"
		// + new sun.misc.BASE64Encoder().encode(fds.getName().getBytes())
		// + "?=");// 解决附件名中文乱码
		// mp.addBodyPart(attachBodyPart);
		/** *************************************************** */
		// MimeBodyPart imgBodyPart = new MimeBodyPart(); // 附件图标
		// byte[] bytes = readFile("C:/button.gif");
		// ByteArrayDataSource fileds = new ByteArrayDataSource(bytes,
		// "application/octet-stream");
		// imgBodyPart.setDataHandler(new DataHandler(fileds));
		// imgBodyPart.setFileName("button.gif");
		// imgBodyPart.setHeader("Content-ID", "<img1></img1>");//
		// 在html中使用该图片方法src="cid:IMG1"
		// mp.addBodyPart(imgBodyPart);
		/** *************************************************** */
		mp.addBodyPart(bodyPart);
		mimeMessage.setContent(mp);// 设置邮件内容对象
		Transport.send(mimeMessage);// 发送邮件
	}

	public static String getHtmlStackTrack(Throwable aThrowable) {

		if (aThrowable == null)
			return "";
		StringBuffer sb = new StringBuffer();
		sb.append(aThrowable.toString()).append("&nbsp;").append(aThrowable.getMessage()).append("<br/>");
		for (StackTraceElement stackTrack : aThrowable.getStackTrace()) {
			sb.append("&nbsp;&nbsp;").append(stackTrack).append("<br/>");
		}
		return sb.toString();
	}
}

/**
 * Smtp认证
 */
class SmtpAuthenticator extends Authenticator {
	String username = null;
	String password = null;

	// SMTP身份验证
	public SmtpAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(this.username, this.password);
	}

}

class ByteArrayDataSource implements DataSource {

	private final String contentType;
	private final byte[] buf;
	private final int len;

	public ByteArrayDataSource(byte[] buf, String contentType) {
		this(buf, buf.length, contentType);
	}

	public ByteArrayDataSource(byte[] buf, int length, String contentType) {
		this.buf = buf;
		this.len = length;
		this.contentType = contentType;
	}

	public String getContentType() {
		if (contentType == null)
			return "application/octet-stream";
		return contentType;
	}

	public InputStream getInputStream() {
		return new ByteArrayInputStream(buf, 0, len);
	}

	public String getName() {
		return null;
	}

	public OutputStream getOutputStream() {
		throw new UnsupportedOperationException();
	}

}
