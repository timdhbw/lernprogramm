package de.master.lernprogramm.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.master.lernprogramm.domain.enumeration.KategorieEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AufgabeEntity.
 */
@Entity
@Table(name = "aufgabe_entity")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "aufgabeentity")
public class AufgabeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "aufgabentitel")
    private String aufgabentitel;

    @Column(name = "bewertung")
    private Integer bewertung;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "kategorie", nullable = false)
    private KategorieEnum kategorie;

    @OneToMany(mappedBy = "aufgabe")
    private Set<AufgabenteilEntity> aufgabenteilEntities = new HashSet<>();

    @OneToMany(mappedBy = "aufgabe")
    private Set<AufgabenhistorieEntity> aufgabenhistorieEntities = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "aufgabe_entity_aufgabentag",
               joinColumns = @JoinColumn(name = "aufgabe_entity_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "aufgabentag_id", referencedColumnName = "id"))
    private Set<AufgabentagEntity> aufgabentags = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "aufgabeEntities", allowSetters = true)
    private ProfilEntity autor;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAufgabentitel() {
        return aufgabentitel;
    }

    public AufgabeEntity aufgabentitel(String aufgabentitel) {
        this.aufgabentitel = aufgabentitel;
        return this;
    }

    public void setAufgabentitel(String aufgabentitel) {
        this.aufgabentitel = aufgabentitel;
    }

    public Integer getBewertung() {
        return bewertung;
    }

    public AufgabeEntity bewertung(Integer bewertung) {
        this.bewertung = bewertung;
        return this;
    }

    public void setBewertung(Integer bewertung) {
        this.bewertung = bewertung;
    }

    public KategorieEnum getKategorie() {
        return kategorie;
    }

    public AufgabeEntity kategorie(KategorieEnum kategorie) {
        this.kategorie = kategorie;
        return this;
    }

    public void setKategorie(KategorieEnum kategorie) {
        this.kategorie = kategorie;
    }

    public Set<AufgabenteilEntity> getAufgabenteilEntities() {
        return aufgabenteilEntities;
    }

    public AufgabeEntity aufgabenteilEntities(Set<AufgabenteilEntity> aufgabenteilEntities) {
        this.aufgabenteilEntities = aufgabenteilEntities;
        return this;
    }

    public AufgabeEntity addAufgabenteilEntity(AufgabenteilEntity aufgabenteilEntity) {
        this.aufgabenteilEntities.add(aufgabenteilEntity);
        aufgabenteilEntity.setAufgabe(this);
        return this;
    }

    public AufgabeEntity removeAufgabenteilEntity(AufgabenteilEntity aufgabenteilEntity) {
        this.aufgabenteilEntities.remove(aufgabenteilEntity);
        aufgabenteilEntity.setAufgabe(null);
        return this;
    }

    public void setAufgabenteilEntities(Set<AufgabenteilEntity> aufgabenteilEntities) {
        this.aufgabenteilEntities = aufgabenteilEntities;
    }

    public Set<AufgabenhistorieEntity> getAufgabenhistorieEntities() {
        return aufgabenhistorieEntities;
    }

    public AufgabeEntity aufgabenhistorieEntities(Set<AufgabenhistorieEntity> aufgabenhistorieEntities) {
        this.aufgabenhistorieEntities = aufgabenhistorieEntities;
        return this;
    }

    public AufgabeEntity addAufgabenhistorieEntity(AufgabenhistorieEntity aufgabenhistorieEntity) {
        this.aufgabenhistorieEntities.add(aufgabenhistorieEntity);
        aufgabenhistorieEntity.setAufgabe(this);
        return this;
    }

    public AufgabeEntity removeAufgabenhistorieEntity(AufgabenhistorieEntity aufgabenhistorieEntity) {
        this.aufgabenhistorieEntities.remove(aufgabenhistorieEntity);
        aufgabenhistorieEntity.setAufgabe(null);
        return this;
    }

    public void setAufgabenhistorieEntities(Set<AufgabenhistorieEntity> aufgabenhistorieEntities) {
        this.aufgabenhistorieEntities = aufgabenhistorieEntities;
    }

    public Set<AufgabentagEntity> getAufgabentags() {
        return aufgabentags;
    }

    public AufgabeEntity aufgabentags(Set<AufgabentagEntity> aufgabentagEntities) {
        this.aufgabentags = aufgabentagEntities;
        return this;
    }

    public AufgabeEntity addAufgabentag(AufgabentagEntity aufgabentagEntity) {
        this.aufgabentags.add(aufgabentagEntity);
        aufgabentagEntity.getAufgabes().add(this);
        return this;
    }

    public AufgabeEntity removeAufgabentag(AufgabentagEntity aufgabentagEntity) {
        this.aufgabentags.remove(aufgabentagEntity);
        aufgabentagEntity.getAufgabes().remove(this);
        return this;
    }

    public void setAufgabentags(Set<AufgabentagEntity> aufgabentagEntities) {
        this.aufgabentags = aufgabentagEntities;
    }

    public ProfilEntity getAutor() {
        return autor;
    }

    public AufgabeEntity autor(ProfilEntity profilEntity) {
        this.autor = profilEntity;
        return this;
    }

    public void setAutor(ProfilEntity profilEntity) {
        this.autor = profilEntity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AufgabeEntity)) {
            return false;
        }
        return id != null && id.equals(((AufgabeEntity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AufgabeEntity{" +
            "id=" + getId() +
            ", aufgabentitel='" + getAufgabentitel() + "'" +
            ", bewertung=" + getBewertung() +
            ", kategorie='" + getKategorie() + "'" +
            "}";
    }
}
