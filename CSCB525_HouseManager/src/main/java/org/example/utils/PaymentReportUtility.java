package org.example.utils;

import org.example.configuration.SesionFactoryUtility;
import org.example.entity.ApartamentEntity;
import org.example.entity.BuildingEntity;
import org.example.entity.CompanyEntity;
import org.example.entity.EmployeeEntity;
import org.hibernate.Session;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PaymentReportUtility {
    public static void writePaidTaxesToFile(String filePath, List<CompanyEntity> companies) {
        if (companies == null || companies.isEmpty()) {
            throw new IllegalArgumentException("The company list cannot be null or empty.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            writer.write("Company, Employee, Building, Apartment, Amount, Payment Date");
            writer.newLine();

            try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
                for (CompanyEntity company : companies) {
                    List<EmployeeEntity> employees = session.createQuery(
                                    "SELECT e FROM Employee e WHERE e.company = :company", EmployeeEntity.class)
                            .setParameter("company", company)
                            .getResultList();

                    for (EmployeeEntity employee : employees) {
                        List<BuildingEntity> buildings = session.createQuery(
                                        "SELECT b FROM Building b WHERE b.employee = :employee", BuildingEntity.class)
                                .setParameter("employee", employee)
                                .getResultList();

                        for (BuildingEntity building : buildings) {
                            List<ApartamentEntity> apartments = session.createQuery(
                                            "SELECT a FROM Apartment a WHERE a.building = :building", ApartamentEntity.class)
                                    .setParameter("building", building)
                                    .getResultList();

                            for (ApartamentEntity apartment : apartments) {
                                if (apartment.getMonthlyTax() > 0 && apartment.isTaxPaid()) {
                                    String paymentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                                    writer.write(company.getName() + ", "
                                            + employee.getName() + ", "
                                            + building.getAddress() + ", "
                                            + apartment.getNumber() + ", "
                                            + apartment.getMonthlyTax() + ", "
                                            + apartment.getPaidTaxDate() + ", "
                                            + paymentDate);
                                    writer.newLine();
                                }
                            }
                        }
                    }
                }
                System.out.println("Data has been written to " + filePath);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
