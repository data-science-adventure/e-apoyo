package mx.infotec.eapoyo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import mx.infotec.eapoyo.domain.enumeration.Estado;

/**
 * A DTO for the {@link mx.infotec.eapoyo.domain.Solicitud} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SolicitudDTO implements Serializable {

    private String id;

    private String curp;

    private String nombre;

    private String primerApellido;

    private String segundoApellido;

    private String genero;

    private String desc;

    private String keywords;

    private String ineUrl;

    private String cvUrl;

    private Estado estado;

    private ApoyoDTO apoyo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getIneUrl() {
        return ineUrl;
    }

    public void setIneUrl(String ineUrl) {
        this.ineUrl = ineUrl;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public ApoyoDTO getApoyo() {
        return apoyo;
    }

    public void setApoyo(ApoyoDTO apoyo) {
        this.apoyo = apoyo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SolicitudDTO)) {
            return false;
        }

        SolicitudDTO solicitudDTO = (SolicitudDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, solicitudDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SolicitudDTO{" +
            "id='" + getId() + "'" +
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
            ", apoyo=" + getApoyo() +
            "}";
    }
}
