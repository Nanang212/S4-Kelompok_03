package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.dto.requests.EmailRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
@AllArgsConstructor
public class EmailService {
  private JavaMailSender sender;
  private TemplateEngine templateEngine;

  public EmailRequest sendMessage(EmailRequest emailRequest) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(emailRequest.getTo());
    message.setSubject(emailRequest.getSubject());
    message.setText(emailRequest.getBody());
    sender.send(message);
    return emailRequest;
  }

  public EmailRequest sendHtmlMessage(EmailRequest emailRequest) {
    Context context = new Context();
    context.setVariables(emailRequest.getProperties());
    String htmlContent = templateEngine.process(emailRequest.getBody(), context);
    try {
      MimeMessage message = sender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setTo(emailRequest.getTo());
      helper.setSubject(emailRequest.getSubject());
      helper.setText(htmlContent, true);
      sender.send(message);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return emailRequest;
  }

  public EmailRequest sendMessageWithAttachment(EmailRequest emailRequest) {
    try {
      MimeMessage message = sender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);

      helper.setTo(emailRequest.getTo());
      helper.setSubject(emailRequest.getSubject());
      helper.setText(emailRequest.getBody());

      FileSystemResource file = new FileSystemResource(
              new File(emailRequest.getAttachment())
      );

      helper.addAttachment(file.getFilename(), file);
      sender.send(message);
    } catch (Exception e) {
      System.out.println("Error = " + e.getMessage());
    }
    return emailRequest;
  }
}
