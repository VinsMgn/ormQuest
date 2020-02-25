package fr.epsi.vincent.dao.personne;

import fr.epsi.vincent.dao.GenericDao;
import fr.epsi.vincent.exception.AlreadyExistsException;
import fr.epsi.vincent.helper.DatabaseHelper;
import fr.epsi.vincent.model.personne.Personne;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class PersonneDao extends GenericDao {
    public static List<Personne> findAll() {
        return getEntityManager().createQuery("select p from Personne p order by p.id", Personne.class).getResultList();
    }

    public Personne insert(Personne personne) throws AlreadyExistsException {
        EntityManager entityManager = getEntityManager();

        // Check if number already exists
        Personne existing = findById(personne.getId());
        if (existing != null) {
            throw new AlreadyExistsException("A person with the id " + personne.getId() + " already exists.");
        }
        DatabaseHelper.beginTransaction(entityManager);
        entityManager.persist(personne);
        DatabaseHelper.commitTransactionAndClose(entityManager);
        return personne;
    }

    public Personne findById(int number) {
        TypedQuery<Personne> query = getEntityManager().createQuery("select p from Personne p where p.id = :number", Personne.class);
        query.setParameter("number", number);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Personne update(Personne personne) {
        EntityManager entityManager = getEntityManager();

        DatabaseHelper.beginTransaction(entityManager);
        entityManager.merge(personne);
        DatabaseHelper.commitTransactionAndClose(entityManager);
        return personne;
    }

    public boolean delete(Personne personne) {
        EntityManager entityManager = getEntityManager();

        try {
            DatabaseHelper.beginTransaction(entityManager);
            entityManager.remove(personne);
            DatabaseHelper.commitTransactionAndClose(entityManager);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
