package fr.epsi.vincent.model.personne;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Artiste")
@PrimaryKeyJoinColumn(name = "Id")
public class Artiste extends Personne {
    @Column(name = "Name")
    private String name;

    private int id;

    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}