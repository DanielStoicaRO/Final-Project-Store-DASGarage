package sda.dasgarage.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    public void sendSimpleMessage(String to, String subject, String text) throws IOException {
        System.out.println("=== Trimit email ===");
        System.out.println("Destinatar: " + to);
        System.out.println("Subiect: " + subject);
        System.out.println("Continut:\n" + text);

        Email from = new Email("Stoica.Daniel@icloud.com");
        Email toEmail = new Email(to);
        Content content = new Content("text/plain", text);
        Mail mail = new Mail(from, subject, toEmail, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            System.out.println("STATUS: " + response.getStatusCode());
            System.out.println("BODY: " + response.getBody());
            System.out.println("HEADERS: " + response.getHeaders());
        } catch (IOException ex) {
            System.out.println("EROARE la trimiterea emailului:");
            ex.printStackTrace();
            throw ex;
        }
    }
}