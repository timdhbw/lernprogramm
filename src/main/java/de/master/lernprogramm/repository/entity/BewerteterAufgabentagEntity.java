package de.master.lernprogramm.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A BewerteterAufgabentagEntity.
 */
@Entity
@Table(name = "bewerteter_aufgabentag_entity")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "bewerteteraufgabentagentity")
public class BewerteterAufgabentagEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "bewertung")
    private Integer bewertung;

    @ManyToOne
    @JsonIgnoreProperties(value = "bewerteterAufgabentagEntities", allowSetters = true)
    private AufgabentagEntity aufgabentag;

    @ManyToOne
    @JsonIgnoreProperties(value = "bewerteterAufgabentagEntities", allowSetters = true)
    private ProfilEntity profil;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBewertung() {
        return bewertung;
    }

    public BewerteterAufgabentagEntity bewertung(Integer bewertung) {
        this.bewertung = bewertung;
        return this;
    }

    public void setBewertung(Integer bewertung) {
        this.bewertung = bewertung;
    }

    public AufgabentagEntity getAufgabentag() {
        return aufgabentag;
    }

    public BewerteterAufgabentagEntity aufgabentag(AufgabentagEntity aufgabentagEntity) {
        this.aufgabentag = aufgabentagEntity;
        return this;
    }

    public void setAufgabentag(AufgabentagEntity aufgabentagEntity) {
        this.aufgabentag = aufgabentagEntity;
    }

    public ProfilEntity getProfil() {
        return profil;
    }

    public BewerteterAufgabentagEntity profil(ProfilEntity profilEntity) {
        this.profil = profilEntity;
        return this;
    }

    public void setProfil(ProfilEntity profilEntity) {
        this.profil = profilEntity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BewerteterAufgabentagEntity)) {
            return false;
        }
        return id != null && id.equals(((BewerteterAufgabentagEntity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BewerteterAufgabentagEntity{" +
            "id=" + getId() +
            ", bewertung=" + getBewertung() +
            "}";
    }
}
