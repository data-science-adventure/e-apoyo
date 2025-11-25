package mx.infotec.eapoyo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mx.infotec.eapoyo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApoyoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApoyoDTO.class);
        ApoyoDTO apoyoDTO1 = new ApoyoDTO();
        apoyoDTO1.setId("id1");
        ApoyoDTO apoyoDTO2 = new ApoyoDTO();
        assertThat(apoyoDTO1).isNotEqualTo(apoyoDTO2);
        apoyoDTO2.setId(apoyoDTO1.getId());
        assertThat(apoyoDTO1).isEqualTo(apoyoDTO2);
        apoyoDTO2.setId("id2");
        assertThat(apoyoDTO1).isNotEqualTo(apoyoDTO2);
        apoyoDTO1.setId(null);
        assertThat(apoyoDTO1).isNotEqualTo(apoyoDTO2);
    }
}
