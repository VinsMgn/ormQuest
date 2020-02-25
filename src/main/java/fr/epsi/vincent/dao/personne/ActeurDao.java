package fr.epsi.vincent.dao.personne;

import fr.epsi.vincent.dao.GenericDao;
import fr.epsi.vincent.exception.AlreadyExistsException;
import fr.epsi.vincent.helper.DatabaseHelper;
import fr.epsi.vincent.model.personne.Acteur;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class ActeurDao extends GenericDao {
    public static List<Acteur> findAll() {
        return getEntityManager().createQuery("select a from Acteur a order by a.id", Acteur.class).getResultList();
    }

    public Acteur insert(Acteur acteur) throws AlreadyExistsException {
        EntityManager entityManager = getEntityManager();

        // Check if number already exists
        Acteur existing = findById(acteur.getId());
        if (existing != null) {
            throw new AlreadyExistsException("An actor with the id " + acteur.getId() + " already exists.");
        }
        DatabaseHelper.beginTransaction(entityManager);
        entityManager.persist(acteur);
        DatabaseHelper.commitTransactionAndClose(entityManager);
        return acteur;
    }

    public Acteur findById(int number) {
        TypedQuery<Acteur> query = getEntityManager().createQuery("select a from Acteur a where a.id = :number", Acteur.class);
        query.setParameter("number", number);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Acteur update(Acteur acteur) {
        EntityManager entityManager = getEntityManager();

        DatabaseHelper.beginTransaction(entityManager);
        entityManager.merge(acteur);
        DatabaseHelper.commitTransactionAndClose(entityManager);
        return acteur;
    }

    public boolean delete(Acteur acteur) {
        EntityManager entityManager = getEntityManager();

        try {
            DatabaseHelper.beginTransaction(entityManager);
            entityManager.remove(acteur);
            DatabaseHelper.commitTransactionAndClose(entityManager);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
