package my.jhipster.app.web.rest;

import static my.jhipster.app.domain.UserGroupAsserts.*;
import static my.jhipster.app.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import my.jhipster.app.IntegrationTest;
import my.jhipster.app.domain.UserGroup;
import my.jhipster.app.repository.UserGroupRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link UserGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/user-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private MockMvc restUserGroupMockMvc;

    private UserGroup userGroup;

    private UserGroup insertedUserGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGroup createEntity() {
        return new UserGroup().name(DEFAULT_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGroup createUpdatedEntity() {
        return new UserGroup().name(UPDATED_NAME);
    }

    @BeforeEach
    public void initTest() {
        userGroup = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedUserGroup != null) {
            userGroupRepository.delete(insertedUserGroup);
            insertedUserGroup = null;
        }
    }

    @Test
    void createUserGroup() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the UserGroup
        var returnedUserGroup = om.readValue(
            restUserGroupMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userGroup)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            UserGroup.class
        );

        // Validate the UserGroup in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertUserGroupUpdatableFieldsEquals(returnedUserGroup, getPersistedUserGroup(returnedUserGroup));

        insertedUserGroup = returnedUserGroup;
    }

    @Test
    void createUserGroupWithExistingId() throws Exception {
        // Create the UserGroup with an existing ID
        userGroup.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserGroupMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userGroup)))
            .andExpect(status().isBadRequest());

        // Validate the UserGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        userGroup.setName(null);

        // Create the UserGroup, which fails.

        restUserGroupMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userGroup)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllUserGroups() throws Exception {
        // Initialize the database
        insertedUserGroup = userGroupRepository.save(userGroup);

        // Get all the userGroupList
        restUserGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    void getUserGroup() throws Exception {
        // Initialize the database
        insertedUserGroup = userGroupRepository.save(userGroup);

        // Get the userGroup
        restUserGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, userGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    void getNonExistingUserGroup() throws Exception {
        // Get the userGroup
        restUserGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingUserGroup() throws Exception {
        // Initialize the database
        insertedUserGroup = userGroupRepository.save(userGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the userGroup
        UserGroup updatedUserGroup = userGroupRepository.findById(userGroup.getId()).orElseThrow();
        updatedUserGroup.name(UPDATED_NAME);

        restUserGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserGroup.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedUserGroup))
            )
            .andExpect(status().isOk());

        // Validate the UserGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedUserGroupToMatchAllProperties(updatedUserGroup);
    }

    @Test
    void putNonExistingUserGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userGroup.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userGroup.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(userGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchUserGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userGroup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(userGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamUserGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userGroup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGroupMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(userGroup)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateUserGroupWithPatch() throws Exception {
        // Initialize the database
        insertedUserGroup = userGroupRepository.save(userGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the userGroup using partial update
        UserGroup partialUpdatedUserGroup = new UserGroup();
        partialUpdatedUserGroup.setId(userGroup.getId());

        restUserGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserGroup.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUserGroup))
            )
            .andExpect(status().isOk());

        // Validate the UserGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUserGroupUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedUserGroup, userGroup),
            getPersistedUserGroup(userGroup)
        );
    }

    @Test
    void fullUpdateUserGroupWithPatch() throws Exception {
        // Initialize the database
        insertedUserGroup = userGroupRepository.save(userGroup);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the userGroup using partial update
        UserGroup partialUpdatedUserGroup = new UserGroup();
        partialUpdatedUserGroup.setId(userGroup.getId());

        partialUpdatedUserGroup.name(UPDATED_NAME);

        restUserGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserGroup.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedUserGroup))
            )
            .andExpect(status().isOk());

        // Validate the UserGroup in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertUserGroupUpdatableFieldsEquals(partialUpdatedUserGroup, getPersistedUserGroup(partialUpdatedUserGroup));
    }

    @Test
    void patchNonExistingUserGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userGroup.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userGroup.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(userGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchUserGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userGroup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(userGroup))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamUserGroup() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        userGroup.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserGroupMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(userGroup))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserGroup in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteUserGroup() throws Exception {
        // Initialize the database
        insertedUserGroup = userGroupRepository.save(userGroup);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the userGroup
        restUserGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, userGroup.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return userGroupRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected UserGroup getPersistedUserGroup(UserGroup userGroup) {
        return userGroupRepository.findById(userGroup.getId()).orElseThrow();
    }

    protected void assertPersistedUserGroupToMatchAllProperties(UserGroup expectedUserGroup) {
        assertUserGroupAllPropertiesEquals(expectedUserGroup, getPersistedUserGroup(expectedUserGroup));
    }

    protected void assertPersistedUserGroupToMatchUpdatableProperties(UserGroup expectedUserGroup) {
        assertUserGroupAllUpdatablePropertiesEquals(expectedUserGroup, getPersistedUserGroup(expectedUserGroup));
    }
}
