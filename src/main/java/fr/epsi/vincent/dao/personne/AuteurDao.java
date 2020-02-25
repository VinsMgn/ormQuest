package fr.epsi.vincent.dao.personne;

import fr.epsi.vincent.dao.GenericDao;
import fr.epsi.vincent.exception.AlreadyExistsException;
import fr.epsi.vincent.helper.DatabaseHelper;
import fr.epsi.vincent.model.personne.Auteur;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class AuteurDao extends GenericDao {
    public static List<Auteur> findAll() {
        return getEntityManager().createQuery("select a from Auteur a order by a.id", Auteur.class).getResultList();
    }

    public Auteur insert(Auteur auteur) throws AlreadyExistsException {
        EntityManager entityManager = getEntityManager();

        // Check if number already exists
        Auteur existing = findById(auteur.getId());
        if (existing != null) {
            throw new AlreadyExistsException("An author with the id " + auteur.getId() + " already exists.");
        }
        DatabaseHelper.beginTransaction(entityManager);
        entityManager.persist(auteur);
        DatabaseHelper.commitTransactionAndClose(entityManager);
        return auteur;
    }

    private Auteur findById(int number) {
        TypedQuery<Auteur> query = getEntityManager().createQuery("select a from Auteur a where a.id = :number", Auteur.class);
        query.setParameter("number", number);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Auteur update(Auteur auteur) {
        EntityManager entityManager = getEntityManager();

        DatabaseHelper.beginTransaction(entityManager);
        entityManager.merge(auteur);
        DatabaseHelper.commitTransactionAndClose(entityManager);
        return auteur;
    }

    public boolean delete(Auteur auteur) {
        EntityManager entityManager = getEntityManager();

        try {
            DatabaseHelper.beginTransaction(entityManager);
            entityManager.remove(auteur);
            DatabaseHelper.commitTransactionAndClose(entityManager);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
