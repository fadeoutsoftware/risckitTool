package it.fadeout.risckit.business;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {
	// static String HOST = "localhost";
	private String HOST_SERVER;
	private String HOST_USER;
	private String HOST_PASSWORD;
	private  Properties  m_sProperties = System.getProperties();      // Get system properties

	private Session m_sSession; 
	public EmailService()
	{
		try 
		{
			HOST_SERVER = ConfigReader.getPropValue("HOST_SERVER");
			HOST_USER = ConfigReader.getPropValue("HOST_USER");
			HOST_PASSWORD = ConfigReader.getPropValue("HOST_PASSWORD");
			// Setup mail server
			
			m_sProperties.setProperty("mail.smtp.host", HOST_SERVER);
			//m_sProperties.setProperty("mail.user", HOST_USER);
			//m_sProperties.setProperty("mail.password", HOST_PASSWORD);
			m_sProperties.setProperty("mail.smtp.auth", "true"); 
			m_sProperties.setProperty("mail.smtp.starttls.enable", "true");
		    // Get the default Session object.
			//m_sSession = Session.getDefaultInstance(m_sProperties);
			m_sSession = Session.getInstance(m_sProperties,
					  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(HOST_USER, HOST_PASSWORD);
				}
			  });
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean SendHtmlEmail(String sTo, String sFrom, String sSubject,String sHtmlText)
	{

		try {
			if( (sTo == null) || (sTo.isEmpty()) )
				return false;
			if( (sFrom == null) || (sFrom.isEmpty()) )
				return false;
			
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(m_sSession);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(sFrom));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(sTo));

			// Set Subject: header field
			message.setSubject(sSubject);

			// Send the actual HTML message, as big as you like
			message.setContent(sHtmlText, "text/html");

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		}catch (MessagingException mex) {
			mex.printStackTrace();
			return false;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
