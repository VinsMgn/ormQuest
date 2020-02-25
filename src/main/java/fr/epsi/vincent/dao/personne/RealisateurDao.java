package fr.epsi.vincent.dao.personne;

import fr.epsi.vincent.dao.GenericDao;
import fr.epsi.vincent.exception.AlreadyExistsException;
import fr.epsi.vincent.helper.DatabaseHelper;
import fr.epsi.vincent.model.personne.Realisateur;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class RealisateurDao extends GenericDao {
    public static List<Realisateur> findAll() {
        return getEntityManager().createQuery("select r from Realisateur r order by r.id", Realisateur.class).getResultList();
    }

    public Realisateur insert(Realisateur realisateur) throws AlreadyExistsException {
        EntityManager entityManager = getEntityManager();

        // Check if number already exists
        Realisateur existing = findById(realisateur.getId());
        if (existing != null) {
            throw new AlreadyExistsException("A realisator with the id " + realisateur.getId() + " already exists.");
        }
        DatabaseHelper.beginTransaction(entityManager);
        entityManager.persist(realisateur);
        DatabaseHelper.commitTransactionAndClose(entityManager);
        return realisateur;
    }

    private Realisateur findById(int number) {
        TypedQuery<Realisateur> query = getEntityManager().createQuery("select r from Realisateur r where r.id = :number", Realisateur.class);
        query.setParameter("number", number);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Realisateur update(Realisateur realisateur) {
        EntityManager entityManager = getEntityManager();

        DatabaseHelper.beginTransaction(entityManager);
        entityManager.merge(realisateur);
        DatabaseHelper.commitTransactionAndClose(entityManager);
        return realisateur;
    }

    public boolean delete(Realisateur realisateur) {
        EntityManager entityManager = getEntityManager();

        try {
            DatabaseHelper.beginTransaction(entityManager);
            entityManager.remove(realisateur);
            DatabaseHelper.commitTransactionAndClose(entityManager);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
