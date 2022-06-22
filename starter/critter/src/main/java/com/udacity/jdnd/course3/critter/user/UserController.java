package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired private EmployeeService employeeService;
    @Autowired private CustomerService customerService;
    @Autowired private PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer convertCustomerDTO = convertToCustomer(customerDTO);
        Long customerId = customerService.save(convertCustomerDTO);

        Customer customer = customerService.getCustomerById(customerId);
        return convertCustomerToCustomerDTO(customer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        for (Customer customer : customerService.getAllCustomers()){
            customerDTOList.add(convertCustomerToCustomerDTO(customer));
        }

        return customerDTOList;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.getPetById(petId);
        Customer customer = pet.getCustomer();
        // Customer customer = customerService.getCustomerByPet(pet);

        return convertCustomerToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee convertEmployeeDTO = convertToEmployee(employeeDTO);
        Long employeeId = employeeService.save(convertEmployeeDTO);

        Employee employee = employeeService.getEmployeeById(employeeId);
        return convertEmployeeToEmployeeDTO(employee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);

        return convertEmployeeToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        
        employeeService.update(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        LocalDate date  = employeeDTO.getDate();
        DayOfWeek day = date.getDayOfWeek();
        Set<EmployeeSkill> skills = employeeDTO.getSkills();

        List<Employee> employeesAvailable = employeeService.getEmployeesForService(day, skills);
        for (Employee employee : employeesAvailable){
            employeeDTOList.add(convertEmployeeToEmployeeDTO(employee));
        }

        return employeeDTOList;
    }

    public EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        
        Long employeeId = employee.getId();
        employeeDTO.setId(employeeId);

        return employeeDTO;
    }

    public Employee convertToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        
        Long employeeDTOId = employeeDTO.getId();
        employee.setId(employeeDTOId);

        return employee;
    }

    public CustomerDTO convertCustomerToCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        List<Long> petIds = new ArrayList<>();
        if (customer.getPets() != null && customer.getPets().size() > 0){
            for (Pet pet : customer.getPets()){
                petIds.add(pet.getId());
            }
        }
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    public Customer convertToCustomer(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        List<Pet> pets = new ArrayList<>();
        if (customerDTO.getPetIds() != null && customerDTO.getPetIds().size() > 0){
            for (Long id : customerDTO.getPetIds()){
                Pet pet = petService.getPetById(id);
                pets.add(pet);
            }
        }
        customer.setPets(pets);
        return customer;
    }

}
