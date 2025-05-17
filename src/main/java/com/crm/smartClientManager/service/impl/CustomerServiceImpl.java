package com.crm.smartClientManager.service.impl;

import com.crm.smartClientManager.dto.CommonResponse;
import com.crm.smartClientManager.dto.address.AddressReqDto;
import com.crm.smartClientManager.dto.customer.CustomerReqDto;
import com.crm.smartClientManager.entity.Address;
import com.crm.smartClientManager.entity.City;
import com.crm.smartClientManager.entity.Country;
import com.crm.smartClientManager.entity.Customer;
import com.crm.smartClientManager.repository.AddressRepository;
import com.crm.smartClientManager.repository.CityRepository;
import com.crm.smartClientManager.repository.CountryRepository;
import com.crm.smartClientManager.repository.CustomerRepository;
import com.crm.smartClientManager.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final AddressRepository addressRepository;


    @Override
    public ResponseEntity<?> registerCustomer(CustomerReqDto reqDto) {

        Customer customer = customerRepository.findByNic(reqDto.getNic());
        if (customer != null) {
            log.info("Customer already exists");
            return ResponseEntity.ok(new CommonResponse<>(false, "Customer Nic already Exists..!"));
        }

        Customer regCustomer = Customer.builder()
                .name(reqDto.getName())
                .dob(reqDto.getDob())
                .nic(reqDto.getNic())
                .createdDate(new Date())
                .updatedDate(new Date())
                .build();

        if (reqDto.getMobileNumber() != null) {
            regCustomer.setMobileNumber(reqDto.getMobileNumber());
        }

        if (reqDto.getAddressList() != null) {
            List<Address> addresses = new ArrayList<>();
            for (AddressReqDto addDto : reqDto.getAddressList()) {
                Optional<City> city = cityRepository.findById(addDto.getCityId());

                if (city.isEmpty()) {
                    return ResponseEntity.ok(new CommonResponse<>(false, "Country or City Not Found..!"));
                }

                Address address = Address.builder()
                        .lineOne(addDto.getLineOne())
                        .lineTwo(addDto.getLineTwo())
                        .city(city.get())
                        .country(city.get().getCountry())
                        .build();

                addresses.add(address);
            }

            regCustomer.setAddresses(addresses);
        }

        if (reqDto.getMemberList() != null && !reqDto.getMemberList().isEmpty()) {
            List<Customer> familyMembers = customerRepository.findAllById(reqDto.getMemberList());
            regCustomer.setFamilyMembers(familyMembers);
        }

        customerRepository.save(regCustomer);

        return ResponseEntity.ok(new CommonResponse<>(true, "Custmer Saved Successfully..!"));

    }

}
