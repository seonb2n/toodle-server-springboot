package com.example.toodle_server_springboot.dto.security;

import com.example.toodle_server_springboot.dto.UserAccountDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record UserPrincipal(
        String email,
        String password,
        String nickname,
        Collection<? extends GrantedAuthority> authorities
) implements UserDetails {

    public static UserPrincipal of(String userEmail, String userPassword, String userNickname) {
        Set<RoleType> roleTypes = Set.of(RoleType.USER);

        return new UserPrincipal(
                userEmail,
                userPassword,
                userNickname,
                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

    public static UserPrincipal from(UserAccountDto dto) {
        return UserPrincipal.of(
                dto.email(),
                dto.password(),
                dto.nickname()
        );
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(
                email,
                password,
                nickname
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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

    public enum RoleType {
        USER("ROLE_USER");

        @Getter
        private final String name;

        RoleType(String name) {
            this.name = name;
        }
    }
}
