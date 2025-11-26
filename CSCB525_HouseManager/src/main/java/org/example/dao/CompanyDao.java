package org.example.dao;

import jakarta.validation.Valid;
import org.example.configuration.SesionFactoryUtility;
import org.example.dto.CreatingCompanyDto;
import org.example.entity.ApartamentEntity;
import org.example.entity.BuildingEntity;
import org.example.entity.CompanyEntity;
import org.example.entity.EmployeeEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.ObjectStreamException;
import java.time.LocalDate;
import java.util.*;
public class CompanyDao {
    public static void createCompany(@Valid CompanyEntity company) {
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(company);
            transaction.commit();
        }
    }

    public static void saveCompanyDto(CreatingCompanyDto createCompanyDto) {
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            CompanyEntity company = new CompanyEntity();

            company.setName(createCompanyDto.getName());
            company.setIncome(createCompanyDto.getIncome());

            session.save(company);
            transaction.commit();
        }
    }

    public static void updateCompany(CompanyEntity company) {
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(company);
            transaction.commit();
        }
    }

    public static void deleteCompany(CompanyEntity company) {
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(company);
            transaction.commit();
        }
    }

    public static void deleteCompanyById(long id) {
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            CompanyEntity company = session.get(CompanyEntity.class, id);

            if (company == null) {
                System.out.println("Company with ID " + id + " does not exist.");
            } else {
                session.delete(company);
                transaction.commit();
                System.out.println("Company with ID " + id + " was successfully deleted.");
            }
        }
    }

    public static CompanyEntity getCompanyById(long id) {
        CompanyEntity company;
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            company = session.get(CompanyEntity.class, id);
            transaction.commit();
        }
        return company;
    }

    public static List<CompanyEntity> getCompanies() {
        List<CompanyEntity> companies;
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            companies = session
                    .createQuery("Select c From Company c", CompanyEntity.class)
                    .getResultList();
            transaction.commit();
        }
        return companies;
    }

    public static void hireEmployee(EmployeeEntity employee, CompanyEntity company) {
        if(employee == null) {
            throw new IllegalArgumentException("Employee does not exist.");
        }

        if(company == null) {
            throw new IllegalArgumentException("Company does not exist.");
        }

        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            employee = session.merge(employee);
            company = session.merge(company);

            if (company.getEmployees().contains(employee)) {
                throw new IllegalStateException("Employee already works for this company.");
            }

            if(employee.getCompany() != null && employee.getCompany() != company) {
                throw new IllegalStateException("Employee works for another company.");
            }

            employee.setCompany(company);
            employee.setHireDate(LocalDate.now());

            company.getEmployees().add(employee);

            session.saveOrUpdate(employee);
            session.saveOrUpdate(company);

            transaction.commit();
        }
    }

    public static void serveBuilding(BuildingEntity building, CompanyEntity company) {
        if(building == null) {
            throw new IllegalArgumentException("Building does not exist.");
        }

        if(company == null) {
            throw new IllegalArgumentException("Company does not exist.");
        }

        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            building = session.merge(building);
            company = session.merge(company);

            EmployeeEntity employee = session.createQuery(
                            "SELECT e FROM Employee e LEFT JOIN e.buildings b " +
                                    "WHERE e.company = :company " +
                                    "GROUP BY e " +
                                    "ORDER BY COUNT(b) ASC"
                            , EmployeeEntity.class)
                    .setParameter("company", company)
                    .setMaxResults(1)
                    .getSingleResult();

            employee.getBuildings().add(building);
            building.setEmployee(employee);

            session.saveOrUpdate(employee);
            session.saveOrUpdate(building);

            transaction.commit();

            System.out.println("The building with ID " + building.getId() + " was served by employee with ID " + employee.getId() + " from " + company.getId());
        }
    }

    public static void fireEmployee(EmployeeEntity employee, CompanyEntity company) {
        if(employee == null) {
            throw new IllegalArgumentException("Employee does not exist.");
        }

        if(company == null) {
            throw new IllegalArgumentException("Company does not exist");
        }

        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            employee = session.merge(employee);
            company = session.merge(company);

            if(!company.getEmployees().contains(employee)) {
                throw new IllegalStateException("Employee is not part of the specified company.");
            }

            Set<BuildingEntity> buildings = new HashSet<>(employee.getBuildings());

            List<EmployeeEntity> employees = session.createQuery(
                            "FROM Employee e WHERE e.company = :company AND e != :employee",
                            EmployeeEntity.class)
                    .setParameter("company", company)
                    .setParameter("employee", employee)
                    .getResultList();

            if(employees.isEmpty()) {
                throw new IllegalStateException("No employees are available for redistribution");
            }

            int currentEmployeeIndex = 0;

            for(BuildingEntity building : buildings) {
                EmployeeEntity newEmployee = employees.get(currentEmployeeIndex);
                newEmployee = session.merge(newEmployee);
                building = session.merge(building);

                employee.getBuildings().remove(building);
                newEmployee.getBuildings().add(building);
                building.setEmployee(newEmployee);

                session.saveOrUpdate(newEmployee);
                session.saveOrUpdate(building);

                currentEmployeeIndex = (currentEmployeeIndex + 1) % employees.size();
            }

            employee.setCompany(null);
            employee.setHireDate(null);

            session.saveOrUpdate(employee);
            session.saveOrUpdate(company);

            transaction.commit();
        }
    }

    public static double calculateTaxPerEmployee(CompanyEntity company, EmployeeEntity employee) {
        if(company == null) {
            throw  new IllegalArgumentException("Company does not exist");
        }

        if(employee == null) {
            throw new IllegalArgumentException("Employee");
        }

        double tax = 0.0f;

        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            employee = session.merge(employee);
            company = session.merge(company);

            System.out.println(employee);
            System.out.println(company);

            if(employee.getCompany() != company) {
                throw new IllegalStateException("The employee is not from that company.");
            }

            tax = EmployeeDao.calculateTax(employee);
        }

        return tax;
    }

    public static List<CompanyEntity> getCompaniesSortedByIncome(boolean order) {
        List<CompanyEntity> companies;
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            companies = session.createQuery(
                    "FROM Company c ORDER BY c.income " + (order ? "DESC" : "ASC"),
                    CompanyEntity.class
            ).getResultList();

            transaction.commit();
        }
        return companies;
    }

    public static List<EmployeeEntity> getEmployeesByCompanySortedByName(CompanyEntity company, boolean order) {
        if (company == null) {
            throw new IllegalArgumentException("Company cannot be null.");
        }

        List<EmployeeEntity> employees;
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            employees = session.createQuery(
                    "SELECT e FROM Employee e WHERE e.company = :company ORDER BY e.name " + (order ? "ASC" : "DESC"),
                    EmployeeEntity.class
            ).setParameter("company", company).getResultList();

            transaction.commit();
        }
        return employees;
    }

    public static List<EmployeeEntity> getEmployeesByCompanySortedByBuildingsCount(CompanyEntity company, boolean order) {
        if (company == null) {
            throw new IllegalArgumentException("Company cannot be null.");
        }

        List<EmployeeEntity> employees;
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            employees = session.createQuery(
                    "SELECT e FROM Employee e WHERE e.company = :company " +
                            "ORDER BY SIZE(e.buildings) " + (order ? "DESC" : "ASC"),
                    EmployeeEntity.class
            ).setParameter("company", company).getResultList();

            transaction.commit();
        }
        return employees;
    }

    public static Map<EmployeeEntity, List<BuildingEntity>> getBuildingsByEmployeesInCompany(CompanyEntity company) {
        if (company == null) {
            throw new IllegalArgumentException("Company cannot be null.");
        }

        Map<EmployeeEntity, List<BuildingEntity>> employeeBuildingMap = new HashMap<>();

        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            List<EmployeeEntity> employees = session.createQuery(
                    "SELECT e FROM Employee e " +
                            "LEFT JOIN FETCH e.buildings b " +
                            "WHERE e.company = :company",
                    EmployeeEntity.class
            ).setParameter("company", company).getResultList();

            for (EmployeeEntity employee : employees) {
                List<BuildingEntity> buildings = new ArrayList<>(employee.getBuildings());
                employeeBuildingMap.put(employee, buildings);
            }

            transaction.commit();
        }
        return employeeBuildingMap;
    }

    public static void detailedTaxesInformation(List<CompanyEntity> companies) {
        if (companies == null || companies.isEmpty()) {
            throw new IllegalArgumentException("The company list cannot be null or empty.");
        }

        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Map<Long, Map<Long, Map<String, Double>>> companyPayments = new HashMap<>();

            for (CompanyEntity company : companies) {
                Map<Long, Map<String, Double>> employeePayments = new HashMap<>();
                companyPayments.put(company.getId(), employeePayments);

                List<EmployeeEntity> employees = session.createQuery(
                                "SELECT e FROM Employee e WHERE e.company = :company", EmployeeEntity.class)
                        .setParameter("company", company)
                        .getResultList();

                for (EmployeeEntity employee : employees) {
                    Map<String, Double> buildingPayments = new HashMap<>();
                    employeePayments.put(employee.getId(), buildingPayments);

                    List<BuildingEntity> buildings = session.createQuery(
                                    "SELECT b FROM Building b WHERE b.employee = :employee", BuildingEntity.class)
                            .setParameter("employee", employee)
                            .getResultList();

                    for (BuildingEntity building : buildings) {
                        double buildingTotalPayments = 0;

                        List<ApartamentEntity> apartments = session.createQuery(
                                        "SELECT a FROM Apartment a WHERE a.building = :building", ApartamentEntity.class)
                                .setParameter("building", building)
                                .getResultList();

                        for (ApartamentEntity apartment : apartments) {
                            if (apartment.getMonthlyTax() > 0) {
                                buildingTotalPayments += apartment.getMonthlyTax();
                            }
                        }

                        buildingPayments.put(building.getAddress(), buildingTotalPayments);
                    }
                }
            }

            for (Map.Entry<Long, Map<Long, Map<String, Double>>> companyEntry : companyPayments.entrySet()) {
                CompanyEntity company = session.get(CompanyEntity.class, companyEntry.getKey());
                System.out.println("Company: " + company.getName());

                for (Map.Entry<Long, Map<String, Double>> employeeEntry : companyEntry.getValue().entrySet()) {
                    EmployeeEntity employee = session.get(EmployeeEntity.class, employeeEntry.getKey());
                    System.out.println("\tEmployee: " + employee.getName());

                    double totalEmployeePayments = 0;
                    for (Map.Entry<String, Double> buildingEntry : employeeEntry.getValue().entrySet()) {
                        String buildingAddress = buildingEntry.getKey();
                        double buildingPayments = buildingEntry.getValue();

                        System.out.println("\t\tBuilding: " + buildingAddress + ", Total Payments: " + buildingPayments);
                        totalEmployeePayments += buildingPayments;
                    }

                    System.out.println("\tTotal Payments for Employee: " + totalEmployeePayments);
                }
            }
        }
    }

    public static void detailedPaidTaxesInformation(List<CompanyEntity> companies) {
        if (companies == null || companies.isEmpty()) {
            throw new IllegalArgumentException("The company list cannot be null or empty.");
        }

        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Map<Long, Map<Long, Map<String, Double>>> companyPayments = new HashMap<>();


            for (CompanyEntity company : companies) {
                Map<Long, Map<String, Double>> employeePayments = new HashMap<>();
                companyPayments.put(company.getId(), employeePayments);

                List<EmployeeEntity> employees = session.createQuery(
                                "SELECT e FROM Employee e WHERE e.company = :company", EmployeeEntity.class)
                        .setParameter("company", company)
                        .getResultList();

                for (EmployeeEntity employee : employees) {
                    Map<String, Double> buildingPayments = new HashMap<>();
                    employeePayments.put(employee.getId(), buildingPayments);

                    List<BuildingEntity> buildings = session.createQuery(
                                    "SELECT b FROM Building b WHERE b.employee = :employee", BuildingEntity.class)
                            .setParameter("employee", employee)
                            .getResultList();

                    for (BuildingEntity building : buildings) {
                        double buildingPaidPayments = 0;

                        List<ApartamentEntity> apartments = session.createQuery(
                                        "SELECT a FROM Apartment a WHERE a.building = :building", ApartamentEntity.class)
                                .setParameter("building", building)
                                .getResultList();

                        for (ApartamentEntity apartment : apartments) {
                            if (apartment.getMonthlyTax() > 0 && apartment.isTaxPaid()) {
                                buildingPaidPayments += apartment.getMonthlyTax();
                            }
                        }
                        buildingPayments.put(building.getAddress(), buildingPaidPayments);
                    }
                }
            }

            for (Map.Entry<Long, Map<Long, Map<String, Double>>> companyEntry : companyPayments.entrySet()) {
                CompanyEntity company = session.get(CompanyEntity.class, companyEntry.getKey());
                System.out.println("Company: " + company.getName());

                for (Map.Entry<Long, Map<String, Double>> employeeEntry : companyEntry.getValue().entrySet()) {
                    EmployeeEntity employee = session.get(EmployeeEntity.class, employeeEntry.getKey());
                    System.out.println("\tEmployee: " + employee.getName());

                    double totalEmployeePaidPayments = 0;
                    for (Map.Entry<String, Double> buildingEntry : employeeEntry.getValue().entrySet()) {
                        String buildingAddress = buildingEntry.getKey();
                        double buildingPaidPayments = buildingEntry.getValue();

                        System.out.println("\t\tBuilding: " + buildingAddress + ", Paid Payments: " + buildingPaidPayments);
                        totalEmployeePaidPayments += buildingPaidPayments;
                    }

                    System.out.println("\tTotal Paid Payments for Employee: " + totalEmployeePaidPayments);
                }
            }
        }
    }
}
