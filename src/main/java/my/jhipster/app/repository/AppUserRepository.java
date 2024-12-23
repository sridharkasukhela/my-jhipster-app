package my.jhipster.app.repository;

import my.jhipster.app.domain.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the AppUser entity.
 */
@Repository
public interface AppUserRepository extends MongoRepository<AppUser, Long> {}
