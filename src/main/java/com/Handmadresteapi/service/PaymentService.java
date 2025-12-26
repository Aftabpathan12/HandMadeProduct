package com.Handmadresteapi.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Handmadresteapi.model.Payment;
import com.Handmadresteapi.repository.PaymentRepository;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment initiatePayment(Payment payment) {
        payment.setStatus("Pending");
        payment.setPaymentDate(java.time.LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public Payment markPaymentSuccessful(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        payment.setStatus("Successful");
        return paymentRepository.save(payment);
    }
}
