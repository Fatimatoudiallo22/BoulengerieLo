package gestion.etudiant.loimmobilier.email;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtil {

    public static boolean sendEmail(String to, String subject, String content) {
        final String fromEmail = "fatimadiallo22ll@gmail.com";
        final String password = "gjcw vlsl axqj fhmz";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
            System.out.println("✅ Email envoyé à " + to);
            return true;

        } catch (AuthenticationFailedException e) {
            System.err.println("❌ Erreur d'authentification SMTP : " + e.getMessage());
            // Ici tu peux notifier l’admin, ou enregistrer dans la BDD que l’email n’a pas été envoyé
            return false;

        } catch (MessagingException e) {
            System.err.println("❌ Erreur lors de l’envoi de l’email : " + e.getMessage());
            return false;
        }
    }


}