package my.jhipster.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A UserGroup.
 */
@Document(collection = "user_group")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    private Long id;

    @NotNull
    @Field("name")
    private String name;

    @DBRef
    @Field("appUser")
    @JsonIgnoreProperties(value = { "userGroups" }, allowSetters = true)
    private AppUser appUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserGroup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public UserGroup name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AppUser getAppUser() {
        return this.appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public UserGroup appUser(AppUser appUser) {
        this.setAppUser(appUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserGroup)) {
            return false;
        }
        return getId() != null && getId().equals(((UserGroup) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
