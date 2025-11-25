package mx.infotec.eapoyo.service.mapper;

import static mx.infotec.eapoyo.domain.SolicitudAsserts.*;
import static mx.infotec.eapoyo.domain.SolicitudTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolicitudMapperTest {

    private SolicitudMapper solicitudMapper;

    @BeforeEach
    void setUp() {
        solicitudMapper = new SolicitudMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSolicitudSample1();
        var actual = solicitudMapper.toEntity(solicitudMapper.toDto(expected));
        assertSolicitudAllPropertiesEquals(expected, actual);
    }
}
