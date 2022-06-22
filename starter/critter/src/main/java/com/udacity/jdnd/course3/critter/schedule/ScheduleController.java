package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeService;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    
    @Autowired private ScheduleService scheduleService;
    @Autowired private EmployeeService employeeService;
    @Autowired private CustomerService customerService;
    @Autowired private PetService petService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule convertScheduleDTO = convertToSchedule(scheduleDTO);
        Schedule schedule = scheduleService.save(convertScheduleDTO);

        return convertScheduleToScheduleDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        for (Schedule schedule : scheduleService.getAllSchedules()){
            scheduleDTOList.add(convertScheduleToScheduleDTO(schedule));
        }

        return scheduleDTOList;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        List<Schedule> scheduleList = scheduleService.getScheduleByPet(pet);
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        
        for (Schedule schedule : scheduleList){
            scheduleDTOList.add(convertScheduleToScheduleDTO(schedule));
        }

        return scheduleDTOList;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        List<Schedule> scheduleList = scheduleService.getScheduleByEmployee(employee);
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();
        
        for (Schedule schedule : scheduleList){
            scheduleDTOList.add(convertScheduleToScheduleDTO(schedule));
        }

        return scheduleDTOList;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        List<Pet> pets = petService.getPetsByCustomer(customer);
        List<Schedule> resetList = new ArrayList<>();
        List<Schedule> scheduleList = new ArrayList<>();
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>();

        for (Pet pet : pets) {
            resetList = scheduleService.getScheduleByPet(pet);
            scheduleList.addAll(resetList);
        }

        for (Schedule schedule : scheduleList){
            scheduleDTOList.add(convertScheduleToScheduleDTO(schedule));
        }

        return scheduleDTOList;
    }

    public ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Employee> employeeList = schedule.getEmployees();
        List<Pet> petList = schedule.getPets();
        List<Long> employeeIds = new ArrayList<>();
        List<Long> petIds = new ArrayList<>();

        for (Employee employee : employeeList){
            Long employeeId = employee.getId();
            employeeIds.add(employeeId);
        }

        for (Pet pet : petList){
            Long petId = pet.getId();
            petIds.add(petId);
        }

        Long scheduleId = schedule.getId();
        scheduleDTO.setId(scheduleId);
        scheduleDTO.setEmployeeIds(employeeIds);
        scheduleDTO.setPetIds(petIds);

        return scheduleDTO;
    }

    public Schedule convertToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Long> petIds = scheduleDTO.getPetIds();
        List<Employee> employeeList = new ArrayList<>();
        List<Pet> petList = new ArrayList<>();

        for (Long employeeId : employeeIds){
            Employee employee = employeeService.getEmployeeById(employeeId);
            employeeList.add(employee);
        }

        for (Long petId : petIds){
            Pet pet = petService.getPetById(petId);
            petList.add(pet);
        }

        Long scheduleId = scheduleDTO.getId();
        schedule.setId(scheduleId);
        schedule.setEmployees(employeeList);
        schedule.setPets(petList);

        return schedule;
    }

    public EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        
        Long employeeId = employee.getId();
        employeeDTO.setId(employeeId);

        return employeeDTO;
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
}
