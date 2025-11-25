package mx.infotec.eapoyo.service.impl;

import java.util.Optional;
import mx.infotec.eapoyo.domain.Solicitud;
import mx.infotec.eapoyo.repository.SolicitudRepository;
import mx.infotec.eapoyo.service.SolicitudService;
import mx.infotec.eapoyo.service.dto.SolicitudDTO;
import mx.infotec.eapoyo.service.mapper.SolicitudMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing
 * {@link mx.infotec.eapoyo.domain.Solicitud}.
 */
@Service
public class SolicitudServiceImpl implements SolicitudService {

    private static final Logger LOG = LoggerFactory.getLogger(SolicitudServiceImpl.class);

    private final SolicitudRepository solicitudRepository;

    private final SolicitudMapper solicitudMapper;

    private static final String SERVER_URL = "http://207.249.118.42/";

    public SolicitudServiceImpl(SolicitudRepository solicitudRepository, SolicitudMapper solicitudMapper) {
        this.solicitudRepository = solicitudRepository;
        this.solicitudMapper = solicitudMapper;
    }

    @Override
    public SolicitudDTO save(SolicitudDTO solicitudDTO) {
        LOG.debug("Request to save Solicitud : {}", solicitudDTO);
        Solicitud solicitud = solicitudMapper.toEntity(solicitudDTO);
        solicitud = solicitudRepository.save(solicitud);
        return solicitudMapper.toDto(solicitud);
    }

    @Override
    public SolicitudDTO update(SolicitudDTO solicitudDTO) {
        LOG.debug("Request to update Solicitud : {}", solicitudDTO);
        Solicitud solicitud = solicitudMapper.toEntity(solicitudDTO);
        solicitud = solicitudRepository.save(solicitud);
        return solicitudMapper.toDto(solicitud);
    }

    @Override
    public Optional<SolicitudDTO> partialUpdate(SolicitudDTO solicitudDTO) {
        LOG.debug("Request to partially update Solicitud : {}", solicitudDTO);

        return solicitudRepository
            .findById(solicitudDTO.getId())
            .map(existingSolicitud -> {
                solicitudMapper.partialUpdate(existingSolicitud, solicitudDTO);

                return existingSolicitud;
            })
            .map(solicitudRepository::save)
            .map(solicitudMapper::toDto);
    }

    @Override
    public Page<SolicitudDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Solicituds");
        return solicitudRepository
            .findAll(pageable)
            .map(solicitudMapper::toDto)
            .map(solicitud -> {
                solicitud.setCvUrl(SERVER_URL + solicitud.getCvUrl());
                solicitud.setIneUrl(SERVER_URL + solicitud.getIneUrl());
                return solicitud;
            });
    }

    @Override
    public Optional<SolicitudDTO> findOne(String id) {
        LOG.debug("Request to get Solicitud : {}", id);
        return solicitudRepository
            .findById(id)
            .map(solicitudMapper::toDto)
            .map(solicitud -> {
                solicitud.setCvUrl(SERVER_URL + solicitud.getCvUrl());
                solicitud.setIneUrl(SERVER_URL + solicitud.getIneUrl());
                return solicitud;
            });
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Solicitud : {}", id);
        solicitudRepository.deleteById(id);
    }
}
