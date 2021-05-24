package de.master.lernprogramm.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A AufgabenhistorieEntity.
 */
@Entity
@Table(name = "aufgabenhistorie_entity")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "aufgabenhistorieentity")
public class AufgabenhistorieEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "bewertungsveraenderung")
    private Integer bewertungsveraenderung;

    @ManyToOne
    @JsonIgnoreProperties(value = "aufgabenhistorieEntities", allowSetters = true)
    private ProfilEntity profil;

    @ManyToOne
    @JsonIgnoreProperties(value = "aufgabenhistorieEntities", allowSetters = true)
    private AufgabeEntity aufgabe;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public AufgabenhistorieEntity datum(LocalDate datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Integer getBewertungsveraenderung() {
        return bewertungsveraenderung;
    }

    public AufgabenhistorieEntity bewertungsveraenderung(Integer bewertungsveraenderung) {
        this.bewertungsveraenderung = bewertungsveraenderung;
        return this;
    }

    public void setBewertungsveraenderung(Integer bewertungsveraenderung) {
        this.bewertungsveraenderung = bewertungsveraenderung;
    }

    public ProfilEntity getProfil() {
        return profil;
    }

    public AufgabenhistorieEntity profil(ProfilEntity profilEntity) {
        this.profil = profilEntity;
        return this;
    }

    public void setProfil(ProfilEntity profilEntity) {
        this.profil = profilEntity;
    }

    public AufgabeEntity getAufgabe() {
        return aufgabe;
    }

    public AufgabenhistorieEntity aufgabe(AufgabeEntity aufgabeEntity) {
        this.aufgabe = aufgabeEntity;
        return this;
    }

    public void setAufgabe(AufgabeEntity aufgabeEntity) {
        this.aufgabe = aufgabeEntity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AufgabenhistorieEntity)) {
            return false;
        }
        return id != null && id.equals(((AufgabenhistorieEntity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AufgabenhistorieEntity{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", bewertungsveraenderung=" + getBewertungsveraenderung() +
            "}";
    }
}
