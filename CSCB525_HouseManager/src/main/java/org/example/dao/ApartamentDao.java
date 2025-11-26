package org.example.dao;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.example.configuration.SesionFactoryUtility;
import org.example.dto.CreatingApartamentDto;
import org.example.dto.CreatingResidentDto;
import org.example.entity.*;
import org.example.services.ApartamentService;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
public class ApartamentDao {
    public static void createApartment(@Valid ApartamentEntity apartment) {
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(apartment);
            transaction.commit();
        }
    }

    public static void saveApartmentDto(CreatingApartamentDto createApartmentDto) {
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            BuildingEntity building = BuildingDao.getBuildingById(createApartmentDto.getBuildingId());

            if (building == null) {
                throw new IllegalArgumentException("No building with this ID found.");
            }

            ApartamentEntity apartment = new ApartamentEntity();
            apartment.setNumber(createApartmentDto.getNumber());
            apartment.setFloor(createApartmentDto.getFloor());
            apartment.setArea(createApartmentDto.getArea());
            apartment.setBuilding(building);

            session.save(apartment);
            transaction.commit();
        }
    }

    public static void updateApartment(ApartamentEntity apartment) {
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(apartment);
            transaction.commit();
        }
    }

    public static ApartamentEntity getApartmentById(long id) {
        ApartamentEntity apartment;
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            apartment = session.get(ApartamentEntity.class, id);
            transaction.commit();
        }

        return apartment;
    }

    public static void deleteApartment(ApartamentEntity apartment) {
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(apartment);
            transaction.commit();
        }
    }

    public static void deleteApartmentById(long id) {
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            ApartamentEntity apartment = session.get(ApartamentEntity.class, id);

            if (apartment == null) {
                System.out.println("Apartment with ID " + id + " does not exist");
            } else {
                session.delete(apartment);
                transaction.commit();
                System.out.println("Apartment with ID " + id + "was successfully deleted.");
            }
        }
    }

    public static List<ApartamentEntity> getAllApartmentsInBuilding(long buildingId) {
        List<ApartamentEntity> apartments;
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            apartments = session
                    .createQuery("Select a from Apartment where a.building.id = :buildingId", ApartamentEntity.class)
                    .setParameter("buildingId", buildingId)
                    .getResultList();
            transaction.commit();
        }

        return apartments;
    }

    public static List<ApartamentEntity> getAllApartmentsInBuildingOnFloor(long buildingId, @Positive int floorNumber) {
        List<ApartamentEntity> apartments;

        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            apartments = session
                    .createQuery("Select a from Apartment where a.building.id = :buildingId AND a.floor = :floorNumber", ApartamentEntity.class)
                    .setParameter("buildingId", buildingId)
                    .setParameter("floorNumber", floorNumber)
                    .getResultList();
            transaction.commit();
        }
        return apartments;
    }

    public static void assignOwnerToApartment(ResidentEntity resident, ApartamentEntity apartment) {
        if (apartment == null) {
            throw new IllegalArgumentException("Apartment does not exist.");
        }
        if (resident == null) {
            throw new IllegalArgumentException("Resident does not exist.");
        }

        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            resident = session.merge(resident);
            apartment = session.merge(apartment);

            apartment.setOwner(resident);
            session.saveOrUpdate(apartment);
            transaction.commit();

            System.out.println(resident.getName() + " is now the owner of apartment #" + apartment.getNumber());
        }
    }

    public static ResidentEntity getApartmentOwner(long id) {
        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            ApartamentEntity apartment = session.get(ApartamentEntity.class, id);
            return apartment != null ? apartment.getOwner() : null;
        }
    }

    public static double calculateTax(ApartamentEntity apartment, BuildingEntity building) {
        if(apartment == null) {
            throw new IllegalArgumentException("Apartment does not exist.");
        }

        if(building == null) {
            throw new IllegalArgumentException("Building does not exist.");
        }

        double tax = 0.0f;
        double BASE_TAX_PER_SQUARE_METER = 2.0;
        double PET_TAX = 10.0;

        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            apartment = session.merge(apartment);
            building = session.merge(building);

            if(apartment.getResidents().isEmpty()) {
                return tax;
            }

            double ADDITIONAL_TAX_PER_RESIDENT = building.getTax().floatValue();

            tax += apartment.getArea() * BASE_TAX_PER_SQUARE_METER;

            for (ResidentEntity resident : apartment.getResidents()) {
                if (resident.getAge() > 7 && resident.isUsesElevator()) {
                    tax += ADDITIONAL_TAX_PER_RESIDENT;
                }
            }

            for(PetEntity pet : apartment.getPets()) {
                if(pet.isUsesCommonArea()) {
                    tax += PET_TAX;
                }
            }
        }
        return tax;
    }

    public static CompanyEntity findManagingCompany(ApartamentEntity apartment) {
        if (apartment == null) {
            throw new IllegalArgumentException("Apartment does not exist.");
        }

        CompanyEntity managingCompany;

        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            apartment = session.merge(apartment);

            BuildingEntity building = apartment.getBuilding();
            if (building == null) {
                throw new IllegalStateException("No building found for the given apartment.");
            }

            EmployeeEntity manager = building.getEmployee();
            if (manager == null) {
                throw new IllegalStateException("No manager found for the building.");
            }

            managingCompany = manager.getCompany();
            if (managingCompany == null) {
                throw new IllegalStateException("No company managing the building.");
            }
        }

        return managingCompany;
    }


    public static void payTax(ApartamentEntity apartment) {
        if (apartment == null) {
            throw new IllegalArgumentException("Apartment does not exist.");
        }

        if(apartment.getMonthlyTax() == 0) {
            throw new IllegalStateException("The tax is currently 0.");
        }

        if(apartment.isTaxPaid() && Period.between(apartment.getPaidTaxDate(), LocalDate.now()).getMonths() == 1) {
            throw new IllegalStateException("The tax is already paid.");
        }

        try (Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            apartment = session.merge(apartment);
            BuildingEntity building = apartment.getBuilding();

            if (building == null) {
                throw new IllegalStateException("Building for the apartment does not exist.");
            }

            CompanyEntity managingCompany = findManagingCompany(apartment);

            if (managingCompany == null) {
                throw new IllegalStateException("Managing company for the building does not exist.");
            }

            if (apartment.isTaxPaid()) {
                throw new IllegalStateException("Tax for this apartment has already been paid.");
            }

            apartment.setTaxPaid(true);
            apartment.setPaidTaxDate(LocalDate.now());

            double tax = apartment.getMonthlyTax();
            BigDecimal newIncome = managingCompany.getIncome().add(BigDecimal.valueOf(tax));
            managingCompany.setIncome(newIncome);

            session.saveOrUpdate(apartment);
            session.saveOrUpdate(managingCompany);

            transaction.commit();

            System.out.println("Tax of " + tax + " has been paid for Apartment " + apartment.getNumber() + ".");
            System.out.println("Company " + managingCompany.getName() + " now has a total income of " + newIncome + ".");
        }
    }
}
