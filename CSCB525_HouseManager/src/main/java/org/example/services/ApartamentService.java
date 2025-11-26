package org.example.services;

import org.example.dao.ApartamentDao;
import org.example.dto.CreatingApartamentDto;
import org.example.entity.ApartamentEntity;
import org.example.entity.ResidentEntity;

import java.util.List;
public class ApartamentService {
    public static void createApartment(ApartamentEntity apartment) {
        ApartamentDao.createApartment(apartment);
    }

    public static void saveApartment(CreatingApartamentDto createApartmentDto) {
        ApartamentDao.saveApartmentDto(createApartmentDto);
    }

    public static void updateApartment(ApartamentEntity apartment) {
        ApartamentDao.updateApartment(apartment);
    }

    public static void deleteApartment(ApartamentEntity apartment) {
        ApartamentDao.deleteApartment(apartment);
    }

    public static void deleteApartmentById(long id) {
        ApartamentDao.deleteApartmentById(id);
    }

    public static ApartamentEntity getApartmentById(long id) {
        return ApartamentDao.getApartmentById(id);
    }

    public static List<ApartamentEntity> getAllApartmentsInBuilding(long buildingId) {
        return ApartamentDao.getAllApartmentsInBuilding(buildingId);
    }

    public static List<ApartamentEntity> getAllApartmentsInBuildingOnFloor(long buildingId, int floorNumber) {
        return ApartamentDao.getAllApartmentsInBuildingOnFloor(buildingId, floorNumber);
    }

    public static void assignOwnerToApartment(ResidentEntity resident, ApartamentEntity apartment) {
        ApartamentDao.assignOwnerToApartment(resident, apartment);
    }

    public static ResidentEntity getApartmentOwner(long id) {
        return ApartamentDao.getApartmentOwner(id);
    }

    public static void payTax(ApartamentEntity apartment) {
        ApartamentDao.payTax(apartment);
    }
}
