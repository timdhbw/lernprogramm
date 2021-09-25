package de.master.lernprogramm.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AufgabentagEntity.
 */
@Entity
@Table(name = "aufgabentag_entity")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "aufgabentagentity")
public class AufgabentagEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tag", nullable = false)
    private String tag;

    @Column(name = "text")
    private String text;

    @ManyToMany(mappedBy = "aufgabentags", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<AufgabeEntity> aufgabes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public AufgabentagEntity tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getText() {
        return text;
    }

    public AufgabentagEntity text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<AufgabeEntity> getAufgabes() {
        return aufgabes;
    }

    public AufgabentagEntity aufgabes(Set<AufgabeEntity> aufgabeEntities) {
        this.aufgabes = aufgabeEntities;
        return this;
    }

    public AufgabentagEntity addAufgabe(AufgabeEntity aufgabeEntity) {
        this.aufgabes.add(aufgabeEntity);
        aufgabeEntity.getAufgabentags().add(this);
        return this;
    }

    public AufgabentagEntity removeAufgabe(AufgabeEntity aufgabeEntity) {
        this.aufgabes.remove(aufgabeEntity);
        aufgabeEntity.getAufgabentags().remove(this);
        return this;
    }

    public void setAufgabes(Set<AufgabeEntity> aufgabeEntities) {
        this.aufgabes = aufgabeEntities;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AufgabentagEntity)) {
            return false;
        }
        return id != null && id.equals(((AufgabentagEntity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AufgabentagEntity{" +
            "id=" + getId() +
            ", tag='" + getTag() + "'" +
            ", text='" + getText() + "'" +
            "}";
    }
}
