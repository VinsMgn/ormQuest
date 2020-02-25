package fr.epsi.vincent.model.article;

import fr.epsi.vincent.model.Style;

import javax.persistence.*;

@Entity
@Table(name = "CD")
@PrimaryKeyJoinColumn(name = "Id")

public class CD extends Article {
    @Column(name = "titre")
    private String titre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id")
    private Style style;

    public CD(int prix, String titre, Style style) {
        super(prix);
        this.titre = titre;
        this.style = style;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}
