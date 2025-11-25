package mx.infotec.eapoyo.repository;

import mx.infotec.eapoyo.domain.Apoyo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Apoyo entity.
 */
@Repository
public interface ApoyoRepository extends MongoRepository<Apoyo, String> {}
