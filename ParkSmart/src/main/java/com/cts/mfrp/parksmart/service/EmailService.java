package com.cts.mfrp.parksmart.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailService {

    private final SendGrid sendGrid =
        new SendGrid(System.getenv("SENDGRID_API_KEY"));

    public void send(String to, String subject, String body) {

        Email from = new Email("noreply@parksmart.com");
        Email toEmail = new Email(to);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, toEmail, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        try {
			request.setBody(mail.build());
		} catch (IOException e) {
			e.printStackTrace();
		}

        try {
            sendGrid.api(request);
        } catch (Exception e) {
            throw new RuntimeException("Email failed", e);
        }
    }
}
