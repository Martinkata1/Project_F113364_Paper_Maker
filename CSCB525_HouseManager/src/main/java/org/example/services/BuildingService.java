package org.example.services;

import org.example.dao.BuildingDao;
import org.example.dto.CreatingBuildingDto;
import org.example.entity.ApartamentEntity;
import org.example.entity.BuildingEntity;
import org.example.entity.PetEntity;
import org.example.entity.ResidentEntity;

import java.util.List;
public class BuildingService {
    public static void createBuilding(BuildingEntity building) {
        BuildingDao.createBuilding(building);
    }

    public static void saveBuilding(CreatingBuildingDto createBuildingDto) {
        BuildingDao.saveBuildingDto(createBuildingDto);
    }

    public static void updateBuilding(BuildingEntity building) {
        BuildingDao.updateBuilding(building);
    }

    public static void deleteBuilding(BuildingEntity building) {
        BuildingDao.deleteBuilding(building);
    }

    public static void deleteBuildingById(long id) {
        BuildingDao.deleteBuildingById(id);
    }

    public static BuildingEntity getBuildingById(long id) {
        return BuildingDao.getBuildingById(id);
    }

    public static List<BuildingEntity> getAllBuildings() {
        return BuildingDao.getBuildings();
    }

    public static List<ResidentEntity> getResidentsByBuildingSortedByName(BuildingEntity building, boolean order) {
        return BuildingDao.getResidentsByBuildingSortedByName(building, order);
    }

    public static List<ResidentEntity> getResidentsByBuildingSortedByAge(BuildingEntity building, boolean order) {
        return BuildingDao.getResidentsByBuildingSortedByAge(building, order);
    }

    public static void printApartmentsInBuilding(BuildingEntity building) {
        List<ApartamentEntity> apartments = BuildingDao.getApartmentsInBuilding(building);

        System.out.println("Apartments in building: " + building.getAddress());
        for (ApartamentEntity apartment : apartments) {
            System.out.println("---------------");
            System.out.println("Apartment Id: " + apartment.getNumber());
            if(!apartment.getResidents().isEmpty()) {
                System.out.println("Residents : ");
                for(ResidentEntity resident : apartment.getResidents()) {
                    System.out.println(" Person's name and age: " + resident.getName() + " " + resident.getAge());
                }
            }

            if(!apartment.getPets().isEmpty()) {
                System.out.println("Pets : ");
                for(PetEntity pet : apartment.getPets()) {
                    System.out.println(" Animal: " + pet.getName());
                }
            }
            System.out.println("Paid tax: " + apartment.isTaxPaid());
        }
        System.out.println("---------------");
    }

    public static void printAllResidentsInBuilding(BuildingEntity building) {
        List<ResidentEntity> residents = BuildingDao.getResidentsInBuilding(building);

        System.out.println("Residents in building: " + building.getAddress());

        for (ResidentEntity resident : residents) {
            System.out.println("---------------");
            System.out.println("Name: " + resident.getName());
            System.out.println("Age: " + resident.getAge());
            System.out.println("Uses elevator: " + resident.isUsesElevator());
        }
        System.out.println("---------------");
    }
}
