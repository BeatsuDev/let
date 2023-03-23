package no.ntnu.let.letapi.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import no.ntnu.let.letapi.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final Long id;
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final boolean admin;

    public UserDetailsImpl(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.admin = user.isAdmin();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = admin ? "ROLE_ADMIN" : "ROLE_USER";
        return List.of((GrantedAuthority) () -> role);
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
