package mx.infotec.eapoyo.web.rest;

import static mx.infotec.eapoyo.domain.SolicitudAsserts.*;
import static mx.infotec.eapoyo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import mx.infotec.eapoyo.IntegrationTest;
import mx.infotec.eapoyo.domain.Solicitud;
import mx.infotec.eapoyo.domain.enumeration.Estado;
import mx.infotec.eapoyo.repository.SolicitudRepository;
import mx.infotec.eapoyo.service.dto.SolicitudDTO;
import mx.infotec.eapoyo.service.mapper.SolicitudMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link SolicitudResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SolicitudResourceIT {

    private static final String DEFAULT_CURP = "AAAAAAAAAA";
    private static final String UPDATED_CURP = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMER_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_PRIMER_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_GENERO = "AAAAAAAAAA";
    private static final String UPDATED_GENERO = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_KEYWORDS = "AAAAAAAAAA";
    private static final String UPDATED_KEYWORDS = "BBBBBBBBBB";

    private static final String DEFAULT_INE_URL = "AAAAAAAAAA";
    private static final String UPDATED_INE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CV_URL = "AAAAAAAAAA";
    private static final String UPDATED_CV_URL = "BBBBBBBBBB";

    private static final Estado DEFAULT_ESTADO = Estado.EN_CAPTURA;
    private static final Estado UPDATED_ESTADO = Estado.ENVIADA;

    private static final String ENTITY_API_URL = "/api/solicituds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private SolicitudMapper solicitudMapper;

    @Autowired
    private MockMvc restSolicitudMockMvc;

    private Solicitud solicitud;

    private Solicitud insertedSolicitud;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Solicitud createEntity() {
        return new Solicitud()
            .curp(DEFAULT_CURP)
            .nombre(DEFAULT_NOMBRE)
            .primerApellido(DEFAULT_PRIMER_APELLIDO)
            .segundoApellido(DEFAULT_SEGUNDO_APELLIDO)
            .genero(DEFAULT_GENERO)
            .desc(DEFAULT_DESC)
            .keywords(DEFAULT_KEYWORDS)
            .ineUrl(DEFAULT_INE_URL)
            .cvUrl(DEFAULT_CV_URL)
            .estado(DEFAULT_ESTADO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Solicitud createUpdatedEntity() {
        return new Solicitud()
            .curp(UPDATED_CURP)
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .genero(UPDATED_GENERO)
            .desc(UPDATED_DESC)
            .keywords(UPDATED_KEYWORDS)
            .ineUrl(UPDATED_INE_URL)
            .cvUrl(UPDATED_CV_URL)
            .estado(UPDATED_ESTADO);
    }

    @BeforeEach
    void initTest() {
        solicitud = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSolicitud != null) {
            solicitudRepository.delete(insertedSolicitud);
            insertedSolicitud = null;
        }
    }

    @Test
    void createSolicitud() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);
        var returnedSolicitudDTO = om.readValue(
            restSolicitudMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(solicitudDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SolicitudDTO.class
        );

        // Validate the Solicitud in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSolicitud = solicitudMapper.toEntity(returnedSolicitudDTO);
        assertSolicitudUpdatableFieldsEquals(returnedSolicitud, getPersistedSolicitud(returnedSolicitud));

        insertedSolicitud = returnedSolicitud;
    }

    @Test
    void createSolicitudWithExistingId() throws Exception {
        // Create the Solicitud with an existing ID
        solicitud.setId("existing_id");
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolicitudMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(solicitudDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Solicitud in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSolicituds() throws Exception {
        // Initialize the database
        insertedSolicitud = solicitudRepository.save(solicitud);

        // Get all the solicitudList
        restSolicitudMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solicitud.getId())))
            .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].primerApellido").value(hasItem(DEFAULT_PRIMER_APELLIDO)))
            .andExpect(jsonPath("$.[*].segundoApellido").value(hasItem(DEFAULT_SEGUNDO_APELLIDO)))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].keywords").value(hasItem(DEFAULT_KEYWORDS)))
            .andExpect(jsonPath("$.[*].ineUrl").value(hasItem(DEFAULT_INE_URL)))
            .andExpect(jsonPath("$.[*].cvUrl").value(hasItem(DEFAULT_CV_URL)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @Test
    void getSolicitud() throws Exception {
        // Initialize the database
        insertedSolicitud = solicitudRepository.save(solicitud);

        // Get the solicitud
        restSolicitudMockMvc
            .perform(get(ENTITY_API_URL_ID, solicitud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(solicitud.getId()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.primerApellido").value(DEFAULT_PRIMER_APELLIDO))
            .andExpect(jsonPath("$.segundoApellido").value(DEFAULT_SEGUNDO_APELLIDO))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC))
            .andExpect(jsonPath("$.keywords").value(DEFAULT_KEYWORDS))
            .andExpect(jsonPath("$.ineUrl").value(DEFAULT_INE_URL))
            .andExpect(jsonPath("$.cvUrl").value(DEFAULT_CV_URL))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    void getNonExistingSolicitud() throws Exception {
        // Get the solicitud
        restSolicitudMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingSolicitud() throws Exception {
        // Initialize the database
        insertedSolicitud = solicitudRepository.save(solicitud);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the solicitud
        Solicitud updatedSolicitud = solicitudRepository.findById(solicitud.getId()).orElseThrow();
        updatedSolicitud
            .curp(UPDATED_CURP)
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .genero(UPDATED_GENERO)
            .desc(UPDATED_DESC)
            .keywords(UPDATED_KEYWORDS)
            .ineUrl(UPDATED_INE_URL)
            .cvUrl(UPDATED_CV_URL)
            .estado(UPDATED_ESTADO);
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(updatedSolicitud);

        restSolicitudMockMvc
            .perform(
                put(ENTITY_API_URL_ID, solicitudDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(solicitudDTO))
            )
            .andExpect(status().isOk());

        // Validate the Solicitud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSolicitudToMatchAllProperties(updatedSolicitud);
    }

    @Test
    void putNonExistingSolicitud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        solicitud.setId(UUID.randomUUID().toString());

        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSolicitudMockMvc
            .perform(
                put(ENTITY_API_URL_ID, solicitudDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(solicitudDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Solicitud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSolicitud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        solicitud.setId(UUID.randomUUID().toString());

        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolicitudMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(solicitudDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Solicitud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSolicitud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        solicitud.setId(UUID.randomUUID().toString());

        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolicitudMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(solicitudDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Solicitud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSolicitudWithPatch() throws Exception {
        // Initialize the database
        insertedSolicitud = solicitudRepository.save(solicitud);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the solicitud using partial update
        Solicitud partialUpdatedSolicitud = new Solicitud();
        partialUpdatedSolicitud.setId(solicitud.getId());

        partialUpdatedSolicitud.nombre(UPDATED_NOMBRE).genero(UPDATED_GENERO).keywords(UPDATED_KEYWORDS).ineUrl(UPDATED_INE_URL);

        restSolicitudMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSolicitud.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSolicitud))
            )
            .andExpect(status().isOk());

        // Validate the Solicitud in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSolicitudUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSolicitud, solicitud),
            getPersistedSolicitud(solicitud)
        );
    }

    @Test
    void fullUpdateSolicitudWithPatch() throws Exception {
        // Initialize the database
        insertedSolicitud = solicitudRepository.save(solicitud);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the solicitud using partial update
        Solicitud partialUpdatedSolicitud = new Solicitud();
        partialUpdatedSolicitud.setId(solicitud.getId());

        partialUpdatedSolicitud
            .curp(UPDATED_CURP)
            .nombre(UPDATED_NOMBRE)
            .primerApellido(UPDATED_PRIMER_APELLIDO)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .genero(UPDATED_GENERO)
            .desc(UPDATED_DESC)
            .keywords(UPDATED_KEYWORDS)
            .ineUrl(UPDATED_INE_URL)
            .cvUrl(UPDATED_CV_URL)
            .estado(UPDATED_ESTADO);

        restSolicitudMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSolicitud.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSolicitud))
            )
            .andExpect(status().isOk());

        // Validate the Solicitud in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSolicitudUpdatableFieldsEquals(partialUpdatedSolicitud, getPersistedSolicitud(partialUpdatedSolicitud));
    }

    @Test
    void patchNonExistingSolicitud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        solicitud.setId(UUID.randomUUID().toString());

        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSolicitudMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, solicitudDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(solicitudDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Solicitud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSolicitud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        solicitud.setId(UUID.randomUUID().toString());

        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolicitudMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(solicitudDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Solicitud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSolicitud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        solicitud.setId(UUID.randomUUID().toString());

        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSolicitudMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(solicitudDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Solicitud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSolicitud() throws Exception {
        // Initialize the database
        insertedSolicitud = solicitudRepository.save(solicitud);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the solicitud
        restSolicitudMockMvc
            .perform(delete(ENTITY_API_URL_ID, solicitud.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return solicitudRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Solicitud getPersistedSolicitud(Solicitud solicitud) {
        return solicitudRepository.findById(solicitud.getId()).orElseThrow();
    }

    protected void assertPersistedSolicitudToMatchAllProperties(Solicitud expectedSolicitud) {
        assertSolicitudAllPropertiesEquals(expectedSolicitud, getPersistedSolicitud(expectedSolicitud));
    }

    protected void assertPersistedSolicitudToMatchUpdatableProperties(Solicitud expectedSolicitud) {
        assertSolicitudAllUpdatablePropertiesEquals(expectedSolicitud, getPersistedSolicitud(expectedSolicitud));
    }
}
