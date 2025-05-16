package com.nmpc.kindergarten.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.nmpc.kindergarten.repository.PasswordResetRepository;

@Service
public class EmailService {
		
	@Autowired
	private JavaMailSender javaMailSender;
	
	public ResponseEntity<Map<String, String>> sendEmail(String toEmail,String subject,String body){
		
		Map<String, String> response = new HashMap<>();
		
		
		SimpleMailMessage mailMessage=new SimpleMailMessage();
		mailMessage.setFrom("nanhemunneplaycenter@gmail.com");
		mailMessage.setTo(toEmail);
		mailMessage.setSubject(subject);
		mailMessage.setText(body);

		javaMailSender.send(mailMessage);
		
		System.out.println("Reset email sent to: " + toEmail);
		response.put("message", "Reset link sent successfully!");
		return ResponseEntity.ok(response);
		
	}

}
