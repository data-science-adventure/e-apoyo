package mx.infotec.eapoyo.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import mx.infotec.eapoyo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SolicitudDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicitudDTO.class);
        SolicitudDTO solicitudDTO1 = new SolicitudDTO();
        solicitudDTO1.setId("id1");
        SolicitudDTO solicitudDTO2 = new SolicitudDTO();
        assertThat(solicitudDTO1).isNotEqualTo(solicitudDTO2);
        solicitudDTO2.setId(solicitudDTO1.getId());
        assertThat(solicitudDTO1).isEqualTo(solicitudDTO2);
        solicitudDTO2.setId("id2");
        assertThat(solicitudDTO1).isNotEqualTo(solicitudDTO2);
        solicitudDTO1.setId(null);
        assertThat(solicitudDTO1).isNotEqualTo(solicitudDTO2);
    }
}
