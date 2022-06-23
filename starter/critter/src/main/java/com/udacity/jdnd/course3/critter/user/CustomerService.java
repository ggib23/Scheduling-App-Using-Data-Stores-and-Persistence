package com.udacity.jdnd.course3.critter.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.pet.Pet;

@Service
@Transactional
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public Long save(Customer customer){
        return customerRepository.save(customer).getId();
    }

    public Customer getCustomerById(Long id){
        return customerRepository.findCustomerById(id);
    }

    // public Customer getCustomerByPet(Pet pet){
    //     return customerRepository.findCustomerByPet(pet);
    // }

    public void addPetToCustomer(Pet pet, Customer customer){
        List<Pet> pets = customer.getPets();
        if (pets != null) {
            pets.add(pet);
        } else {
            pets = new ArrayList<Pet>();
            pets.add(pet);
        }
        customer.setPets(pets);
        customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
}
