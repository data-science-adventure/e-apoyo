package mx.infotec.eapoyo.service;

import java.util.Optional;
import mx.infotec.eapoyo.service.dto.SolicitudDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link mx.infotec.eapoyo.domain.Solicitud}.
 */
public interface SolicitudService {
    /**
     * Save a solicitud.
     *
     * @param solicitudDTO the entity to save.
     * @return the persisted entity.
     */
    SolicitudDTO save(SolicitudDTO solicitudDTO);

    /**
     * Updates a solicitud.
     *
     * @param solicitudDTO the entity to update.
     * @return the persisted entity.
     */
    SolicitudDTO update(SolicitudDTO solicitudDTO);

    /**
     * Partially updates a solicitud.
     *
     * @param solicitudDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SolicitudDTO> partialUpdate(SolicitudDTO solicitudDTO);

    /**
     * Get all the solicituds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SolicitudDTO> findAll(Pageable pageable);

    /**
     * Get the "id" solicitud.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SolicitudDTO> findOne(String id);

    /**
     * Delete the "id" solicitud.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
