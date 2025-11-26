package org.example.services;

import org.example.dao.EmployeeDao;
import org.example.dto.CreatingEmployeeDto;
import org.example.entity.EmployeeEntity;

public class EmployeeService {
    public static void createEmployee(EmployeeEntity employee) {
        EmployeeDao.createEmployee(employee);
    }

    public static void saveEmployee(CreatingEmployeeDto createEmployeeDto) {
        EmployeeDao.saveEmployeeDto(createEmployeeDto);
    }

    public static void updateEmployee(EmployeeEntity employee) {
        EmployeeDao.updateEmployee(employee);
    }

    public static void deleteEmployee(EmployeeEntity employee) {
        EmployeeDao.deleteEmployee(employee);
    }

    public static void deleteEmployeeById(long id) {
        EmployeeDao.deleteEmployeeById(id);
    }

    public static EmployeeEntity getEmployeeById(long id) {
        return EmployeeDao.getEmployeeById(id);
    }
}
