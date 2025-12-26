package com.Handmadresteapi.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Handmadresteapi.model.Payment;
import com.Handmadresteapi.repository.PaymentRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {
	
	@Autowired
	private PaymentRepository payrepoobj;
	
	@PostMapping("/finalpayment")
	public String startpayment(@RequestParam String orderID,@RequestParam String username,@RequestParam String OrderAmount,Model model,HttpSession Session)
	{
		Payment payment = new Payment();
		payment.setAmount(Double.parseDouble(OrderAmount));
		payment.setOrderId(Long.parseLong(orderID));
		payment.setModeofPayment("Credit Card");
		payment.setPaymentDate(LocalDateTime.now());
		payment.setStatus("Completed");
		
		payrepoobj.save(payment);
		model.addAttribute(payment);
		return "payment-success";
	}
	
	@GetMapping("/pay")
	public String paypage() 
	{
	return "Payment";	
	}

    }
