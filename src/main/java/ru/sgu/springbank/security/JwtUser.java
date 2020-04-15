package ru.sgu.springbank.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.sgu.springbank.entity.Account;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class JwtUser implements UserDetails {

    private final long id;

    private final String login;

    private final String password;

    private final String phone;

    private final String address;

    private final boolean enabled;

    private final Date lastPasswordResetDate;

    private final Collection<? extends GrantedAuthority> authorities;

    private final List<Account> accounts;

    public JwtUser(
            long id,
            String login,
            String password,
            String phone,
            String address,
            boolean enabled,
            Date lastPasswordResetDate,
            Collection<? extends GrantedAuthority> authorities,
            List<Account> accounts
    ) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.authorities = authorities;
        this.accounts = accounts;
    }

    @JsonIgnore
    public long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @JsonIgnore
    public String getPhone() {
        return phone;
    }

    @JsonIgnore
    public String getAddress() {
        return address;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    public List<Account> getAccounts() {
        return accounts;
    }
}
