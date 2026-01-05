package com.gshop.notificationservice.consumer;

import com.gshop.notificationservice.config.RabbitMQConfig;
import com.gshop.notificationservice.dto.OrderPlacedEvent;
import com.gshop.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void consumeOrderPlacedEvent(OrderPlacedEvent event) {
        log.info("Received OrderPlacedEvent from queue: {}", event);
        emailService.sendOrderConfirmation(event);
    }
}
