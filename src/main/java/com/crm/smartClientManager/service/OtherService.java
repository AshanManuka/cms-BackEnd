package com.crm.smartClientManager.service;

import org.springframework.http.ResponseEntity;

public interface OtherService {
    ResponseEntity<?> getAllCountry();

    ResponseEntity<?> getCitiesByCountry(Long countryId);
}
