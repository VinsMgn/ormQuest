package fr.epsi.vincent;

import fr.epsi.vincent.dao.article.ArticlesDao;
import fr.epsi.vincent.dao.article.CDDao;
import fr.epsi.vincent.exception.AlreadyExistsException;
import fr.epsi.vincent.model.Style;
import fr.epsi.vincent.model.article.CD;

import java.util.ArrayList;
import java.util.List;

public class App {

    private static ArticlesDao articlesDao = new ArticlesDao();
    private static CDDao cdsDao = new CDDao();

    public static void main(String[] args) {
        // Appeler le DAO pour récupérer la liste de tous les articles présents en base de données
        System.out.println("Liste des articles dans la base de données :");
        articlesDao.findAll().forEach(System.out::println);

        System.out.println("Insertion de cd :");

        List<CD> newCds = new ArrayList<>();
        Style rock = new Style("Rock");
        CD cd = new CD(10, "Johnny", rock);
        newCds.add(cd);
        newCds.forEach(c -> {
            try {
                System.out.println("CD inserted : " + CDDao.insert(c));
            } catch (AlreadyExistsException e) {
                e.printStackTrace();
            }
        });
        System.out.println();


        // Appeler le DAO pour récupérer les CDs dans la BDD
        System.out.println("Liste des CDs dans la base de données :");
        cdsDao.findAll().forEach(System.out::println);

    }
}
