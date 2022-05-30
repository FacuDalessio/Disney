package com.disney.disney.repositories;

import com.disney.disney.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    public Customer findByEmail(String email);
}
