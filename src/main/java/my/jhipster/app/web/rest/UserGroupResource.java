package my.jhipster.app.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import my.jhipster.app.domain.UserGroup;
import my.jhipster.app.repository.UserGroupRepository;
import my.jhipster.app.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link my.jhipster.app.domain.UserGroup}.
 */
@RestController
@RequestMapping("/api/user-groups")
public class UserGroupResource {

    private static final Logger LOG = LoggerFactory.getLogger(UserGroupResource.class);

    private static final String ENTITY_NAME = "userGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserGroupRepository userGroupRepository;

    public UserGroupResource(UserGroupRepository userGroupRepository) {
        this.userGroupRepository = userGroupRepository;
    }

    /**
     * {@code POST  /user-groups} : Create a new userGroup.
     *
     * @param userGroup the userGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userGroup, or with status {@code 400 (Bad Request)} if the userGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UserGroup> createUserGroup(@Valid @RequestBody UserGroup userGroup) throws URISyntaxException {
        LOG.debug("REST request to save UserGroup : {}", userGroup);
        if (userGroup.getId() != null) {
            throw new BadRequestAlertException("A new userGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        userGroup = userGroupRepository.save(userGroup);
        return ResponseEntity.created(new URI("/api/user-groups/" + userGroup.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, userGroup.getId().toString()))
            .body(userGroup);
    }

    /**
     * {@code PUT  /user-groups/:id} : Updates an existing userGroup.
     *
     * @param id the id of the userGroup to save.
     * @param userGroup the userGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGroup,
     * or with status {@code 400 (Bad Request)} if the userGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserGroup> updateUserGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserGroup userGroup
    ) throws URISyntaxException {
        LOG.debug("REST request to update UserGroup : {}, {}", id, userGroup);
        if (userGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        userGroup = userGroupRepository.save(userGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userGroup.getId().toString()))
            .body(userGroup);
    }

    /**
     * {@code PATCH  /user-groups/:id} : Partial updates given fields of an existing userGroup, field will ignore if it is null
     *
     * @param id the id of the userGroup to save.
     * @param userGroup the userGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGroup,
     * or with status {@code 400 (Bad Request)} if the userGroup is not valid,
     * or with status {@code 404 (Not Found)} if the userGroup is not found,
     * or with status {@code 500 (Internal Server Error)} if the userGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserGroup> partialUpdateUserGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserGroup userGroup
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update UserGroup partially : {}, {}", id, userGroup);
        if (userGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userGroup.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserGroup> result = userGroupRepository
            .findById(userGroup.getId())
            .map(existingUserGroup -> {
                if (userGroup.getName() != null) {
                    existingUserGroup.setName(userGroup.getName());
                }

                return existingUserGroup;
            })
            .map(userGroupRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userGroup.getId().toString())
        );
    }

    /**
     * {@code GET  /user-groups} : get all the userGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userGroups in body.
     */
    @GetMapping("")
    public List<UserGroup> getAllUserGroups() {
        LOG.debug("REST request to get all UserGroups");
        return userGroupRepository.findAll();
    }

    /**
     * {@code GET  /user-groups/:id} : get the "id" userGroup.
     *
     * @param id the id of the userGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserGroup> getUserGroup(@PathVariable("id") Long id) {
        LOG.debug("REST request to get UserGroup : {}", id);
        Optional<UserGroup> userGroup = userGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userGroup);
    }

    /**
     * {@code DELETE  /user-groups/:id} : delete the "id" userGroup.
     *
     * @param id the id of the userGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserGroup(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete UserGroup : {}", id);
        userGroupRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
