package fr.epsi.vincent.model;

import fr.epsi.vincent.model.article.CD;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="Style")
@Inheritance(strategy = InheritanceType.JOINED)
public class Style implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "style")
    private String style;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "CD")
    private List<CD> cds;

    public Style(String style) {
        this.style = style;
    }

    public Style() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}