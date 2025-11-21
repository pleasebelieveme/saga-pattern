package com.market.payment;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

	public void createPayment(DeliveryMessage deliveryMessage) {
		Payment payment = Payment.builder()
			.paymentId(UUID.randomUUID())
			.userId(deliveryMessage.getUserId())
			.paymentAmount(deliveryMessage.getPayAmount())
			.payStatus("SUCCESS")
			.build();

		if(payment.getPaymentAmount() >= 10000) {
			log.error("Payment amount exceeds limit : {}", payment.getPaymentAmount());
		}
	}

}
