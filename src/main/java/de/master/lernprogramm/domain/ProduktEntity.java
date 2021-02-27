package de.master.lernprogramm.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A ProduktEntity.
 */
@Entity
@Table(name = "produkt_entity")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "produktentity")
public class ProduktEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "bewertung")
    private Integer bewertung;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ProduktEntity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBewertung() {
        return bewertung;
    }

    public ProduktEntity bewertung(Integer bewertung) {
        this.bewertung = bewertung;
        return this;
    }

    public void setBewertung(Integer bewertung) {
        this.bewertung = bewertung;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduktEntity)) {
            return false;
        }
        return id != null && id.equals(((ProduktEntity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduktEntity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", bewertung=" + getBewertung() +
            "}";
    }
}
