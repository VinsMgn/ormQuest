package fr.epsi.vincent.dao;


import fr.epsi.vincent.helper.*;

import javax.persistence.EntityManager;


public class GenericDao {

    private static EntityManager entityManager;

    public GenericDao() {
        entityManager = DatabaseHelper.createEntityManager();
    }

    protected static EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = DatabaseHelper.createEntityManager();
        }
        return entityManager;
    }
}
