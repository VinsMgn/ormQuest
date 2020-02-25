package fr.epsi.vincent.dao.article;

import fr.epsi.vincent.dao.GenericDao;
import fr.epsi.vincent.exception.AlreadyExistsException;
import fr.epsi.vincent.helper.DatabaseHelper;
import fr.epsi.vincent.model.article.CD;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class CDDao extends GenericDao {
    /**
     * Finds all articles.
     * @return A list containing all the articles.
     */
    public List<CD> findAll() {
        return getEntityManager().createQuery("select c from CD c order by c.id", CD.class).getResultList();
    }
    public static CD insert(CD cd) throws AlreadyExistsException {
        EntityManager entityManager = getEntityManager();

        // Check if number already exists
        CD existing = findByTitle(cd.getTitre());
        if (existing != null) {
            throw new AlreadyExistsException("A cd with the title " + cd.getTitre() + " already exists.");
        }
        DatabaseHelper.beginTransaction(entityManager);
        entityManager.persist(cd);
        DatabaseHelper.commitTransactionAndClose(entityManager);
        return cd;
    }


    public CD update(CD cd) {
        EntityManager entityManager = getEntityManager();

        DatabaseHelper.beginTransaction(entityManager);
        entityManager.merge(cd);
        DatabaseHelper.commitTransactionAndClose(entityManager);

        return cd;
    }

    public CD findByTitle(String titre) {
        TypedQuery<CD> query = getEntityManager().createQuery("from CD c where c.titre = :titre", CD.class);
        query.setParameter("titre", titre);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public boolean delete(CD cd) {
        EntityManager entityManager = getEntityManager();

        try {
            DatabaseHelper.beginTransaction(entityManager);
            entityManager.remove(cd);
            DatabaseHelper.commitTransactionAndClose(entityManager);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
