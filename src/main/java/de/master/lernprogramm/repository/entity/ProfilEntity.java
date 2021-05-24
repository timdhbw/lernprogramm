package de.master.lernprogramm.repository.entity;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProfilEntity.
 */
@Entity
@Table(name = "profil_entity")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "profilentity")
public class ProfilEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "profil_id", nullable = false)
    private String profilId;

    @Column(name = "vorname")
    private String vorname;

    @Column(name = "nachname")
    private String nachname;

    @OneToMany(mappedBy = "profil")
    private Set<BewerteterAufgabentagEntity> bewerteterAufgabentagEntities = new HashSet<>();

    @OneToMany(mappedBy = "profil")
    private Set<AufgabenhistorieEntity> aufgabenhistorieEntities = new HashSet<>();

    @OneToMany(mappedBy = "autor")
    private Set<AufgabeEntity> aufgabeEntities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfilId() {
        return profilId;
    }

    public ProfilEntity profilId(String profilId) {
        this.profilId = profilId;
        return this;
    }

    public void setProfilId(String profilId) {
        this.profilId = profilId;
    }

    public String getVorname() {
        return vorname;
    }

    public ProfilEntity vorname(String vorname) {
        this.vorname = vorname;
        return this;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public ProfilEntity nachname(String nachname) {
        this.nachname = nachname;
        return this;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public Set<BewerteterAufgabentagEntity> getBewerteterAufgabentagEntities() {
        return bewerteterAufgabentagEntities;
    }

    public ProfilEntity bewerteterAufgabentagEntities(Set<BewerteterAufgabentagEntity> bewerteterAufgabentagEntities) {
        this.bewerteterAufgabentagEntities = bewerteterAufgabentagEntities;
        return this;
    }

    public ProfilEntity addBewerteterAufgabentagEntity(BewerteterAufgabentagEntity bewerteterAufgabentagEntity) {
        this.bewerteterAufgabentagEntities.add(bewerteterAufgabentagEntity);
        bewerteterAufgabentagEntity.setProfil(this);
        return this;
    }

    public ProfilEntity removeBewerteterAufgabentagEntity(BewerteterAufgabentagEntity bewerteterAufgabentagEntity) {
        this.bewerteterAufgabentagEntities.remove(bewerteterAufgabentagEntity);
        bewerteterAufgabentagEntity.setProfil(null);
        return this;
    }

    public void setBewerteterAufgabentagEntities(Set<BewerteterAufgabentagEntity> bewerteterAufgabentagEntities) {
        this.bewerteterAufgabentagEntities = bewerteterAufgabentagEntities;
    }

    public Set<AufgabenhistorieEntity> getAufgabenhistorieEntities() {
        return aufgabenhistorieEntities;
    }

    public ProfilEntity aufgabenhistorieEntities(Set<AufgabenhistorieEntity> aufgabenhistorieEntities) {
        this.aufgabenhistorieEntities = aufgabenhistorieEntities;
        return this;
    }

    public ProfilEntity addAufgabenhistorieEntity(AufgabenhistorieEntity aufgabenhistorieEntity) {
        this.aufgabenhistorieEntities.add(aufgabenhistorieEntity);
        aufgabenhistorieEntity.setProfil(this);
        return this;
    }

    public ProfilEntity removeAufgabenhistorieEntity(AufgabenhistorieEntity aufgabenhistorieEntity) {
        this.aufgabenhistorieEntities.remove(aufgabenhistorieEntity);
        aufgabenhistorieEntity.setProfil(null);
        return this;
    }

    public void setAufgabenhistorieEntities(Set<AufgabenhistorieEntity> aufgabenhistorieEntities) {
        this.aufgabenhistorieEntities = aufgabenhistorieEntities;
    }

    public Set<AufgabeEntity> getAufgabeEntities() {
        return aufgabeEntities;
    }

    public ProfilEntity aufgabeEntities(Set<AufgabeEntity> aufgabeEntities) {
        this.aufgabeEntities = aufgabeEntities;
        return this;
    }

    public ProfilEntity addAufgabeEntity(AufgabeEntity aufgabeEntity) {
        this.aufgabeEntities.add(aufgabeEntity);
        aufgabeEntity.setAutor(this);
        return this;
    }

    public ProfilEntity removeAufgabeEntity(AufgabeEntity aufgabeEntity) {
        this.aufgabeEntities.remove(aufgabeEntity);
        aufgabeEntity.setAutor(null);
        return this;
    }

    public void setAufgabeEntities(Set<AufgabeEntity> aufgabeEntities) {
        this.aufgabeEntities = aufgabeEntities;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfilEntity)) {
            return false;
        }
        return id != null && id.equals(((ProfilEntity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfilEntity{" +
            "id=" + getId() +
            ", profilId='" + getProfilId() + "'" +
            ", vorname='" + getVorname() + "'" +
            ", nachname='" + getNachname() + "'" +
            "}";
    }
}
