package org.example.dao;

import jakarta.validation.Valid;
import org.example.configuration.SesionFactoryUtility;
import org.example.dto.CreatingApartamentDto;
import org.example.dto.CreatingResidentDto;
import org.example.entity.ApartamentEntity;
import org.example.entity.ResidentEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
public class ResidentDao {
    public static void createResident(@Valid ResidentEntity resident) {
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(resident);
            transaction.commit();
        }
    }

    public static void saveResidentDto(CreatingResidentDto createResidentDto) {
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            ResidentEntity resident = new ResidentEntity();
            resident.setName(createResidentDto.getName());
            resident.setAge(createResidentDto.getAge());
            resident.setUsesElevator(createResidentDto.isUseElevator());
            session.save(resident);
            transaction.commit();
        }
    }

    public static void updateResident(ResidentEntity resident) {
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(resident);
            transaction.commit();
        }
    }

    public static void deleteResident(ResidentEntity resident) {
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(resident);
            transaction.commit();
        }
    }

    public static void deleteResidentById(long id) {
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            ResidentEntity resident = session.get(ResidentEntity.class, id);

            if(resident == null) {
                System.out.println("Resident with ID " + id + " does not exist.");
            } else {
                session.delete(resident);
                transaction.commit();
                System.out.println("Resident with ID " + id + " was successfully deleted.");
            }
        }
    }

    public static ResidentEntity getResidentById(long id) {
        ResidentEntity resident;
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            resident = session.get(ResidentEntity.class, id);
            transaction.commit();
        }
        return resident;
    }

    public static void addResidentToApartment(ResidentEntity resident, ApartamentEntity apartment) {
        if (apartment == null) {
            throw new IllegalArgumentException("Apartment does not exist.");
        }
        if (resident == null) {
            throw new IllegalArgumentException("Resident does not exist.");
        }

        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            resident = session.merge(resident);
            apartment = session.merge(apartment);

            resident.setApartment(apartment);
            apartment.getResidents().add(resident);

            session.saveOrUpdate(resident);
            session.saveOrUpdate(apartment);
            transaction.commit();
            System.out.println("Resident " + resident.getName() + " has been added to apartment #" + apartment.getNumber());
        }
    }
}
