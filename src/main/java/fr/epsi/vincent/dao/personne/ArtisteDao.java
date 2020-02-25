package fr.epsi.vincent.dao.personne;

import fr.epsi.vincent.dao.GenericDao;
import fr.epsi.vincent.exception.AlreadyExistsException;
import fr.epsi.vincent.helper.DatabaseHelper;
import fr.epsi.vincent.model.personne.Artiste;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class ArtisteDao extends GenericDao {
    public static List<Artiste> findAll() {
        return getEntityManager().createQuery("select a from Artiste a order by a.id", Artiste.class).getResultList();
    }

    public Artiste insert(Artiste artiste) throws AlreadyExistsException {
        EntityManager entityManager = getEntityManager();

        // Check if number already exists
        Artiste existing = findById(artiste.getId());
        if (existing != null) {
            throw new AlreadyExistsException("An artist with the id " + artiste.getId() + " already exists.");
        }
        DatabaseHelper.beginTransaction(entityManager);
        entityManager.persist(artiste);
        DatabaseHelper.commitTransactionAndClose(entityManager);
        return artiste;
    }

    private Artiste findById(int number) {
        TypedQuery<Artiste> query = getEntityManager().createQuery("select a from Artiste a where a.id = :number", Artiste.class);
        query.setParameter("number", number);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Artiste update(Artiste artiste) {
        EntityManager entityManager = getEntityManager();

        DatabaseHelper.beginTransaction(entityManager);
        entityManager.merge(artiste);
        DatabaseHelper.commitTransactionAndClose(entityManager);
        return artiste;
    }

    public boolean delete(Artiste artiste) {
        EntityManager entityManager = getEntityManager();

        try {
            DatabaseHelper.beginTransaction(entityManager);
            entityManager.remove(artiste);
            DatabaseHelper.commitTransactionAndClose(entityManager);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
