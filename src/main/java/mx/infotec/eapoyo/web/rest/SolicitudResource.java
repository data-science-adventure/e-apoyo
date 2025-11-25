package mx.infotec.eapoyo.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mx.infotec.eapoyo.repository.SolicitudRepository;
import mx.infotec.eapoyo.service.SolicitudService;
import mx.infotec.eapoyo.service.dto.SolicitudDTO;
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
 * REST controller for managing {@link mx.infotec.eapoyo.domain.Solicitud}.
 */
@RestController
@RequestMapping("/api/solicituds")
public class SolicitudResource {

    private static final Logger LOG = LoggerFactory.getLogger(SolicitudResource.class);

    private static final String ENTITY_NAME = "solicitud";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SolicitudService solicitudService;

    private final SolicitudRepository solicitudRepository;

    public SolicitudResource(SolicitudService solicitudService, SolicitudRepository solicitudRepository) {
        this.solicitudService = solicitudService;
        this.solicitudRepository = solicitudRepository;
    }

    /**
     * {@code POST  /solicituds} : Create a new solicitud.
     *
     * @param solicitudDTO the solicitudDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new solicitudDTO, or with status {@code 400 (Bad Request)} if the solicitud has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SolicitudDTO> createSolicitud(@RequestBody SolicitudDTO solicitudDTO) throws URISyntaxException {
        LOG.debug("REST request to save Solicitud : {}", solicitudDTO);
        if (solicitudDTO.getId() != null) {
            throw new BadRequestAlertException("A new solicitud cannot already have an ID", ENTITY_NAME, "idexists");
        }
        solicitudDTO = solicitudService.save(solicitudDTO);
        return ResponseEntity.created(new URI("/api/solicituds/" + solicitudDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, solicitudDTO.getId()))
            .body(solicitudDTO);
    }

    /**
     * {@code PUT  /solicituds/:id} : Updates an existing solicitud.
     *
     * @param id the id of the solicitudDTO to save.
     * @param solicitudDTO the solicitudDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solicitudDTO,
     * or with status {@code 400 (Bad Request)} if the solicitudDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the solicitudDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SolicitudDTO> updateSolicitud(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SolicitudDTO solicitudDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Solicitud : {}, {}", id, solicitudDTO);
        if (solicitudDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, solicitudDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!solicitudRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        solicitudDTO = solicitudService.update(solicitudDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, solicitudDTO.getId()))
            .body(solicitudDTO);
    }

    /**
     * {@code PATCH  /solicituds/:id} : Partial updates given fields of an existing solicitud, field will ignore if it is null
     *
     * @param id the id of the solicitudDTO to save.
     * @param solicitudDTO the solicitudDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solicitudDTO,
     * or with status {@code 400 (Bad Request)} if the solicitudDTO is not valid,
     * or with status {@code 404 (Not Found)} if the solicitudDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the solicitudDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SolicitudDTO> partialUpdateSolicitud(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody SolicitudDTO solicitudDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Solicitud partially : {}, {}", id, solicitudDTO);
        if (solicitudDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, solicitudDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!solicitudRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SolicitudDTO> result = solicitudService.partialUpdate(solicitudDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, solicitudDTO.getId())
        );
    }

    /**
     * {@code GET  /solicituds} : get all the solicituds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of solicituds in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SolicitudDTO>> getAllSolicituds(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Solicituds");
        Page<SolicitudDTO> page = solicitudService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /solicituds/:id} : get the "id" solicitud.
     *
     * @param id the id of the solicitudDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the solicitudDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudDTO> getSolicitud(@PathVariable("id") String id) {
        LOG.debug("REST request to get Solicitud : {}", id);
        Optional<SolicitudDTO> solicitudDTO = solicitudService.findOne(id);
        return ResponseUtil.wrapOrNotFound(solicitudDTO);
    }

    /**
     * {@code DELETE  /solicituds/:id} : delete the "id" solicitud.
     *
     * @param id the id of the solicitudDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSolicitud(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Solicitud : {}", id);
        solicitudService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
