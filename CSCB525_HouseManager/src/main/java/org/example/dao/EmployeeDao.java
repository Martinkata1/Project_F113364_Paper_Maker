package org.example.dao;

import jakarta.validation.Valid;
import org.example.configuration.SesionFactoryUtility;
import org.example.dto.CreatingEmployeeDto;
import org.example.entity.BuildingEntity;
import org.example.entity.CompanyEntity;
import org.example.entity.EmployeeEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
public class EmployeeDao {
    public static void createEmployee(@Valid EmployeeEntity employee) {
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
        }
    }

    public static void saveEmployeeDto(CreatingEmployeeDto createEmployeeDto) {
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            EmployeeEntity employee = new EmployeeEntity();
            employee.setName(createEmployeeDto.getName());

            session.save(employee);
            transaction.commit();
        }
    }

    public static void updateEmployee(EmployeeEntity employee) {
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(employee);
            transaction.commit();
        }
    }

    public static void deleteEmployee(EmployeeEntity employee) {
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(employee);
            transaction.commit();
        }
    }

    public static void deleteEmployeeById(long id) {
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            EmployeeEntity employee = session.get(EmployeeEntity.class, id);

            if (employee == null) {
                System.out.println("CEmployee with ID " + id + "does not exist.");
            } else {
                session.delete(employee);
                transaction.commit();
                System.out.println("Compnay with ID " + id + " was successfully deleted.");
            }
        }
    }

    public static EmployeeEntity getEmployeeById(long id) {
        EmployeeEntity employee;

        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            employee = session.get(EmployeeEntity.class, id);
            transaction.commit();
        }

        return employee;
    }

    public static double calculateTax(EmployeeEntity employee) {
        if(employee == null) {
            throw new IllegalArgumentException("Employee does not exist.");
        }

        double tax = 0.0f;

        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            employee = session.merge(employee);

            for(BuildingEntity building : employee.getBuildings()) {
                building = session.merge(building);
                tax += BuildingDao.calculateTax(building);
            }
        }

        return tax;
    }
}
