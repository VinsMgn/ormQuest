package fr.epsi.vincent.dao.article;

import fr.epsi.vincent.dao.GenericDao;
import fr.epsi.vincent.exception.AlreadyExistsException;
import fr.epsi.vincent.helper.DatabaseHelper;
import fr.epsi.vincent.model.article.DVD;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class DVDDao extends GenericDao {

    public static List<DVD> findAll() {
        return getEntityManager().createQuery("select d from DVD d order by d.id", DVD.class).getResultList();
    }


    public DVD findById(int number) {
        TypedQuery<DVD> query = getEntityManager().createQuery("select d from DVD d where d.id = :number", DVD.class);
        query.setParameter("number", number);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public DVD insert(DVD dvd) throws AlreadyExistsException {
        EntityManager entityManager = getEntityManager();

        DVD existing = findById(dvd.getId());
        if (existing != null) {
            throw new AlreadyExistsException("An article with the number " + dvd.getId() + " already exists.");
        }
        DatabaseHelper.beginTransaction(entityManager);
        entityManager.persist(dvd);
        DatabaseHelper.commitTransactionAndClose(entityManager);
        return dvd;
    }

    public DVD update(DVD dvd) {
        EntityManager entityManager = getEntityManager();

        DatabaseHelper.beginTransaction(entityManager);
        entityManager.merge(dvd);
        DatabaseHelper.commitTransactionAndClose(entityManager);

        return dvd;
    }

    public boolean delete(DVD dvd) {
        EntityManager entityManager = getEntityManager();

        try {
            DatabaseHelper.beginTransaction(entityManager);
            entityManager.remove(dvd);
            DatabaseHelper.commitTransactionAndClose(entityManager);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
