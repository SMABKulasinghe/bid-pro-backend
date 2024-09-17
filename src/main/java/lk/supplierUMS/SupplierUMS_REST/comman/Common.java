package lk.supplierUMS.SupplierUMS_REST.comman;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Component;

@Component
public class Common {

	
	public boolean sendEMail(List<String> to, String subject, String body) {
		try {
			Properties properties = System.getProperties();

			String email ="bidpro.tender@gmail.com";
			String Password ="dhwq wpum btcx prjs";
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
			
			// End

				
				Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(email, Password);
					}
				});
				Message message = new MimeMessage(session);
				message.addFrom(new InternetAddress[] { new InternetAddress("bidpro.tender@gmail.com") });
				
				for (String string : to) {
					message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(string));
				}
				
				message.setSubject(subject);
				message.setContent(body, "text/plain");

				Transport.send(message);

				System.out.println("Done");

				return true;

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	}
	
	public boolean sendEMailHtml(List<String> to, String subject, String body) {
		try {
			Properties properties = System.getProperties();

			String email ="bidpro.tender@gmail.com";
			String Password ="dhwq wpum btcx prjs";
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
			
			// End

				
				Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(email, Password);
					}
				});
				Message message = new MimeMessage(session);

				message.addFrom(new InternetAddress[] { new InternetAddress("bidpro.tender@gmail.com") });
				
				for (String string : to) {
					message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(string));
				}
				
				message.setSubject(subject);
				message.setContent(body, "text/html");

				Transport.send(message);

				System.out.println("Done");

				return true;

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	}
	
	public boolean sendEMailHtmlWithAttachment(List<String> to, String subject, String body1,String body2, String attachment) {
		try {
			Properties properties = System.getProperties();

			String email ="bidpro.tender@gmail.com";
			String Password ="dhwq wpum btcx prjs";
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
			properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

			
			// End

				
				Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(email, Password);
					}
				});
				Message message = new MimeMessage(session);

				message.addFrom(new InternetAddress[] { new InternetAddress("bidpro.tender@gmail.com") });
				
				for (String string : to) {
					message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(string));
				}
				
				BodyPart messageBodyPart1 = new MimeBodyPart(); 
				messageBodyPart1.setContent(body1,"text/html");
				
				BodyPart messageBodyPart2 = new MimeBodyPart(); 
				messageBodyPart2.setContent(body2,"text/html");
				
				System.out.println("attachment----"+attachment);
				MimeBodyPart attachmentPart = new MimeBodyPart();
				attachmentPart.attachFile(new File(attachment));
				
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart1);
				multipart.addBodyPart(attachmentPart);
				multipart.addBodyPart(messageBodyPart2);

				
				message.setSubject(subject);
				message.setContent(multipart, "text/html");

				Transport.send(message);

				System.out.println("Done");

				return true;

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	}
	
}
