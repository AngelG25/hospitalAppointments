package com.portfolio.srv;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailSrv {

  private final JavaMailSender mailSender;

  public void sendEmail(String destinatary, String subject, String body) {
    try {
      MimeMessage message = mailSender.createMimeMessage();
      configureMessage(destinatary, subject, body, message);

      mailSender.send(message);
      log.info("Email sent to {}", destinatary);

    } catch (MessagingException e) {
      log.error("Error sending mail to {}: {}", destinatary, e.getMessage());
    }
  }

  private static void configureMessage(String destinatary, String subject, String body, MimeMessage message)
      throws MessagingException {
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setTo(destinatary);
    helper.setSubject(subject);
    helper.setText(body, true);
  }
}
