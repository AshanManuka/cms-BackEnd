package com.crm.smartClientManager.repository;

import com.crm.smartClientManager.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
