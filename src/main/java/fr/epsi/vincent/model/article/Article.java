package fr.epsi.vincent.model.article;

import fr.epsi.vincent.model.Commande;
import fr.epsi.vincent.model.personne.Personne;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Article")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "Prix")
    private int prix;

    @ManyToMany(mappedBy = "articles")
    private Set<Commande> commandes =  new HashSet<Commande>();

    @ManyToMany(mappedBy = "articles")
    private Set<Personne> personnes =  new HashSet<Personne>();

    public Article(int prix) {
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
