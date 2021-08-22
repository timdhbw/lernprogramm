package de.master.lernprogramm.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A MultipleChoiceAntwortEntity.
 */
@Entity
@Table(name = "multiple_choice_antwort_entity")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "multiplechoiceantwortentity")
public class MultipleChoiceAntwortEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "checked")
    private Boolean checked;

    @Column(name = "checked_richtig")
    private Boolean checkedRichtig;

    @Column(name = "label")
    private String label;

    @ManyToOne
    @JsonIgnoreProperties(value = "multipleChoiceAntwortEntities", allowSetters = true)
    private AufgabenteilEntity aufgabenteil;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isChecked() {
        return checked;
    }

    public MultipleChoiceAntwortEntity checked(Boolean checked) {
        this.checked = checked;
        return this;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean isCheckedRichtig() {
        return checkedRichtig;
    }

    public MultipleChoiceAntwortEntity checkedRichtig(Boolean checkedRichtig) {
        this.checkedRichtig = checkedRichtig;
        return this;
    }

    public void setCheckedRichtig(Boolean checkedRichtig) {
        this.checkedRichtig = checkedRichtig;
    }

    public String getLabel() {
        return label;
    }

    public MultipleChoiceAntwortEntity label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public AufgabenteilEntity getAufgabenteil() {
        return aufgabenteil;
    }

    public MultipleChoiceAntwortEntity aufgabenteil(AufgabenteilEntity aufgabenteilEntity) {
        this.aufgabenteil = aufgabenteilEntity;
        return this;
    }

    public void setAufgabenteil(AufgabenteilEntity aufgabenteilEntity) {
        this.aufgabenteil = aufgabenteilEntity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MultipleChoiceAntwortEntity)) {
            return false;
        }
        return id != null && id.equals(((MultipleChoiceAntwortEntity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MultipleChoiceAntwortEntity{" +
            "id=" + getId() +
            ", checked='" + isChecked() + "'" +
            ", checkedRichtig='" + isCheckedRichtig() + "'" +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
