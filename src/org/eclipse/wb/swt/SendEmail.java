package org.eclipse.wb.swt;

import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SendEmail {

	private String fromEmail;
    public String toEmail;
    private String EmailSubject;
    private String EmailBody;
    private String hostDomain;

    private Properties properties;

    private MimeMessage message;
    private Authenticator authenticator;

    public void SendMailProcess () {
        fromEmail = "alhanouf.alsulaiman1990@gmail.com";
        EmailSubject = "URGENT: Full Disk Space";
        EmailBody = "Please Check (E:/) Drive Space.. It is less than 1GB.";
       hostDomain = "smtp.gmail.com";

        authenticator = new SMTPAuthenticator ();        
        properties = System.getProperties ();
        properties.put ( "mail.smtp.host", hostDomain );
        properties.put ( "mail.smtp.starttls.enable", "true" );
        properties.put ( "mail.smtp.port", "587" );
        properties.put ( "mail.smtp.auth", "true" );
        
        sendMail(fromEmail, toEmail, EmailSubject, EmailBody);
    }

    private void sendMail ( String from, String to,
                    String subject, String messageBody) {
        try {
            Session session = Session.getInstance ( properties, authenticator );
            message = new MimeMessage ( session );
            message.setFrom ( new InternetAddress ( from ) );
            message.addRecipient ( Message.RecipientType.TO, new InternetAddress ( to ) );
            message.setSubject ( subject );

            message.setText(messageBody);

            Transport.send ( message );
            System.out.println ( "\n Email Sent successfully...." );
        } catch ( Exception me ) {
            me.printStackTrace ();
        }
    } 

   

class SMTPAuthenticator extends Authenticator {

    public PasswordAuthentication getPasswordAuthentication () {
        String username = "alhanouf.alsulaiman1990@gmail.com";
        String password = "Alhanouf.1990";

        return new PasswordAuthentication( username,  password );
    }
}
}

