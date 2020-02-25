package fr.epsi.vincent.dao;

import fr.epsi.vincent.exception.AlreadyExistsException;
import fr.epsi.vincent.helper.DatabaseHelper;
import fr.epsi.vincent.model.Style;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class StyleDao extends GenericDao {
    public static List<Style> findAll() {
        return getEntityManager().createQuery("select s from Style s order by s.id", Style.class).getResultList();
    }

    public Style insert(Style style) throws AlreadyExistsException {
        EntityManager entityManager = getEntityManager();

        // Check if number already exists
        Style existing = findById(style.getId());
        if (existing != null) {
            throw new AlreadyExistsException("A style with the id " + style.getId() + " already exists.");
        }
        DatabaseHelper.beginTransaction(entityManager);
        entityManager.persist(style);
        DatabaseHelper.commitTransactionAndClose(entityManager);
        return style;
    }

    private Style findById(int number) {
        TypedQuery<Style> query = getEntityManager().createQuery("select s from Style s where s.id = :number", Style.class);
        query.setParameter("number", number);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Style update(Style style) {
        EntityManager entityManager = getEntityManager();

        DatabaseHelper.beginTransaction(entityManager);
        entityManager.merge(style);
        DatabaseHelper.commitTransactionAndClose(entityManager);
        return style;
    }

    public boolean delete(Style style) {
        EntityManager entityManager = getEntityManager();

        try {
            DatabaseHelper.beginTransaction(entityManager);
            entityManager.remove(style);
            DatabaseHelper.commitTransactionAndClose(entityManager);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
