package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.configuration.KonnectConfig;
import com.example.dtos.PaymentRequest;
import com.example.dtos.PaymentResponse;
import com.example.dtos.PaymentStatus;
@Service
public class KonnectPaymentServiceImpl implements KonnectPaymentService{
	 @Autowired
	    private KonnectConfig konnectConfig;


	 private final RestTemplate restTemplate;


	    public KonnectPaymentServiceImpl(RestTemplate restTemplate) {
	        this.restTemplate = restTemplate;
	    }
	@Override
	public PaymentResponse initiatePayment(PaymentRequest paymentRequest) {
		String url = konnectConfig.getApiUrl() + "/payments/init-payment";
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", konnectConfig.getApiKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentRequest> request = new HttpEntity<>(paymentRequest, headers);
        ResponseEntity<PaymentResponse> response = restTemplate.postForEntity(url, request, PaymentResponse.class);

        return response.getBody();
	}

	@Override
	public PaymentStatus getPaymentStatus(String paymentId) {
		 String url = konnectConfig.getApiUrl() + "/payments/" + paymentId;
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("x-api-key", konnectConfig.getApiKey());

	        HttpEntity<String> request = new HttpEntity<>(headers);
	        ResponseEntity<PaymentStatus> response = restTemplate.exchange(url, HttpMethod.GET, request, PaymentStatus.class);

	        return response.getBody();
	}

}
