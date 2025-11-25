package mx.infotec.eapoyo.domain;

import java.io.Serializable;
import mx.infotec.eapoyo.domain.enumeration.TipoApoyo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Apoyo.
 */
@Document(collection = "apoyo")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Apoyo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nombre")
    private String nombre;

    @Field("desc")
    private String desc;

    @Field("prerrequisitos")
    private String prerrequisitos;

    @Field("keywords")
    private String keywords;

    @Field("tipo")
    private TipoApoyo tipo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Apoyo id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Apoyo nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesc() {
        return this.desc;
    }

    public Apoyo desc(String desc) {
        this.setDesc(desc);
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrerrequisitos() {
        return this.prerrequisitos;
    }

    public Apoyo prerrequisitos(String prerrequisitos) {
        this.setPrerrequisitos(prerrequisitos);
        return this;
    }

    public void setPrerrequisitos(String prerrequisitos) {
        this.prerrequisitos = prerrequisitos;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public Apoyo keywords(String keywords) {
        this.setKeywords(keywords);
        return this;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public TipoApoyo getTipo() {
        return this.tipo;
    }

    public Apoyo tipo(TipoApoyo tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(TipoApoyo tipo) {
        this.tipo = tipo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Apoyo)) {
            return false;
        }
        return getId() != null && getId().equals(((Apoyo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Apoyo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", desc='" + getDesc() + "'" +
            ", prerrequisitos='" + getPrerrequisitos() + "'" +
            ", keywords='" + getKeywords() + "'" +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
