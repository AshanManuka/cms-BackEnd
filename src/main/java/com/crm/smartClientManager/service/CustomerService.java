package com.crm.smartClientManager.service;

import com.crm.smartClientManager.dto.customer.CustomerReqDto;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<?> registerCustomer(CustomerReqDto reqDto);

    ResponseEntity<?> searchCustomerByKeyword(String keyword);

    ResponseEntity<?> getAllCustomers();
}
