package mx.infotec.eapoyo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mx.infotec.eapoyo.repository.ApoyoRepository;
import mx.infotec.eapoyo.service.ApoyoService;
import mx.infotec.eapoyo.service.dto.ApoyoDTO;
import mx.infotec.eapoyo.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link mx.infotec.eapoyo.domain.Apoyo}.
 */
@RestController
@RequestMapping("/api/apoyos")
public class ApoyoResource {

    private static final Logger LOG = LoggerFactory.getLogger(ApoyoResource.class);

    private static final String ENTITY_NAME = "apoyo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApoyoService apoyoService;

    private final ApoyoRepository apoyoRepository;

    public ApoyoResource(ApoyoService apoyoService, ApoyoRepository apoyoRepository) {
        this.apoyoService = apoyoService;
        this.apoyoRepository = apoyoRepository;
    }

    /**
     * {@code POST  /apoyos} : Create a new apoyo.
     *
     * @param apoyoDTO the apoyoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apoyoDTO, or with status {@code 400 (Bad Request)} if the apoyo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ApoyoDTO> createApoyo(@RequestBody ApoyoDTO apoyoDTO) throws URISyntaxException {
        LOG.debug("REST request to save Apoyo : {}", apoyoDTO);
        apoyoDTO = apoyoService.save(apoyoDTO);
        return ResponseEntity.created(new URI("/api/apoyos/" + apoyoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, apoyoDTO.getId()))
            .body(apoyoDTO);
    }

    /**
     * {@code PUT  /apoyos/:id} : Updates an existing apoyo.
     *
     * @param id the id of the apoyoDTO to save.
     * @param apoyoDTO the apoyoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apoyoDTO,
     * or with status {@code 400 (Bad Request)} if the apoyoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apoyoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApoyoDTO> updateApoyo(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ApoyoDTO apoyoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Apoyo : {}, {}", id, apoyoDTO);
        if (apoyoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apoyoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apoyoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        apoyoDTO = apoyoService.update(apoyoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apoyoDTO.getId()))
            .body(apoyoDTO);
    }

    /**
     * {@code PATCH  /apoyos/:id} : Partial updates given fields of an existing apoyo, field will ignore if it is null
     *
     * @param id the id of the apoyoDTO to save.
     * @param apoyoDTO the apoyoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apoyoDTO,
     * or with status {@code 400 (Bad Request)} if the apoyoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the apoyoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the apoyoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ApoyoDTO> partialUpdateApoyo(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ApoyoDTO apoyoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Apoyo partially : {}, {}", id, apoyoDTO);
        if (apoyoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apoyoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apoyoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ApoyoDTO> result = apoyoService.partialUpdate(apoyoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apoyoDTO.getId())
        );
    }

    /**
     * {@code GET  /apoyos} : get all the apoyos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apoyos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ApoyoDTO>> getAllApoyos(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Apoyos");
        Page<ApoyoDTO> page = apoyoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /apoyos/:id} : get the "id" apoyo.
     *
     * @param id the id of the apoyoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apoyoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApoyoDTO> getApoyo(@PathVariable("id") String id) {
        LOG.debug("REST request to get Apoyo : {}", id);
        Optional<ApoyoDTO> apoyoDTO = apoyoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apoyoDTO);
    }

    /**
     * {@code DELETE  /apoyos/:id} : delete the "id" apoyo.
     *
     * @param id the id of the apoyoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApoyo(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Apoyo : {}", id);
        apoyoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
