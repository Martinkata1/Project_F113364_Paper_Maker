package org.example.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class PetEntity extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "uses_common_area", nullable = false)
    private boolean usesCommonArea = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id")
    private ApartamentEntity apartment;

    public PetEntity() {}

    public PetEntity(String name, boolean usesCommonArea, ApartamentEntity apartment) {
        this.name = name;
        this.usesCommonArea = usesCommonArea;
        this.apartment = apartment;
    }

    public PetEntity(long id, String name, boolean usesCommonArea, ApartamentEntity apartment) {
        super(id);
        this.name = name;
        this.usesCommonArea = usesCommonArea;
        this.apartment = apartment;
    }

    public PetEntity(String name, boolean usesCommonArea) {
        this.name = name;
        this.usesCommonArea = usesCommonArea;
    }

    public PetEntity(long id, String name, boolean usesCommonArea) {
        super(id);
        this.name = name;
        this.usesCommonArea = usesCommonArea;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUsesCommonArea() {
        return usesCommonArea;
    }

    public void setUsesCommonArea(boolean usesCommonArea) {
        this.usesCommonArea = usesCommonArea;
    }

    public ApartamentEntity getApartment() {
        return apartment;
    }

    public void setApartment(ApartamentEntity apartment) {
        this.apartment = apartment;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                ", usesCommonArea=" + usesCommonArea +
                '}';
    }
}
