package mx.infotec.eapoyo.domain;

import java.io.Serializable;
import mx.infotec.eapoyo.domain.enumeration.Estado;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Solicitud.
 */
@Document(collection = "solicitud")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Solicitud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("curp")
    private String curp;

    @Field("nombre")
    private String nombre;

    @Field("primer_apellido")
    private String primerApellido;

    @Field("segundo_apellido")
    private String segundoApellido;

    @Field("genero")
    private String genero;

    @Field("desc")
    private String desc;

    @Field("keywords")
    private String keywords;

    @Field("ine_url")
    private String ineUrl;

    @Field("cv_url")
    private String cvUrl;

    @Field("estado")
    private Estado estado;

    @DBRef
    @Field("apoyo")
    private Apoyo apoyo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Solicitud id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurp() {
        return this.curp;
    }

    public Solicitud curp(String curp) {
        this.setCurp(curp);
        return this;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Solicitud nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return this.primerApellido;
    }

    public Solicitud primerApellido(String primerApellido) {
        this.setPrimerApellido(primerApellido);
        return this;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return this.segundoApellido;
    }

    public Solicitud segundoApellido(String segundoApellido) {
        this.setSegundoApellido(segundoApellido);
        return this;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getGenero() {
        return this.genero;
    }

    public Solicitud genero(String genero) {
        this.setGenero(genero);
        return this;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDesc() {
        return this.desc;
    }

    public Solicitud desc(String desc) {
        this.setDesc(desc);
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public Solicitud keywords(String keywords) {
        this.setKeywords(keywords);
        return this;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getIneUrl() {
        return this.ineUrl;
    }

    public Solicitud ineUrl(String ineUrl) {
        this.setIneUrl(ineUrl);
        return this;
    }

    public void setIneUrl(String ineUrl) {
        this.ineUrl = ineUrl;
    }

    public String getCvUrl() {
        return this.cvUrl;
    }

    public Solicitud cvUrl(String cvUrl) {
        this.setCvUrl(cvUrl);
        return this;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public Estado getEstado() {
        return this.estado;
    }

    public Solicitud estado(Estado estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Apoyo getApoyo() {
        return this.apoyo;
    }

    public void setApoyo(Apoyo apoyo) {
        this.apoyo = apoyo;
    }

    public Solicitud apoyo(Apoyo apoyo) {
        this.setApoyo(apoyo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Solicitud)) {
            return false;
        }
        return getId() != null && getId().equals(((Solicitud) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Solicitud{" +
            "id=" + getId() +
            ", curp='" + getCurp() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", primerApellido='" + getPrimerApellido() + "'" +
            ", segundoApellido='" + getSegundoApellido() + "'" +
            ", genero='" + getGenero() + "'" +
            ", desc='" + getDesc() + "'" +
            ", keywords='" + getKeywords() + "'" +
            ", ineUrl='" + getIneUrl() + "'" +
            ", cvUrl='" + getCvUrl() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
