package ru.sgu.springbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sgu.springbank.auth.JwtTokenUtil;
import ru.sgu.springbank.entity.AuthorityType;
import ru.sgu.springbank.entity.User;
import ru.sgu.springbank.exception.AuthenticationException;
import ru.sgu.springbank.exception.RegistrationException;
import ru.sgu.springbank.exception.UserNotFoundException;
import ru.sgu.springbank.repository.AuthorityRepository;
import ru.sgu.springbank.repository.UserRepository;
import ru.sgu.springbank.security.JwtUser;

import java.util.Collections;
import java.util.Objects;

@Service
public class UserService {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(String login, String password) {
        Objects.requireNonNull(login);
        Objects.requireNonNull(password);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
            final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(login);
            return jwtTokenUtil.generateToken(userDetails);
        } catch (DisabledException ex) {
            throw new AuthenticationException("Пользователь неактивирован", ex);
        } catch (BadCredentialsException ex) {
            throw new AuthenticationException("Неверный логин или пароль", ex);
        }
    }

    public User register(User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new RegistrationException("Пользователь с таким логином уже существует");
        }
        user.setAuthorities(Collections.singletonList(authorityRepository.findByName(AuthorityType.ROLE_USER)));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public String refresh(String authToken) {
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser)jwtUserDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь с таким ID не найден"));
    }

    public User getByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

}
