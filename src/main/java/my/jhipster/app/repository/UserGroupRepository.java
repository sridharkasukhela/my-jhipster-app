package my.jhipster.app.repository;

import my.jhipster.app.domain.UserGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the UserGroup entity.
 */
@Repository
public interface UserGroupRepository extends MongoRepository<UserGroup, Long> {}
