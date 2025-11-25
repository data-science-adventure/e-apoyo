package mx.infotec.eapoyo.service.mapper;

import static mx.infotec.eapoyo.domain.ApoyoAsserts.*;
import static mx.infotec.eapoyo.domain.ApoyoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApoyoMapperTest {

    private ApoyoMapper apoyoMapper;

    @BeforeEach
    void setUp() {
        apoyoMapper = new ApoyoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getApoyoSample1();
        var actual = apoyoMapper.toEntity(apoyoMapper.toDto(expected));
        assertApoyoAllPropertiesEquals(expected, actual);
    }
}
