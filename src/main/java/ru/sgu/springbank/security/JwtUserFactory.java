package ru.sgu.springbank.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.sgu.springbank.entity.Authority;
import ru.sgu.springbank.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getPhone(),
                user.getAddress(),
                user.isEnabled(),
                user.getLastPasswordResetDate(),
                mapToGrantedAuthorities(user.getAuthorities()),
                user.getAccounts()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }

}
