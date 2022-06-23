package com.udacity.jdnd.course3.critter.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;

@Service
@Transactional
public class ScheduleService {
    @Autowired ScheduleRepository scheduleRepository;
    
    public Schedule save(Schedule schedule){
        schedule = scheduleRepository.save(schedule);
        return schedule;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleByPet(Pet pet) {
        return scheduleRepository.findScheduleByPets(pet);
    }

    public List<Schedule> getScheduleByEmployee(Employee employee) {
        return scheduleRepository.findScheduleByEmployees(employee);
    }
}
