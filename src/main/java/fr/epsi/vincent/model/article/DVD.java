package fr.epsi.vincent.model.article;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "DVD")
@PrimaryKeyJoinColumn(name = "Id")
public class DVD extends Article {
    @Column(name = "Titre")
    private String titre;

    @Column(name = "Category")
    private String category;

    public DVD(int prix, String title) {
        super(prix);
        this.titre = title;
    }
}
