package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PastOrPresent;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.Set;

@Entity
public class EmployeeEntity extends BaseEntity {
    @NotBlank(message = "Employee's name cannot be blank!")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Valid
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER)
    private Set<BuildingEntity> buildings;

    public EmployeeEntity() {}

    public EmployeeEntity(String name, LocalDate hireDate) {
        this.name = name;
        this.hireDate = hireDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public @Valid CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(@Valid CompanyEntity company) {
        this.company = company;
    }

    public Set<BuildingEntity> getBuildings() {
        return buildings;
    }

    public void setBuildings(Set<BuildingEntity> buildings) {
        this.buildings = buildings;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", hireDate=" + hireDate +
                ", company=" + company +
                '}';
    }
}
