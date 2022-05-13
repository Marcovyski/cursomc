package com.example.coursemc.services;

import org.springframework.mail.SimpleMailMessage;

import com.example.coursemc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	

}
