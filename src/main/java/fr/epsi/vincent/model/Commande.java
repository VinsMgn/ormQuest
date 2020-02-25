package fr.epsi.vincent.model;

import fr.epsi.vincent.model.article.Article;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="Commande")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Commande implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Numero")
    private int numero;

    @ManyToMany
    @JoinTable(name = "Article",
            joinColumns = { @JoinColumn(name = "Id") }, //Id de la commande
            inverseJoinColumns = { @JoinColumn(name = "Id") })  // Id de l'article
    private List<Article> articles;


    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
