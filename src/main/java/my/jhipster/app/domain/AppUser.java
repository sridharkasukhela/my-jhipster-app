package my.jhipster.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A AppUser.
 */
@Document(collection = "app_user")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    private Long id;

    @NotNull
    @Field("external_user_id")
    private String externalUserId;

    @NotNull
    @Field("username")
    private String username;

    @NotNull
    @Field("first_name")
    private String firstName;

    @NotNull
    @Field("last_name")
    private String lastName;

    @NotNull
    @Field("email")
    private String email;

    @Field("registered_date")
    private ZonedDateTime registeredDate;

    @Field("last_login_date")
    private ZonedDateTime lastLoginDate;

    @DBRef
    @Field("userGroup")
    @JsonIgnoreProperties(value = { "appUser" }, allowSetters = true)
    private Set<UserGroup> userGroups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExternalUserId() {
        return this.externalUserId;
    }

    public AppUser externalUserId(String externalUserId) {
        this.setExternalUserId(externalUserId);
        return this;
    }

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    public String getUsername() {
        return this.username;
    }

    public AppUser username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public AppUser firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public AppUser lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public AppUser email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ZonedDateTime getRegisteredDate() {
        return this.registeredDate;
    }

    public AppUser registeredDate(ZonedDateTime registeredDate) {
        this.setRegisteredDate(registeredDate);
        return this;
    }

    public void setRegisteredDate(ZonedDateTime registeredDate) {
        this.registeredDate = registeredDate;
    }

    public ZonedDateTime getLastLoginDate() {
        return this.lastLoginDate;
    }

    public AppUser lastLoginDate(ZonedDateTime lastLoginDate) {
        this.setLastLoginDate(lastLoginDate);
        return this;
    }

    public void setLastLoginDate(ZonedDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Set<UserGroup> getUserGroups() {
        return this.userGroups;
    }

    public void setUserGroups(Set<UserGroup> userGroups) {
        if (this.userGroups != null) {
            this.userGroups.forEach(i -> i.setAppUser(null));
        }
        if (userGroups != null) {
            userGroups.forEach(i -> i.setAppUser(this));
        }
        this.userGroups = userGroups;
    }

    public AppUser userGroups(Set<UserGroup> userGroups) {
        this.setUserGroups(userGroups);
        return this;
    }

    public AppUser addUserGroup(UserGroup userGroup) {
        this.userGroups.add(userGroup);
        userGroup.setAppUser(this);
        return this;
    }

    public AppUser removeUserGroup(UserGroup userGroup) {
        this.userGroups.remove(userGroup);
        userGroup.setAppUser(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUser)) {
            return false;
        }
        return getId() != null && getId().equals(((AppUser) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUser{" +
            "id=" + getId() +
            ", externalUserId='" + getExternalUserId() + "'" +
            ", username='" + getUsername() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", registeredDate='" + getRegisteredDate() + "'" +
            ", lastLoginDate='" + getLastLoginDate() + "'" +
            "}";
    }
}
