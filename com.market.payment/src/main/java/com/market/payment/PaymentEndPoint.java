package com.market.payment;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEndPoint {

	public final PaymentService paymentService;

	@RabbitListener(queues = "${message.queue.payment}")
	public void receiveMessage(DeliveryMessage deliveryMessage) {
		log.info("PAYMENT MESSAGE: {}", deliveryMessage);
		paymentService.createPayment(deliveryMessage);
	}
}
