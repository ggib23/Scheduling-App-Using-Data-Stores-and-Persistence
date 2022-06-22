package com.udacity.jdnd.course3.critter.schedule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>{

    // Find a schedule by id
    Schedule findScheduleById(Long id);

    // Get all schedule
    List<Schedule> findAll();

    // Get schedule by pet
    List<Schedule> findScheduleByPets(Pet pet);

    // Get schedule by employee
    List<Schedule> findScheduleByEmployees(Employee employee);
}
