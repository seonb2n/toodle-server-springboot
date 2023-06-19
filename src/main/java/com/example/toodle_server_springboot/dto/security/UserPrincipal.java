package com.example.toodle_server_springboot.dto.security;

import com.example.toodle_server_springboot.dto.UserAccountDto;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public record UserPrincipal(
    String email,
    String password,
    String nickname,
    Collection<? extends GrantedAuthority> authorities,
    Map<String, Object> oAuth2Attributes
) implements UserDetails, OAuth2User {

    public static UserPrincipal of(String userEmail, String userPassword, String userNickname) {
        return of(userEmail, userPassword, userNickname, Map.of());
    }

    public static UserPrincipal of(String userEmail, String userPassword, String userNickname,
        Map<String, Object> oAuth2Attributes) {
        Set<RoleType> roleTypes = Set.of(RoleType.USER);

        return new UserPrincipal(
            userEmail,
            userPassword,
            userNickname,
            roleTypes.stream()
                .map(RoleType::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableSet()),
            oAuth2Attributes
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

    //OAuth2User 구현에 필요한 메서드
    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2Attributes;
    }

    //OAuth2User 구현에 필요한 메서드
    @Override
    public String getName() {
        return email;
    }
}
