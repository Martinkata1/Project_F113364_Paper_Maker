package org.example.services;

import org.example.dao.ResidentDao;
import org.example.dto.CreatingResidentDto;
import org.example.entity.ApartamentEntity;
import org.example.entity.ResidentEntity;
public class ResidentService {
    public static void createResident(ResidentEntity resident) {
        ResidentDao.createResident(resident);
    }

    public static void saveResident(CreatingResidentDto createResidentDto) {
        ResidentDao.saveResidentDto(createResidentDto);
    }

    public static void updateResident(ResidentEntity resident) {
        ResidentDao.updateResident(resident);
    }

    public static void deleteResident(ResidentEntity resident) {
        ResidentDao.deleteResident(resident);
    }

    public static void deleteResidentById(long id) {
        ResidentDao.deleteResidentById(id);
    }

    public static ResidentEntity getResidentById(long id) {
        return ResidentDao.getResidentById(id);
    }

    public static void addResidentToApartment(ResidentEntity resident, ApartamentEntity apartment) {
        ResidentDao.addResidentToApartment(resident, apartment);
    }
}
