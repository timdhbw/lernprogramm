package de.master.lernprogramm.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.master.lernprogramm.domain.enumeration.AufgabenteiltypEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A AufgabenteilEntity.
 */
@Entity
@Table(name = "aufgabenteil_entity")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "aufgabenteilentity")
public class AufgabenteilEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "laufen_nr")
    private Integer laufenNr;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "aufgabenteiltyp", nullable = false)
    private AufgabenteiltypEnum aufgabenteiltyp;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(updatable = false)
    @JsonIgnoreProperties(value = "aufgabenteilEntities", allowSetters = true)
    private AufgabeEntity aufgabe;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLaufenNr() {
        return laufenNr;
    }

    public AufgabenteilEntity laufenNr(Integer laufenNr) {
        this.laufenNr = laufenNr;
        return this;
    }

    public void setLaufenNr(Integer laufenNr) {
        this.laufenNr = laufenNr;
    }

    public AufgabenteiltypEnum getAufgabenteiltyp() {
        return aufgabenteiltyp;
    }

    public AufgabenteilEntity aufgabenteiltyp(AufgabenteiltypEnum aufgabenteiltyp) {
        this.aufgabenteiltyp = aufgabenteiltyp;
        return this;
    }

    public void setAufgabenteiltyp(AufgabenteiltypEnum aufgabenteiltyp) {
        this.aufgabenteiltyp = aufgabenteiltyp;
    }

    public String getText() {
        return text;
    }

    public AufgabenteilEntity text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AufgabeEntity getAufgabe() {
        return aufgabe;
    }

    public AufgabenteilEntity aufgabe(AufgabeEntity aufgabeEntity) {
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
        if (!(o instanceof AufgabenteilEntity)) {
            return false;
        }
        return id != null && id.equals(((AufgabenteilEntity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AufgabenteilEntity{" +
            "id=" + getId() +
            ", laufenNr=" + getLaufenNr() +
            ", aufgabenteiltyp='" + getAufgabenteiltyp() + "'" +
            ", text='" + getText() + "'" +
            "}";
    }
}
