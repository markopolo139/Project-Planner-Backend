package pl.ms.projectoverview.app.configuration.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import pl.ms.projectoverview.app.exceptions.InvalidUserException;

import java.util.*;

public class UserEntity extends User {
    private final int id;
    private final String email;

    public UserEntity(
            String username, String password, Collection<? extends GrantedAuthority> authorities, int id, String email
    ) {
        super(username, password, authorities);
        this.id = id;
        this.email = email;
    }

    public static Builder customBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String username;
        private String password;
        private List<GrantedAuthority> authorities;
        private int id;
        private String email;

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setAuthorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = new ArrayList<>(authorities);
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        private void validate() throws InvalidUserException {
            if (username.isBlank() || password.isBlank() || email.isBlank() || authorities.isEmpty() || id < 0) {
                throw new InvalidUserException();
            }
        }

        public UserEntity build() throws InvalidUserException {
            validate();
            return new UserEntity(username, password, authorities, id, email);
        }
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        if (!super.equals(o)) return false;
        return getId() == that.getId() && Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getEmail());
    }
}
