package mx.infotec.eapoyo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import mx.infotec.eapoyo.domain.enumeration.TipoApoyo;

/**
 * A DTO for the {@link mx.infotec.eapoyo.domain.Apoyo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApoyoDTO implements Serializable {

    private String id;

    private String nombre;

    private String desc;

    private String prerrequisitos;

    private String keywords;

    private TipoApoyo tipo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrerrequisitos() {
        return prerrequisitos;
    }

    public void setPrerrequisitos(String prerrequisitos) {
        this.prerrequisitos = prerrequisitos;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public TipoApoyo getTipo() {
        return tipo;
    }

    public void setTipo(TipoApoyo tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApoyoDTO)) {
            return false;
        }

        ApoyoDTO apoyoDTO = (ApoyoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, apoyoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApoyoDTO{" +
            "id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", desc='" + getDesc() + "'" +
            ", prerrequisitos='" + getPrerrequisitos() + "'" +
            ", keywords='" + getKeywords() + "'" +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
