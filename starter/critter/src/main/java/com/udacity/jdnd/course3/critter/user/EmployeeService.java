package com.udacity.jdnd.course3.critter.user;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Long save(Employee employee){
        return employeeRepository.save(employee).getId();
    }

    public Employee getEmployeeById(Long id){
        return employeeRepository.findEmployeeById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
    //     employeeRepository.getOne(employeeId).setDaysAvailable(daysAvailable);
    // }

    public List<Employee> getEmployeesForService(DayOfWeek daysAvailable, Set<EmployeeSkill> skills) {
        List<Employee> employeeList = employeeRepository.findDistinctEmployeeByDaysAvailableAndSkillsIn(daysAvailable, skills);
        List<Employee> skilledEmployee = new ArrayList<>();

        for(Employee employee : employeeList) {
            // Check if employee skills contain ALL the required skills
            if(employee.getSkills().containsAll(skills)) {
                skilledEmployee.add(employee);
            }
        }

        return skilledEmployee;
    }

    public void update(Employee employee) {
        if (employeeRepository.findEmployeeById(employee.getId()) != null) {
            Employee employeeUpdate = new Employee();
            employeeUpdate.setId(employee.getId());

            if (employee.getName() != null) {
                employeeUpdate.setName(employee.getName());
            }
            if (employee.getSkills() != null) {
                employeeUpdate.setSkills(employee.getSkills());
            }
            if (employee.getDaysAvailable() != null) {
                employeeUpdate.setDaysAvailable(employee.getDaysAvailable());
            }
            
            employeeRepository.save(employeeUpdate);
        }
    }
    
}
