package com.example.service;

import com.example.dtos.PaymentRequest;
import com.example.dtos.PaymentResponse;
import com.example.dtos.PaymentStatus;

public interface KonnectPaymentService {

	public PaymentResponse initiatePayment(PaymentRequest paymentRequest);
	 public PaymentStatus getPaymentStatus(String paymentId);


}
