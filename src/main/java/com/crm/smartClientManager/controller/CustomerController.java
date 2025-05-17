package com.crm.smartClientManager.controller;

import com.crm.smartClientManager.dto.customer.CustomerReqDto;
import com.crm.smartClientManager.dto.customer.CustomerUpdateReqDto;
import com.crm.smartClientManager.service.CustomerService;
import com.crm.smartClientManager.service.OtherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final OtherService otherService;

    @GetMapping("/get-countries")
    public ResponseEntity<?> getCountry(){
        log.info("Request for get all country");
        return otherService.getAllCountry();
    }

    @GetMapping(value = "/cities-by-country")
    public ResponseEntity<?> getCitiesByCountry(@RequestParam Long countryId){
        log.info("Request for get all cities by Country, countryId:{}",countryId);
        return otherService.getCitiesByCountry(countryId);
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerReqDto reqDto){
        log.info("Requst for save new Customer, customerNic:{}", reqDto.getNic());
        return customerService.registerCustomer(reqDto);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCustomer(@RequestParam String keyword){
        log.info("Request for search customer by keyword : {}",keyword);
        return customerService.searchCustomerByKeyword(keyword);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCustomer(){
        log.info("Request for get all customera");
        return customerService.getAllCustomers();
    }

    @GetMapping("/single")
    public ResponseEntity<?> getSingleCustomer(@RequestParam Long customerId){
        log.info("Request for get single customer, customerId:{}",customerId);
        return customerService.getSingleCustomer(customerId);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerUpdateReqDto reqDto){
        log.info("Request for update customer, customerId: {}",reqDto.getId());
        return customerService.updateCustomer(reqDto);
    }









}
