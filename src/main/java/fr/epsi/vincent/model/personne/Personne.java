package fr.epsi.vincent.model.personne;


import fr.epsi.vincent.model.article.Article;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="Personne")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Personne implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @ManyToMany
    @JoinTable(name = "Article",
            joinColumns = { @JoinColumn(name = "Id") }, //Id de la personne
            inverseJoinColumns = { @JoinColumn(name = "Id") })  // Id de l'article
    private List<Article> articles;

    public abstract int getId();

    public void setId(int id) {
        this.id = id;
    }
}
