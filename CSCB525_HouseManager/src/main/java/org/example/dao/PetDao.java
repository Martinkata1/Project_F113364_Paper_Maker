package org.example.dao;

import jakarta.validation.Valid;
import org.example.configuration.SesionFactoryUtility;
import org.example.dto.CreatingPetDto;
import org.example.entity.ApartamentEntity;
import org.example.entity.PetEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
public class PetDao {
    public static void createPet(@Valid PetEntity pet) {
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(pet);
            transaction.commit();
        }
    }

    public static void savePetDto(CreatingPetDto createPetDto) {
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            PetEntity pet = new PetEntity();
            pet.setName(createPetDto.getName());
            pet.setUsesCommonArea(createPetDto.isUsesCommonArea());

            session.save(pet);
            transaction.commit();
        }
    }

    public static void updatePet(PetEntity pet) {
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(pet);
            transaction.commit();
        }
    }

    public static PetEntity getPetById(long id) {
        PetEntity pet;
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            pet = session.get(PetEntity.class, id);
            transaction.commit();
        }

        return pet;
    }

    public static void deletePet(PetEntity pet) {
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(pet);
            transaction.commit();
        }
    }

    public static void deletePetById(long id) {
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            PetEntity pet = session.get(PetEntity.class, id);

            if(pet == null) {
                System.out.println("Pet with ID " + id + " does not exist");
            } else {
                session.delete(pet);
                transaction.commit();
                System.out.println("Pet with ID " + id + " was successfully deleted.");
            }
        }
    }

    public static List<PetEntity> getAllPetsInApartment(long apartmentId) {
        List<PetEntity> pets;
        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            pets = session
                    .createQuery("Select p from Pets where p.apartment.id = :apartmentId", PetEntity.class)
                    .setParameter("apartmentId", apartmentId)
                    .getResultList();
            transaction.commit();
        }
        return pets;
    }

    public static void addPetToApartment(PetEntity pet, ApartamentEntity apartment) {
        if(pet == null) {
            throw new IllegalArgumentException("Pet does not exist.");
        }

        if (apartment == null) {
            throw new IllegalArgumentException("Apartment does not exist.");
        }

        try(Session session = SesionFactoryUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            pet.setApartment(apartment);
            apartment.getPets().add(pet);

            session.saveOrUpdate(pet);
            session.saveOrUpdate(apartment);

            transaction.commit();

            System.out.println(pet.getName() + " was added to apartment #" + apartment.getNumber());
        }
    }
}
