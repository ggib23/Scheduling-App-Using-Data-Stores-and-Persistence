package com.udacity.jdnd.course3.critter.user;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    // Check if an employee by this id exists
    Employee findEmployeeById(Long id);

    // Get all employees
    List<Employee> findAll();

    // Set availability of employee
    // void setDaysAvailable(Set<DayOfWeek> daysAvailable);

    // Find employee for service
    List<Employee> findDistinctEmployeeByDaysAvailableAndSkillsIn(DayOfWeek daysAvailable, Set<EmployeeSkill> skills);
    
}
