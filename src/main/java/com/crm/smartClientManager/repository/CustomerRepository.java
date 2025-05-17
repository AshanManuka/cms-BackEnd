package com.crm.smartClientManager.repository;

import com.crm.smartClientManager.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT c FROM Customer c WHERE c.nic = ?1")
    Customer findByNic(String nic);
}
