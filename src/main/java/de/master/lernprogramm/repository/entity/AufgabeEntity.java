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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "aufgabentitel")
    private String aufgabentitel;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "kategorie", nullable = false)
    private KategorieEnum kategorie;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "aufgabe", cascade = CascadeType.ALL)
    private Set<AufgabenteilEntity> aufgabenteilEntities = new HashSet<>();

    @OneToMany(mappedBy = "aufgabe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<AufgabenbwtunghistEntity> aufgabenbwtunghistEntities = new HashSet<>();

    @OneToMany(mappedBy = "aufgabe", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<AufgabenhistorieEntity> aufgabenhistorieEntities = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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

    public Set<AufgabenbwtunghistEntity> getAufgabenbwtunghistEntities() {
        return aufgabenbwtunghistEntities;
    }

    public AufgabeEntity aufgabenbwtunghistEntities(Set<AufgabenbwtunghistEntity> aufgabenbwtunghistEntities) {
        this.aufgabenbwtunghistEntities = aufgabenbwtunghistEntities;
        return this;
    }

    public AufgabeEntity addAufgabenbwtunghistEntity(AufgabenbwtunghistEntity aufgabenbwtunghistEntity) {
        this.aufgabenbwtunghistEntities.add(aufgabenbwtunghistEntity);
        aufgabenbwtunghistEntity.setAufgabe(this);
        return this;
    }

    public AufgabeEntity removeAufgabenbwtunghistEntity(AufgabenbwtunghistEntity aufgabenbwtunghistEntity) {
        this.aufgabenbwtunghistEntities.remove(aufgabenbwtunghistEntity);
        aufgabenbwtunghistEntity.setAufgabe(null);
        return this;
    }

    public void setAufgabenbwtunghistEntities(Set<AufgabenbwtunghistEntity> aufgabenbwtunghistEntities) {
        this.aufgabenbwtunghistEntities = aufgabenbwtunghistEntities;
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
            ", kategorie='" + getKategorie() + "'" +
            "}";
    }
}
