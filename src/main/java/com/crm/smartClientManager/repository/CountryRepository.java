package com.crm.smartClientManager.repository;

import com.crm.smartClientManager.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
