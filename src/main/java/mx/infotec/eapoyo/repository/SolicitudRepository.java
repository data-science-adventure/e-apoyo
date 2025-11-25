package mx.infotec.eapoyo.repository;

import mx.infotec.eapoyo.domain.Solicitud;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Solicitud entity.
 */
@Repository
public interface SolicitudRepository extends MongoRepository<Solicitud, String> {}
