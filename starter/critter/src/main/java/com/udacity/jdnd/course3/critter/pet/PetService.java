package com.udacity.jdnd.course3.critter.pet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;

@Service
@Transactional
public class PetService {
    @Autowired PetRepository petRepository;
    @Autowired CustomerService customerService;

    public Pet save(Pet pet){
        pet = petRepository.save(pet);
        Customer customer = pet.getCustomer();
        customerService.addPetToCustomer(pet, customer);

        return pet;
    }

    public Pet getPetById(Long id){
        return petRepository.findPetById(id);
    }

    public List<Pet> getPetsByCustomer(Customer customer){
        return petRepository.findPetsByCustomer(customer);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }
}
