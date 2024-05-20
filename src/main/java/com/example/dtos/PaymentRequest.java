package com.example.dtos;

import java.util.List;

import lombok.Data;
@Data
public class PaymentRequest {
	 private String receiverWalletId;
	    private String token;
	    private int amount;
	    private String type;
	    private String description;
	    private List<String> acceptedPaymentMethods;
	    private int lifespan;
	    private boolean checkoutForm;
	    private boolean addPaymentFeesToAmount;
	    private String firstName;
	    private String lastName;
	    private String phoneNumber;
	    private String email;
	    private String orderId;
	    private String webhook;
	    private boolean silentWebhook;
	    private String successUrl;
	    private String failUrl;
	    private String theme;
}
