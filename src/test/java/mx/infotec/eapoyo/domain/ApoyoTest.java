package mx.infotec.eapoyo.domain;

import static mx.infotec.eapoyo.domain.ApoyoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import mx.infotec.eapoyo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApoyoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Apoyo.class);
        Apoyo apoyo1 = getApoyoSample1();
        Apoyo apoyo2 = new Apoyo();
        assertThat(apoyo1).isNotEqualTo(apoyo2);

        apoyo2.setId(apoyo1.getId());
        assertThat(apoyo1).isEqualTo(apoyo2);

        apoyo2 = getApoyoSample2();
        assertThat(apoyo1).isNotEqualTo(apoyo2);
    }
}
