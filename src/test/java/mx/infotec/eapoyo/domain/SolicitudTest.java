package mx.infotec.eapoyo.domain;

import static mx.infotec.eapoyo.domain.ApoyoTestSamples.*;
import static mx.infotec.eapoyo.domain.SolicitudTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mx.infotec.eapoyo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SolicitudTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Solicitud.class);
        Solicitud solicitud1 = getSolicitudSample1();
        Solicitud solicitud2 = new Solicitud();
        assertThat(solicitud1).isNotEqualTo(solicitud2);

        solicitud2.setId(solicitud1.getId());
        assertThat(solicitud1).isEqualTo(solicitud2);

        solicitud2 = getSolicitudSample2();
        assertThat(solicitud1).isNotEqualTo(solicitud2);
    }

    @Test
    void apoyoTest() {
        Solicitud solicitud = getSolicitudRandomSampleGenerator();
        Apoyo apoyoBack = getApoyoRandomSampleGenerator();

        solicitud.setApoyo(apoyoBack);
        assertThat(solicitud.getApoyo()).isEqualTo(apoyoBack);

        solicitud.apoyo(null);
        assertThat(solicitud.getApoyo()).isNull();
    }
}
