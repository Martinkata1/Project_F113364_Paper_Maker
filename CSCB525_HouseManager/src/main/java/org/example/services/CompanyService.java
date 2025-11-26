package org.example.services;

import org.example.dao.CompanyDao;
import org.example.dto.CreatingCompanyDto;
import org.example.entity.BuildingEntity;
import org.example.entity.CompanyEntity;
import org.example.entity.EmployeeEntity;

import java.util.List;
import java.util.Map;
public class CompanyService {
    public static void createCompany(CompanyEntity company) {
        CompanyDao.createCompany(company);
    }

    public static void saveCompany(CreatingCompanyDto createCompanyDto) {
        CompanyDao.saveCompanyDto(createCompanyDto);
    }

    public static void updateCompany(CompanyEntity company) {
        CompanyDao.updateCompany(company);
    }

    public static void deleteCompany(CompanyEntity company) {
        CompanyDao.deleteCompany(company);
    }

    public static void deleteCompanyById(long id) {
        CompanyDao.deleteCompanyById(id);
    }

    public static CompanyEntity getCompanyById(long id) {
        return CompanyDao.getCompanyById(id);
    }

    public static List<CompanyEntity> getAllCompanies() {
        return CompanyDao.getCompanies();
    }

    public static void hireEmployee(EmployeeEntity employee, CompanyEntity company) {
        CompanyDao.hireEmployee(employee, company);
    }

    public static void serveBuilding(BuildingEntity building, CompanyEntity company) {
        CompanyDao.serveBuilding(building, company);
    }

    public static void fireEmployee(EmployeeEntity employee, CompanyEntity company) {
        CompanyDao.fireEmployee(employee, company);
    }

    public static double calculateTaxPerEmployee(CompanyEntity company, EmployeeEntity employee) {
        return CompanyDao.calculateTaxPerEmployee(company, employee);
    }

    public static List<CompanyEntity> getCompaniesSortedByIncome(boolean order) {
        return CompanyDao.getCompaniesSortedByIncome(order);
    }

    public static List<EmployeeEntity> getEmployeesByCompanySortedByName(CompanyEntity company, boolean order) {
        return CompanyDao.getEmployeesByCompanySortedByName(company, order);
    }

    public static List<EmployeeEntity> getEmployeesByCompanySortedByBuildingsCount(CompanyEntity company, boolean order) {
        return CompanyDao.getEmployeesByCompanySortedByBuildingsCount(company, order);
    }

    public static void printBuildingsByEmployeeInCompany(CompanyEntity company) {
        Map<EmployeeEntity, List<BuildingEntity>> employeeBuildingMap = CompanyDao.getBuildingsByEmployeesInCompany(company);

        System.out.println("Buildings served by employees in company: " + company.getName());

        for (Map.Entry<EmployeeEntity, List<BuildingEntity>> entry : employeeBuildingMap.entrySet()) {
            EmployeeEntity employee = entry.getKey();
            List<BuildingEntity> buildings = entry.getValue();

            System.out.println("---------------");

            System.out.println("Employee's name: " + employee.getName());
            System.out.println("Total buildings: " + buildings.size());

            for (BuildingEntity building : buildings) {
                System.out.println("  - " + building.getAddress());
            }
        }
        System.out.println("---------------");
    }

    public static void detailedTaxesInformation(List<CompanyEntity> companies) {
        CompanyDao.detailedTaxesInformation(companies);
    }

    public static void detailedPaidTaxesInformation(List<CompanyEntity> companies) {
        CompanyDao.detailedPaidTaxesInformation(companies);
    }
}
