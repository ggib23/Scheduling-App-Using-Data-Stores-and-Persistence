package com.udacity.jdnd.course3.critter.pet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.user.Customer;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long>{

    // Retrieve pet by customer
    List<Pet> findPetsByCustomer(Customer customer);

    // Find a pet by id
    Pet findPetById(Long id);

    // Get all pets
    List<Pet> findAll();

}
