package com.crm.smartClientManager.service.impl;

import com.crm.smartClientManager.dto.CommonResponse;
import com.crm.smartClientManager.dto.customer.CustomerReqDto;
import com.crm.smartClientManager.dto.customer.CustomerUpdateReqDto;
import com.crm.smartClientManager.entity.Customer;
import com.crm.smartClientManager.repository.AddressRepository;
import com.crm.smartClientManager.repository.CityRepository;
import com.crm.smartClientManager.repository.CountryRepository;
import com.crm.smartClientManager.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterCustomer_Success() {
        CustomerReqDto reqDto = new CustomerReqDto();
        reqDto.setName("John Doe");
        reqDto.setDob(new Date());
        reqDto.setNic("123456789V");

        when(customerRepository.findByNic("123456789V")).thenReturn(null);

        ResponseEntity<?> response = customerService.registerCustomer(reqDto);

        assertNotNull(response);
        assertTrue(response.getBody().toString().contains("Custmer Saved Successfully..!"));
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void testRegisterCustomer_AlreadyExists() {
        CustomerReqDto reqDto = new CustomerReqDto();
        reqDto.setNic("NIC001");

        when(customerRepository.findByNic("NIC001")).thenReturn(new Customer());

        ResponseEntity<?> response = customerService.registerCustomer(reqDto);

        CommonResponse<?> body = (CommonResponse<?>) response.getBody();
        assertFalse(body.isSuccess());
        assertEquals("Customer Nic already Exists..!", body.getMessage());
    }

    @Test
    public void testSearchCustomerByKeyword() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Jane Doe");
        customer.setNic("NIC002");
        customer.setDob(new Date());

        when(customerRepository.searchByKeyword("Jane")).thenReturn(List.of(customer));

        ResponseEntity<?> response = customerService.searchCustomerByKeyword("Jane");

        CommonResponse<?> body = (CommonResponse<?>) response.getBody();
        assertTrue(body.isSuccess());
        List<?> resultList = (List<?>) body.getBody();
        assertEquals(1, resultList.size());
    }

    @Test
    public void testGetAllCustomers() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Test User");
        customer.setNic("NIC003");
        customer.setDob(new Date());
        customer.setFamilyMembers(new ArrayList<>());
        customer.setAddresses(new ArrayList<>());
        customer.setMobileNumber(List.of("111"));

        when(customerRepository.findAll()).thenReturn(List.of(customer));

        ResponseEntity<?> response = customerService.getAllCustomers();

        CommonResponse<?> body = (CommonResponse<?>) response.getBody();
        assertTrue(body.isSuccess());
        List<?> list = (List<?>) body.getBody();
        assertEquals(1, list.size());
    }

    @Test
    public void testGetSingleCustomer_Found() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Single User");
        customer.setDob(new Date());
        customer.setNic("NIC004");
        customer.setFamilyMembers(new ArrayList<>());
        customer.setAddresses(new ArrayList<>());
        customer.setMobileNumber(List.of("999"));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        ResponseEntity<?> response = customerService.getSingleCustomer(1L);

        CommonResponse<?> body = (CommonResponse<?>) response.getBody();
        assertTrue(body.isSuccess());
    }

    @Test
    public void testGetSingleCustomer_NotFound() {
        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = customerService.getSingleCustomer(999L);

        CommonResponse<?> body = (CommonResponse<?>) response.getBody();
        assertFalse(body.isSuccess());
        assertEquals("Customer not found", body.getMessage());
    }

    @Test
    public void testUpdateCustomer_NicExists() {
        CustomerUpdateReqDto dto = new CustomerUpdateReqDto();
        dto.setId(2L);
        dto.setNic("NIC999");

        Customer otherCustomer = new Customer();
        otherCustomer.setId(1L);

        when(customerRepository.findByNic("NIC999")).thenReturn(otherCustomer);

        ResponseEntity<?> response = customerService.updateCustomer(dto);

        CommonResponse<?> body = (CommonResponse<?>) response.getBody();
        assertFalse(body.isSuccess());
        assertEquals("New Nic already Exists..!", body.getMessage());
    }

    @Test
    public void testUpdateCustomer_NotFound() {
        CustomerUpdateReqDto dto = new CustomerUpdateReqDto();
        dto.setId(1L);
        dto.setNic("NIC101");

        when(customerRepository.findByNic("NIC101")).thenReturn(null);
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = customerService.updateCustomer(dto);

        CommonResponse<?> body = (CommonResponse<?>) response.getBody();
        assertFalse(body.isSuccess());
        assertEquals("Customer Not found..!", body.getMessage());
    }









}
