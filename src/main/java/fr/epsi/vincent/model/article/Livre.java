package fr.epsi.vincent.model.article;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Livre")
@PrimaryKeyJoinColumn(name = "Id")
public class Livre extends Article {
    @Column(name = "Titre")
    private String titre;

    public Livre(int prix, String title) {
        super(prix);
        this.titre = title;
    }
}
