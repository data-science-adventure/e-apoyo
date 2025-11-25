package mx.infotec.eapoyo.service;

import java.util.Optional;
import mx.infotec.eapoyo.service.dto.ApoyoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link mx.infotec.eapoyo.domain.Apoyo}.
 */
public interface ApoyoService {
    /**
     * Save a apoyo.
     *
     * @param apoyoDTO the entity to save.
     * @return the persisted entity.
     */
    ApoyoDTO save(ApoyoDTO apoyoDTO);

    /**
     * Updates a apoyo.
     *
     * @param apoyoDTO the entity to update.
     * @return the persisted entity.
     */
    ApoyoDTO update(ApoyoDTO apoyoDTO);

    /**
     * Partially updates a apoyo.
     *
     * @param apoyoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApoyoDTO> partialUpdate(ApoyoDTO apoyoDTO);

    /**
     * Get all the apoyos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApoyoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" apoyo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApoyoDTO> findOne(String id);

    /**
     * Delete the "id" apoyo.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
