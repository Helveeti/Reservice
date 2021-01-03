package com.varausjarjestelma.sähköposti;
// File Name SimppeliMaili.java

import java.security.Timestamp;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.varausjarjestelma.logiikka.Varausmanageri;

/**
 * A class for sending email messages.
 * 
 * @author E. Niemi
 *
 */
public class SimppeliMaili {

	String varatunTilanNimi;
	Timestamp aloitusaika;
	Timestamp lopetusaika;

	/**
	 * Sends a message to the email address
	 * passed as an argument.
	 * 
	 * @param recipient
	 * @throws MessagingException
	 */
	public static void sendMail(String recipient) throws MessagingException {
		System.out.println("valmistaudutaan emailin lähettämiseen..");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enalble", true);
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.starttls.enable", "true");

		// lähettäjäosoite ja salasana
		final String myAccountEmail = "reservicefinland@gmail.com";
		final String password = "team7team7";

		Session session = Session.getInstance(properties, new Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountEmail, password);
			}
		});
		Message message = prepareMessage(session, myAccountEmail, recipient);
		Transport.send(message);
		System.out.println("viesti lähetetty!");

	}

	private static Message prepareMessage(Session session, String myAccountEmail, String recipient) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject("Reserviceltä viestiä!!");
			String osaviestiä = new Varausmanageri().varaustiedotMailiin();
			message.setText("Reserviceltä terve, " + osaviestiä + " Kiitos!");
			return message;
		} catch (Exception ex) {
			Logger.getLogger(SimppeliMaili.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

}
