package com.crm.smartClientManager.repository;

import com.crm.smartClientManager.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT c FROM City c WHERE c.country.id = ?1")
    List<City> getCitiesByCountry(Long countryId);
}
