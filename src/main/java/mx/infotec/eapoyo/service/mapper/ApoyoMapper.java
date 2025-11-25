package mx.infotec.eapoyo.service.mapper;

import mx.infotec.eapoyo.domain.Apoyo;
import mx.infotec.eapoyo.service.dto.ApoyoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Apoyo} and its DTO {@link ApoyoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ApoyoMapper extends EntityMapper<ApoyoDTO, Apoyo> {}
