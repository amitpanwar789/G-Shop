package com.gshop.notificationservice.service;

import com.gshop.notificationservice.dto.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendOrderConfirmation(OrderPlacedEvent event) {
        log.info("Preparing to send email to: {}", event.getEmail());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@gshop.com");
            message.setTo(event.getEmail());
            message.setSubject("Order Confirmation - " + event.getOrderId());
            message.setText("Thank you for your order!\n\n" +
                    "Order ID: " + event.getOrderId() + "\n" +
                    "Total Amount: $" + event.getTotalAmount() + "\n\n" +
                    "We will notify you when it ships.");

            // In a real scenario with valid credentials, this would send the email.
            // For now, checks if properties are dummy or real could be done,
            // but we will let the try-catch handle connection failures if config is
            // invalid.
            mailSender.send(message);
            log.info("Email sent successfully to {}", event.getEmail());
        } catch (Exception e) {
            log.error("Failed to send email: {}", e.getMessage());
            // We intentionally swallow the error for this demo so the service doesn't crash
            // if the user hasn't provided real SMTP credentials.
            log.info("SIMULATION: Email would have been sent to {} with amount {}", event.getEmail(),
                    event.getTotalAmount());
        }
    }
}
