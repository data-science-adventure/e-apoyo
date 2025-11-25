package mx.infotec.eapoyo.web.rest;

import static mx.infotec.eapoyo.domain.ApoyoAsserts.*;
import static mx.infotec.eapoyo.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import mx.infotec.eapoyo.IntegrationTest;
import mx.infotec.eapoyo.domain.Apoyo;
import mx.infotec.eapoyo.domain.enumeration.TipoApoyo;
import mx.infotec.eapoyo.repository.ApoyoRepository;
import mx.infotec.eapoyo.service.dto.ApoyoDTO;
import mx.infotec.eapoyo.service.mapper.ApoyoMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ApoyoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ApoyoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_PRERREQUISITOS = "AAAAAAAAAA";
    private static final String UPDATED_PRERREQUISITOS = "BBBBBBBBBB";

    private static final String DEFAULT_KEYWORDS = "AAAAAAAAAA";
    private static final String UPDATED_KEYWORDS = "BBBBBBBBBB";

    private static final TipoApoyo DEFAULT_TIPO = TipoApoyo.CURSO;
    private static final TipoApoyo UPDATED_TIPO = TipoApoyo.BECA;

    private static final String ENTITY_API_URL = "/api/apoyos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ApoyoRepository apoyoRepository;

    @Autowired
    private ApoyoMapper apoyoMapper;

    @Autowired
    private MockMvc restApoyoMockMvc;

    private Apoyo apoyo;

    private Apoyo insertedApoyo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apoyo createEntity() {
        return new Apoyo()
            .nombre(DEFAULT_NOMBRE)
            .desc(DEFAULT_DESC)
            .prerrequisitos(DEFAULT_PRERREQUISITOS)
            .keywords(DEFAULT_KEYWORDS)
            .tipo(DEFAULT_TIPO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apoyo createUpdatedEntity() {
        return new Apoyo()
            .nombre(UPDATED_NOMBRE)
            .desc(UPDATED_DESC)
            .prerrequisitos(UPDATED_PRERREQUISITOS)
            .keywords(UPDATED_KEYWORDS)
            .tipo(UPDATED_TIPO);
    }

    @BeforeEach
    void initTest() {
        apoyo = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedApoyo != null) {
            apoyoRepository.delete(insertedApoyo);
            insertedApoyo = null;
        }
    }

    @Test
    void createApoyo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Apoyo
        ApoyoDTO apoyoDTO = apoyoMapper.toDto(apoyo);
        var returnedApoyoDTO = om.readValue(
            restApoyoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apoyoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ApoyoDTO.class
        );

        // Validate the Apoyo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedApoyo = apoyoMapper.toEntity(returnedApoyoDTO);
        assertApoyoUpdatableFieldsEquals(returnedApoyo, getPersistedApoyo(returnedApoyo));

        insertedApoyo = returnedApoyo;
    }

    @Test
    void createApoyoWithExistingId() throws Exception {
        // Create the Apoyo with an existing ID
        apoyo.setId("existing_id");
        ApoyoDTO apoyoDTO = apoyoMapper.toDto(apoyo);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApoyoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apoyoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Apoyo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void getAllApoyos() throws Exception {
        // Initialize the database
        insertedApoyo = apoyoRepository.save(apoyo);

        // Get all the apoyoList
        restApoyoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apoyo.getId())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].prerrequisitos").value(hasItem(DEFAULT_PRERREQUISITOS)))
            .andExpect(jsonPath("$.[*].keywords").value(hasItem(DEFAULT_KEYWORDS)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @Test
    void getApoyo() throws Exception {
        // Initialize the database
        insertedApoyo = apoyoRepository.save(apoyo);

        // Get the apoyo
        restApoyoMockMvc
            .perform(get(ENTITY_API_URL_ID, apoyo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apoyo.getId()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC))
            .andExpect(jsonPath("$.prerrequisitos").value(DEFAULT_PRERREQUISITOS))
            .andExpect(jsonPath("$.keywords").value(DEFAULT_KEYWORDS))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    void getNonExistingApoyo() throws Exception {
        // Get the apoyo
        restApoyoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingApoyo() throws Exception {
        // Initialize the database
        insertedApoyo = apoyoRepository.save(apoyo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apoyo
        Apoyo updatedApoyo = apoyoRepository.findById(apoyo.getId()).orElseThrow();
        updatedApoyo
            .nombre(UPDATED_NOMBRE)
            .desc(UPDATED_DESC)
            .prerrequisitos(UPDATED_PRERREQUISITOS)
            .keywords(UPDATED_KEYWORDS)
            .tipo(UPDATED_TIPO);
        ApoyoDTO apoyoDTO = apoyoMapper.toDto(updatedApoyo);

        restApoyoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apoyoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apoyoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Apoyo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedApoyoToMatchAllProperties(updatedApoyo);
    }

    @Test
    void putNonExistingApoyo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apoyo.setId(UUID.randomUUID().toString());

        // Create the Apoyo
        ApoyoDTO apoyoDTO = apoyoMapper.toDto(apoyo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApoyoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, apoyoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apoyoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apoyo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchApoyo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apoyo.setId(UUID.randomUUID().toString());

        // Create the Apoyo
        ApoyoDTO apoyoDTO = apoyoMapper.toDto(apoyo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApoyoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(apoyoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apoyo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamApoyo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apoyo.setId(UUID.randomUUID().toString());

        // Create the Apoyo
        ApoyoDTO apoyoDTO = apoyoMapper.toDto(apoyo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApoyoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(apoyoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Apoyo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateApoyoWithPatch() throws Exception {
        // Initialize the database
        insertedApoyo = apoyoRepository.save(apoyo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apoyo using partial update
        Apoyo partialUpdatedApoyo = new Apoyo();
        partialUpdatedApoyo.setId(apoyo.getId());

        partialUpdatedApoyo.keywords(UPDATED_KEYWORDS).tipo(UPDATED_TIPO);

        restApoyoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApoyo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApoyo))
            )
            .andExpect(status().isOk());

        // Validate the Apoyo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApoyoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedApoyo, apoyo), getPersistedApoyo(apoyo));
    }

    @Test
    void fullUpdateApoyoWithPatch() throws Exception {
        // Initialize the database
        insertedApoyo = apoyoRepository.save(apoyo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the apoyo using partial update
        Apoyo partialUpdatedApoyo = new Apoyo();
        partialUpdatedApoyo.setId(apoyo.getId());

        partialUpdatedApoyo
            .nombre(UPDATED_NOMBRE)
            .desc(UPDATED_DESC)
            .prerrequisitos(UPDATED_PRERREQUISITOS)
            .keywords(UPDATED_KEYWORDS)
            .tipo(UPDATED_TIPO);

        restApoyoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedApoyo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedApoyo))
            )
            .andExpect(status().isOk());

        // Validate the Apoyo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertApoyoUpdatableFieldsEquals(partialUpdatedApoyo, getPersistedApoyo(partialUpdatedApoyo));
    }

    @Test
    void patchNonExistingApoyo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apoyo.setId(UUID.randomUUID().toString());

        // Create the Apoyo
        ApoyoDTO apoyoDTO = apoyoMapper.toDto(apoyo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApoyoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, apoyoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(apoyoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apoyo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchApoyo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apoyo.setId(UUID.randomUUID().toString());

        // Create the Apoyo
        ApoyoDTO apoyoDTO = apoyoMapper.toDto(apoyo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApoyoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(apoyoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Apoyo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamApoyo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        apoyo.setId(UUID.randomUUID().toString());

        // Create the Apoyo
        ApoyoDTO apoyoDTO = apoyoMapper.toDto(apoyo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApoyoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(apoyoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Apoyo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteApoyo() throws Exception {
        // Initialize the database
        insertedApoyo = apoyoRepository.save(apoyo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the apoyo
        restApoyoMockMvc
            .perform(delete(ENTITY_API_URL_ID, apoyo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return apoyoRepository.count();
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

    protected Apoyo getPersistedApoyo(Apoyo apoyo) {
        return apoyoRepository.findById(apoyo.getId()).orElseThrow();
    }

    protected void assertPersistedApoyoToMatchAllProperties(Apoyo expectedApoyo) {
        assertApoyoAllPropertiesEquals(expectedApoyo, getPersistedApoyo(expectedApoyo));
    }

    protected void assertPersistedApoyoToMatchUpdatableProperties(Apoyo expectedApoyo) {
        assertApoyoAllUpdatablePropertiesEquals(expectedApoyo, getPersistedApoyo(expectedApoyo));
    }
}
