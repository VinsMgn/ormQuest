package fr.epsi.vincent.dao.article;

import fr.epsi.vincent.dao.GenericDao;
import fr.epsi.vincent.exception.AlreadyExistsException;
import fr.epsi.vincent.model.article.Article;
import fr.epsi.vincent.helper.*;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

public class ArticlesDao extends GenericDao {

    /**
     * Finds all articles.
     * @return A list containing all the articles.
     */
    public List<Article> findAll() {
        return getEntityManager().createQuery("select a from Article a order by a.id", Article.class).getResultList();
    }

    /**
     * Finds a article by its id.
     * @return The matching article, otherwise null.
     * @throws SQLException
     */
    public Article findById(int number) {
        TypedQuery<Article> query = getEntityManager().createQuery("select a from Article a where a.id = :number", Article.class);
        query.setParameter("number", number);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Inserts an article.
     *
     * @param article The article to persist.
     * @return The persisted article.
     * @throws AlreadyExistsException The article already exists.
     */
    public Article insert(Article article) throws AlreadyExistsException {
        EntityManager entityManager = getEntityManager();

        Article existing = findById(article.getId());
        if (existing != null) {
            throw new AlreadyExistsException("An article with the number " + article.getId() + " already exists.");
        }
        DatabaseHelper.beginTransaction(entityManager);
        entityManager.persist(article);
        DatabaseHelper.commitTransactionAndClose(entityManager);
        return article;
    }

    public Article update(Article article) {
        EntityManager entityManager = getEntityManager();

        DatabaseHelper.beginTransaction(entityManager);
        entityManager.merge(article);
        DatabaseHelper.commitTransactionAndClose(entityManager);

        return article;
    }

    public boolean delete(Article article) {
        EntityManager entityManager = getEntityManager();

        try {
            DatabaseHelper.beginTransaction(entityManager);
            entityManager.remove(article);
            DatabaseHelper.commitTransactionAndClose(entityManager);
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}
