package it.fadeout.risckit.business;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {
	private static String HOST = "localhost";
	private  Properties  m_sProperties = System.getProperties();      // Get system properties

	private Session m_sSession; 
	public EmailService(String sMailServer)
	{
		try 
		{
			// Setup mail server
			m_sProperties.setProperty("mail.smtp.host", HOST);

		    // Get the default Session object.
			m_sSession = Session.getDefaultInstance(m_sProperties);
			
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
