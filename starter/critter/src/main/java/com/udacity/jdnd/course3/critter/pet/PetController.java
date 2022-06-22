package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    
    @Autowired private CustomerService customerService;
    @Autowired private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet convertPetDTO = convertToPet(petDTO);
        Pet pet = petService.save(convertPetDTO);

        return convertPetToPetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);

        return convertPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> petDTOList = new ArrayList<>();
        for (Pet pet : petService.getAllPets()){
            petDTOList.add(convertPetToPetDTO(pet));
        }

        return petDTOList;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        Customer customer = customerService.getCustomerById(ownerId);
        List<Pet> petList = petService.getPetsByCustomer(customer);
        List<PetDTO> petDTOList = new ArrayList<>();
        for (Pet pet : petList){
            petDTOList.add(convertPetToPetDTO(pet));
        }

        return petDTOList;
    }

    public PetDTO convertPetToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);

        Customer customer = pet.getCustomer();
        Long ownerId = customer.getId();
        Long petId = pet.getId();

        petDTO.setOwnerId(ownerId);
        petDTO.setId(petId);

        return petDTO;
    }

    public Pet convertToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);

        Long petId = petDTO.getId();
        Long customerId = petDTO.getOwnerId();
        Customer customer = customerService.getCustomerById(customerId);

        pet.setId(petId);
        pet.setCustomer(customer);

        return pet;
    }
}
