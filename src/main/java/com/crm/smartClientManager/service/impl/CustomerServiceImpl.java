package com.crm.smartClientManager.service.impl;

import com.crm.smartClientManager.dto.CommonResponse;
import com.crm.smartClientManager.dto.address.AddressReqDto;
import com.crm.smartClientManager.dto.address.AddressResDto;
import com.crm.smartClientManager.dto.city.CityResDto;
import com.crm.smartClientManager.dto.customer.CustomerBasicResDto;
import com.crm.smartClientManager.dto.customer.CustomerFullResDto;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final AddressRepository addressRepository;


    @Override
    public ResponseEntity<?> registerCustomer(CustomerReqDto reqDto) {

        List<Customer> familyMembers = new ArrayList<>();
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
                        .customer(regCustomer)
                        .build();

                addresses.add(address);
            }

            regCustomer.setAddresses(addresses);
        }

        if (reqDto.getMemberList() != null && !reqDto.getMemberList().isEmpty()) {
            familyMembers = customerRepository.findAllById(reqDto.getMemberList());
            regCustomer.setFamilyMembers(familyMembers);
        }

        Customer savedCustomer = customerRepository.save(regCustomer);

        for (Customer member : familyMembers) {
            List<Customer> tmpList = member.getFamilyMembers();
            if (tmpList == null) {
                tmpList = new ArrayList<>();
            }
            tmpList.add(savedCustomer);
            member.setFamilyMembers(tmpList);
            customerRepository.save(member);
        }

        return ResponseEntity.ok(new CommonResponse<>(true, "Custmer Saved Successfully..!"));

    }

    @Override
    public ResponseEntity<?> searchCustomerByKeyword(String keyword) {
        List<Customer> customerList = customerRepository.searchByKeyword(keyword);

        List<CustomerBasicResDto> resList = customerList.stream()
                .map(customer -> new CustomerBasicResDto(customer.getId(), customer.getName(), customer.getNic(), customer.getDob()))
                .toList();

        return ResponseEntity.ok(new CommonResponse<>(true, resList));
    }

    @Override
    public ResponseEntity<?> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();

        List<CustomerFullResDto> responseList = customerList.stream().map(customer -> {
            List<CustomerBasicResDto> memberList = customer.getFamilyMembers().stream()
                    .map(member -> new CustomerBasicResDto(
                            member.getId(),
                            member.getName(),
                            member.getNic(),
                            member.getDob()))
                    .toList();

            List<AddressResDto> addressList = customer.getAddresses().stream()
                    .map(address -> new AddressResDto(
                            address.getId(),
                            address.getLineOne(),
                            address.getLineTwo(),
                            address.getCity().getName(),
                            address.getCountry().getName()))
                    .toList();

            System.out.println(addressList);

            List<String> mobileNumbers = customer.getMobileNumber();

            return new CustomerFullResDto(
                    customer.getName(),
                    customer.getDob(),
                    customer.getNic(),
                    mobileNumbers,
                    memberList,
                    addressList
            );
        }).toList();

        return ResponseEntity.ok(new CommonResponse<>(true, responseList));

    }

}
