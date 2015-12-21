package treenote.web.user;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	
	public void send(String title, String receiver, String content) {
		
		System.out.println("SendEmail.send 시작");
		Properties properties = new Properties();

		try {
			System.out.println("들어옴");
			properties.put("mail.smtp.starttls.enable", true); // added this line
			properties.put("mail.smtp.host", "smtp.gmail.com");
		    properties.put("mail.smtp.user", "treeNote74@gmail.com");
		    properties.put("mail.smtp.password", "@@java74");
		    properties.put("mail.smtp.port", "587");
		    properties.put("mail.smtp.auth", true);
		    
//			properties.load(getClass().getResourceAsStream("/config/emailauth.properties"));


			Authenticator authenticator = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(properties.getProperty("mail.smtp.user"),
							properties.getProperty("mail.smtp.password"));
				}
			};

			Session session = Session.getInstance(properties, authenticator);
			session.setDebug(false);

			MimeMessage message = new MimeMessage(session);

			message.addHeader("Content-type", "text/html; charset=UTF-8");
			message.addHeader("format", "flowed");
			message.addHeader("Content-Transfer-Encoding", "8bit");

			message.setFrom(new InternetAddress(properties.getProperty("mail.smtp.user"), "관리자"));
			message.setReplyTo(InternetAddress.parse(properties.getProperty("mail.smtp.user"), false));

			message.setSubject(title, "UTF-8");
			message.setContent(content, "text/html; charset=utf-8");
			message.setSentDate(new Date());

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver, false));

			Transport.send(message);
			System.out.println("SendEmail.send 성공");

		} catch (IOException ie) {
			// TODO Auto-generated catch block
			ie.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}

	}
}
