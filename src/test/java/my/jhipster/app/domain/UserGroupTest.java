package my.jhipster.app.domain;

import static my.jhipster.app.domain.AppUserTestSamples.*;
import static my.jhipster.app.domain.UserGroupTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import my.jhipster.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserGroup.class);
        UserGroup userGroup1 = getUserGroupSample1();
        UserGroup userGroup2 = new UserGroup();
        assertThat(userGroup1).isNotEqualTo(userGroup2);

        userGroup2.setId(userGroup1.getId());
        assertThat(userGroup1).isEqualTo(userGroup2);

        userGroup2 = getUserGroupSample2();
        assertThat(userGroup1).isNotEqualTo(userGroup2);
    }

    @Test
    void appUserTest() {
        UserGroup userGroup = getUserGroupRandomSampleGenerator();
        AppUser appUserBack = getAppUserRandomSampleGenerator();

        userGroup.setAppUser(appUserBack);
        assertThat(userGroup.getAppUser()).isEqualTo(appUserBack);

        userGroup.appUser(null);
        assertThat(userGroup.getAppUser()).isNull();
    }
}
