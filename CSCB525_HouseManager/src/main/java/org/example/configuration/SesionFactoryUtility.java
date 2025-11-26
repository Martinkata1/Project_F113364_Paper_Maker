package org.example.configuration;

import org.example.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SesionFactoryUtility {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(ApartamentEntity.class);
            configuration.addAnnotatedClass(BuildingEntity.class);
            configuration.addAnnotatedClass(CompanyEntity.class);
            configuration.addAnnotatedClass(EmployeeEntity.class);
            configuration.addAnnotatedClass(PetEntity.class);
            configuration.addAnnotatedClass(ResidentEntity.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }

        return sessionFactory;
    }
}