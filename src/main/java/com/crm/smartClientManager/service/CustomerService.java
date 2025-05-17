package com.crm.smartClientManager.service;

import com.crm.smartClientManager.dto.customer.CustomerReqDto;
import com.crm.smartClientManager.dto.customer.CustomerUpdateReqDto;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    ResponseEntity<?> registerCustomer(CustomerReqDto reqDto);

    ResponseEntity<?> searchCustomerByKeyword(String keyword);

    ResponseEntity<?> getAllCustomers();

    ResponseEntity<?> updateCustomer(CustomerUpdateReqDto reqDto);

    ResponseEntity<?> getSingleCustomer(Long customerId);
}
