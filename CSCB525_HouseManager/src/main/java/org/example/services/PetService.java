package org.example.services;

import org.example.dao.PetDao;
import org.example.dto.CreatingPetDto;
import org.example.entity.ApartamentEntity;
import org.example.entity.PetEntity;

import java.util.List;
public class PetService {
    public static void createPet(PetEntity pet) {
        PetDao.createPet(pet);
    }

    public static void savePet(CreatingPetDto createPetDto) {
        PetDao.savePetDto(createPetDto);
    }

    public static void updatePet(PetEntity pet) {
        PetDao.updatePet(pet);
    }

    public static PetEntity getPetById(long id) {
        return PetDao.getPetById(id);
    }

    public static void deletePet(PetEntity pet) {
        PetDao.deletePet(pet);
    }

    public static void deletePetById(long id) {
        PetDao.deletePetById(id);
    }

    public static List<PetEntity> getAllPetsInApartment(long apartmentId) {
        return PetDao.getAllPetsInApartment(apartmentId);
    }

    public static void addPetToApartment(PetEntity pet, ApartamentEntity apartment) {
        PetDao.addPetToApartment(pet, apartment);
    }
}
