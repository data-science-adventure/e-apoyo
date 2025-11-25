package mx.infotec.eapoyo.service.impl;

import java.util.Optional;
import mx.infotec.eapoyo.domain.Apoyo;
import mx.infotec.eapoyo.repository.ApoyoRepository;
import mx.infotec.eapoyo.service.ApoyoService;
import mx.infotec.eapoyo.service.dto.ApoyoDTO;
import mx.infotec.eapoyo.service.mapper.ApoyoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link mx.infotec.eapoyo.domain.Apoyo}.
 */
@Service
public class ApoyoServiceImpl implements ApoyoService {

    private static final Logger LOG = LoggerFactory.getLogger(ApoyoServiceImpl.class);

    private final ApoyoRepository apoyoRepository;

    private final ApoyoMapper apoyoMapper;

    public ApoyoServiceImpl(ApoyoRepository apoyoRepository, ApoyoMapper apoyoMapper) {
        this.apoyoRepository = apoyoRepository;
        this.apoyoMapper = apoyoMapper;
    }

    @Override
    public ApoyoDTO save(ApoyoDTO apoyoDTO) {
        LOG.debug("Request to save Apoyo : {}", apoyoDTO);
        Apoyo apoyo = apoyoMapper.toEntity(apoyoDTO);
        apoyo = apoyoRepository.save(apoyo);
        return apoyoMapper.toDto(apoyo);
    }

    @Override
    public ApoyoDTO update(ApoyoDTO apoyoDTO) {
        LOG.debug("Request to update Apoyo : {}", apoyoDTO);
        Apoyo apoyo = apoyoMapper.toEntity(apoyoDTO);
        apoyo = apoyoRepository.save(apoyo);
        return apoyoMapper.toDto(apoyo);
    }

    @Override
    public Optional<ApoyoDTO> partialUpdate(ApoyoDTO apoyoDTO) {
        LOG.debug("Request to partially update Apoyo : {}", apoyoDTO);

        return apoyoRepository
            .findById(apoyoDTO.getId())
            .map(existingApoyo -> {
                apoyoMapper.partialUpdate(existingApoyo, apoyoDTO);

                return existingApoyo;
            })
            .map(apoyoRepository::save)
            .map(apoyoMapper::toDto);
    }

    @Override
    public Page<ApoyoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Apoyos");
        return apoyoRepository.findAll(pageable).map(apoyoMapper::toDto);
    }

    @Override
    public Optional<ApoyoDTO> findOne(String id) {
        LOG.debug("Request to get Apoyo : {}", id);
        return apoyoRepository.findById(id).map(apoyoMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete Apoyo : {}", id);
        apoyoRepository.deleteById(id);
    }
}
