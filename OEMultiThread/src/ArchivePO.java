import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.smtp.SMTPTransport;

public class ArchivePO {
	
	
	public static void main() {
		File attch;
		
	//	EmailAttachment attachment;
	//	MultiPartEmail email;
		
		Properties prop;
		String mailhost = "mail.apple.com";
		String mailer = "smtpsend";
		
		attch = new File (OEMultiT.inputPath.getAbsolutePath() + "/" + "ing.bar.53938.5958377.pdf");
		
		//set properties
		prop = System.getProperties();
		prop.put("mail.smtps.host", mailhost);
		prop.put("mail.smtps.ssl.enable", "true");
		prop.put("mail.smtps.auth", "true");
		
		//instaciate a session
		javax.mail.Session session = javax.mail.Session.getInstance(prop, null);
		
		//create the email message
		Message email = new MimeMessage(session);
		try {
			email.setFrom(new InternetAddress("gpinheiro@apple.com"));
			email.setRecipient(RecipientType.TO, new InternetAddress("gpinheiro@apple.com"));
			email.setSubject("soldTo,PO#,SO#");
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		MimeBodyPart mbp1 = new MimeBodyPart();
		try {
			mbp1.setText("__");
			MimeBodyPart mbp2 = new MimeBodyPart();
			mbp2.attachFile(attch);
			MimeMultipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(mbp2);
			email.setContent(mp);
			email.setHeader("X-Mailer", mailer);
		    email.setSentDate(new Date());
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SMTPTransport t = null;
		try {
			t = (SMTPTransport)session.getTransport("smtps");
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		try {
			t.connect(mailhost, "gpinheiro", "donner25");
			t.sendMessage(email, email.getAllRecipients());
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
				try {
					System.out.println("Response: " + t.getLastServerResponse());
					t.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
	    }
	}
}
