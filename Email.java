
//These libraries are for email feature.
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * This class serves for sending an e-mail report of the database to the email
 * address specified by the user.
 * 
 * @author mikitotakeshima
 *
 */
public class Email {
	/**
	 * This function sends email to a recipient address and with the subject line
	 * and content. These values are passed by the parameters of the function.
	 * 
	 * @param recepientAddress
	 * @param subjectLine
	 * @param content
	 * @throws MessagingException
	 */
	public static void sendEmailReport(String recipientAddress, String subjectLine, String content)
			throws MessagingException {
		String senderEmailAddress = "cs3702020projectphase3"; // The default sender's email address.
		String emailPassword = "cs370Project3"; // The password of the account which holds the email address above.

		/*
		 * Declare the instance of Properties. This stores the attributes to send
		 * e-mails.
		 */
		Properties properties = new Properties();

		// Puts the attributes to send e-mails.
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		/*
		 * Establish the session, creating the instance of session, passing the
		 * properties, and authenticating the e-mail address and the password.
		 */
		Session session = Session.getInstance(properties, new Authenticator() {

			@Override // Authenticate the e-mail address and the password of the sender.
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmailAddress, emailPassword);
			}// getPasswordAuthentication
		});

		/*
		 * Create the instance of Message passing the session and e-mail attributes to
		 * the function prepareMessage.
		 */
		Message message = prepareMessage(session, senderEmailAddress, recipientAddress, subjectLine, content);

		Transport.send(message); // This static method sends message passing the message object above.
	}// sendEmailReport

	/**
	 * This function returns Message instance which sets the session, address,
	 * recipient, and the subject values.
	 * 
	 * @param session
	 * @param address
	 * @param recepient
	 * @param subject
	 * @param content
	 * @return Message instance when it successfully sets the values.
	 */
	private static Message prepareMessage(Session session, String address, String recepient, String subject,
			String content) {

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(address));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject(subject);
			message.setText(content);

			return message;
		} // try
		catch (Exception e) {
			System.out.println(e);
		} // catch
		return null;
	}// prepareMessage

}// Email
