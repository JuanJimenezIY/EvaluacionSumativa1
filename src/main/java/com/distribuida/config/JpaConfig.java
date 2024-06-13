package com.distribuida.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class JpaConfig {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pu-distribuida");

    @Produces
    public EntityManager createEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}