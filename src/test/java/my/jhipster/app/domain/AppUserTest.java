package my.jhipster.app.domain;

import static my.jhipster.app.domain.AppUserTestSamples.*;
import static my.jhipster.app.domain.UserGroupTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import my.jhipster.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUser.class);
        AppUser appUser1 = getAppUserSample1();
        AppUser appUser2 = new AppUser();
        assertThat(appUser1).isNotEqualTo(appUser2);

        appUser2.setId(appUser1.getId());
        assertThat(appUser1).isEqualTo(appUser2);

        appUser2 = getAppUserSample2();
        assertThat(appUser1).isNotEqualTo(appUser2);
    }

    @Test
    void userGroupTest() {
        AppUser appUser = getAppUserRandomSampleGenerator();
        UserGroup userGroupBack = getUserGroupRandomSampleGenerator();

        appUser.addUserGroup(userGroupBack);
        assertThat(appUser.getUserGroups()).containsOnly(userGroupBack);
        assertThat(userGroupBack.getAppUser()).isEqualTo(appUser);

        appUser.removeUserGroup(userGroupBack);
        assertThat(appUser.getUserGroups()).doesNotContain(userGroupBack);
        assertThat(userGroupBack.getAppUser()).isNull();

        appUser.userGroups(new HashSet<>(Set.of(userGroupBack)));
        assertThat(appUser.getUserGroups()).containsOnly(userGroupBack);
        assertThat(userGroupBack.getAppUser()).isEqualTo(appUser);

        appUser.setUserGroups(new HashSet<>());
        assertThat(appUser.getUserGroups()).doesNotContain(userGroupBack);
        assertThat(userGroupBack.getAppUser()).isNull();
    }
}
