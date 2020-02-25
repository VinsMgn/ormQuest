package fr.epsi.vincent.dao;

import fr.epsi.vincent.exception.AlreadyExistsException;
import fr.epsi.vincent.helper.DatabaseHelper;
import fr.epsi.vincent.model.Commande;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class CommandeDao extends GenericDao {
    public static List<Commande> findAll() {
        return getEntityManager().createQuery("select c from Commande c order by c.numero", Commande.class).getResultList();
    }

    public Commande insert(Commande commande) throws AlreadyExistsException {
        EntityManager entityManager = getEntityManager();

        // Check if number already exists
        Commande existing = findById(commande.getNumero());
        if (existing != null) {
            throw new AlreadyExistsException("A command with the number " + commande.getNumero() + " already exists.");
        }
        DatabaseHelper.beginTransaction(entityManager);
        entityManager.persist(commande);
        DatabaseHelper.commitTransactionAndClose(entityManager);
        return commande;
    }

    private Commande findById(int number) {
        TypedQuery<Commande> query = getEntityManager().createQuery("select c from Commande c where c.numero = :number", Commande.class);
        query.setParameter("number", number);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Commande update(Commande commande) {
        EntityManager entityManager = getEntityManager();

        DatabaseHelper.beginTransaction(entityManager);
        entityManager.merge(commande);
        DatabaseHelper.commitTransactionAndClose(entityManager);
        return commande;
    }

    public boolean delete(Commande commande) {
        EntityManager entityManager = getEntityManager();

        try {
            DatabaseHelper.beginTransaction(entityManager);
            entityManager.remove(commande);
            DatabaseHelper.commitTransactionAndClose(entityManager);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
