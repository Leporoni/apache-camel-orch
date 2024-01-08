package com.leporonitech.payments.config;

import com.leporonitech.payments.model.Payment;

import java.time.format.DateTimeFormatter;

public class PaymentLineGenerator {

    public static String generatePaymentLine(Payment payment) {
        String productPriceStr = String.valueOf(payment.getProductPrice());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String generationDateStr = formatter.format(payment.getGenerationDate());
        String dueDateStr = formatter.format(payment.getDueDate());

        return String.join(".", payment.getProductName(), productPriceStr, generationDateStr, dueDateStr);
    }
}