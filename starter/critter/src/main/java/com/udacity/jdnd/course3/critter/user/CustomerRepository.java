package com.udacity.jdnd.course3.critter.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

    // Retrieve Customer by pet
    // Customer findCustomerByPet(Pet pet);
    
    // Find a customer by id
    Customer findCustomerById(Long id);
    
    // Get all customers
    List<Customer> findAll();
}
