package mx.infotec.eapoyo.service.mapper;

import mx.infotec.eapoyo.domain.Apoyo;
import mx.infotec.eapoyo.domain.Solicitud;
import mx.infotec.eapoyo.service.dto.ApoyoDTO;
import mx.infotec.eapoyo.service.dto.SolicitudDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Solicitud} and its DTO {@link SolicitudDTO}.
 */
@Mapper(componentModel = "spring")
public interface SolicitudMapper extends EntityMapper<SolicitudDTO, Solicitud> {
    @Mapping(target = "apoyo", source = "apoyo", qualifiedByName = "apoyoId")
    SolicitudDTO toDto(Solicitud s);

    @Named("apoyoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ApoyoDTO toDtoApoyoId(Apoyo apoyo);
}
