package com.anisehealth.matching.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PatientRequestTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testPaymentMethodDeserialization() throws Exception {
        // Test case-sensitive values
        String jsonWithExactCase = """
            {"paymentMethod": "Insurance"}
            """;
        PatientRequest request = objectMapper.readValue(jsonWithExactCase, PatientRequest.class);
        assertEquals(PatientRequest.PaymentMethod.INSURANCE, request.getPaymentMethod());

        // Test different case
        String jsonWithDifferentCase = """
            {"paymentMethod": "INSURANCE"}
            """;
        request = objectMapper.readValue(jsonWithDifferentCase, PatientRequest.class);
        assertEquals(PatientRequest.PaymentMethod.INSURANCE, request.getPaymentMethod());

        // Test self-pay
        String jsonSelfPay = """
            {"paymentMethod": "Self-pay"}
            """;
        request = objectMapper.readValue(jsonSelfPay, PatientRequest.class);
        assertEquals(PatientRequest.PaymentMethod.SELF_PAY, request.getPaymentMethod());
    }

    @Test
    public void testPaymentMethodInvalidValue() {
        String jsonInvalid = """
            {"paymentMethod": "InvalidValue"}
            """;
        assertThrows(Exception.class, () -> 
            objectMapper.readValue(jsonInvalid, PatientRequest.class)
        );
    }
} 