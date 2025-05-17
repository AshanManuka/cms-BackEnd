package com.crm.smartClientManager.service.impl;

import com.crm.smartClientManager.dto.CommonResponse;
import com.crm.smartClientManager.dto.country.CountryResDto;
import com.crm.smartClientManager.entity.Country;
import com.crm.smartClientManager.repository.CityRepository;
import com.crm.smartClientManager.repository.CountryRepository;
import com.crm.smartClientManager.service.OtherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class OtherServiceImpl implements OtherService {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;


    @Override
    public ResponseEntity<?> getAllCountry() {
        List<Country> countryList = countryRepository.findAll();

        List<CountryResDto> resList = countryList.stream()
                .map(country -> new CountryResDto(country.getId(), country.getName()))
                .toList();

        return ResponseEntity.ok(new CommonResponse<>(true, resList));
    }
}
