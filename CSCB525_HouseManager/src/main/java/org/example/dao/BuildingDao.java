package org.example.dao;

import jakarta.validation.Valid;
import org.example.configuration.SesionFactoryUtility;
import org.example.dto.BuildingDto;
import org.example.dto.CreatingApartamentDto;
import org.example.dto.CreatingBuildingDto;
import org.example.entity.ApartamentEntity;
import org.example.entity.BuildingEntity;
import org.example.entity.CompanyEntity;
import org.example.entity.ResidentEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
public class BuildingDao {
    public static void createBuilding(@Valid BuildingEntity building) {
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(building);
            transaction.commit();
        }
    }

    public static void saveBuildingDto(CreatingBuildingDto createBuildingDto) {
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            BuildingEntity building = new BuildingEntity();
            building.setAddress(createBuildingDto.getAddress());
            building.setFloorsCount(createBuildingDto.getFloorsCount());
            building.setApartmentsCount(createBuildingDto.getApartmentsCount());
            building.setBuiltArea(createBuildingDto.getBuiltArea());
            building.setCommonArea(createBuildingDto.getCommonArea());
            building.setTax(createBuildingDto.getTax());
            session.save(building);
            transaction.commit();
        }
    }

    public static void updateBuilding(BuildingEntity building) {
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(building);
            transaction.commit();
        }
    }

    public static void deleteBuilding(BuildingEntity building) {
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(building);
            transaction.commit();
        }
    }

    public static void deleteBuildingById(long id) {
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            BuildingEntity building = session.get(BuildingEntity.class, id);

            if(building == null) {
                System.out.println("Building with ID " + id + " does not exist.");
            } else {
                session.delete(building);
                transaction.commit();
                System.out.println("Building with ID " + id + " was successfully deleted.");
            }
        }
    }

    public static BuildingEntity getBuildingById(long id) {
        BuildingEntity building;
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            building = session.get(BuildingEntity.class, id);
            transaction.commit();
        }
        return building;
    }

    public static List<BuildingEntity> getBuildings() {
        List<BuildingEntity> buildings;
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            buildings = session
                    .createQuery("Select b from Building b", BuildingEntity.class)
                    .getResultList();
            transaction.commit();
        }

        return buildings;
    }

    public static double calculateTax(BuildingEntity building) {
        if (building == null) {
            throw new IllegalArgumentException("Building doesn't exist.");
        }
        double tax = 0.0f;

        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            building = session.merge(building);

            for(ApartamentEntity apartment : building.getApartments()) {
                double apartmentTax = ApartamentDao.calculateTax(apartment, building);
                apartment.setMonthlyTax(apartmentTax);

                if(Period.between(apartment.getPaidTaxDate(), LocalDate.now()).getMonths() >= 1) {
                    apartment.setTaxPaid(false);
                }

                session.saveOrUpdate(apartment);
                tax += apartmentTax;
            }

            transaction.commit();
        }

        return tax;
    }

    public static List<ResidentEntity> getResidentsByBuildingSortedByName(BuildingEntity building, boolean order) {
        if (building == null) {
            throw new IllegalArgumentException("Building cannot be null.");
        }

        List<ResidentEntity> residents;
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            residents = session.createQuery(
                    "SELECT r FROM Resident r JOIN r.apartment a WHERE a.building = :building " +
                            "ORDER BY r.name " + (order ? "ASC" : "DESC"),
                    ResidentEntity.class
            ).setParameter("building", building).getResultList();

            transaction.commit();
        }
        return residents;
    }

    public static List<ResidentEntity> getResidentsByBuildingSortedByAge(BuildingEntity building, boolean order) {
        if (building == null) {
            throw new IllegalArgumentException("Building cannot be null.");
        }

        List<ResidentEntity> residents;
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            residents = session.createQuery(
                    "SELECT r FROM Resident r JOIN r.apartment a WHERE a.building = :building " +
                            "ORDER BY r.age " + (order ? "ASC" : "DESC"),
                    ResidentEntity.class
            ).setParameter("building", building).getResultList();

            transaction.commit();
        }
        return residents;
    }

    public static List<ApartamentEntity> getApartmentsInBuilding(BuildingEntity building) {
        if (building == null) {
            throw new IllegalArgumentException("Building cannot be null.");
        }

        List<ApartamentEntity> apartments;

        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            apartments = session.createQuery(
                    "SELECT a FROM Apartment a WHERE a.building = :building",
                    ApartamentEntity.class
            ).setParameter("building", building).getResultList();

            transaction.commit();
        }

        return apartments;
    }

    public static List<ResidentEntity> getResidentsInBuilding(BuildingEntity building) {
        if (building == null) {
            throw new IllegalArgumentException("Building cannot be null.");
        }

        List<ResidentEntity> residents = new ArrayList<>();

        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            List<ApartamentEntity> apartments = session.createQuery(
                    "SELECT a FROM Apartment a WHERE a.building = :building",
                    ApartamentEntity.class
            ).setParameter("building", building).getResultList();

            for (ApartamentEntity apartment : apartments) {
                residents.addAll(apartment.getResidents());
            }

            transaction.commit();
        }

        return residents;
    }
}
