package com.market.payment;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

	@Value("${message.queue.err.product}")
	private String productErrorQueue;

	private final RabbitTemplate rabbitTemplate;

	public void createPayment(DeliveryMessage deliveryMessage) {
		Payment payment = Payment.builder()
			.paymentId(UUID.randomUUID())
			.userId(deliveryMessage.getUserId())
			.paymentAmount(deliveryMessage.getPayAmount())
			.payStatus("SUCCESS")
			.build();

		if(payment.getPaymentAmount() >= 10000) {
			log.error("Payment amount exceeds limit : {}", payment.getPaymentAmount());
			deliveryMessage.setErrorType("PAYMENT_LIMIT_EXCEEDED");
			this.rollbackPayment(deliveryMessage);
		}
	}

	public void rollbackPayment(DeliveryMessage deliveryMessage) {
		log.info("PAYMENT ROLLBACK!!");
		rabbitTemplate.convertAndSend(productErrorQueue, deliveryMessage);
	}
}
