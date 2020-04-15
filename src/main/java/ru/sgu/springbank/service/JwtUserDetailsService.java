package ru.sgu.springbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sgu.springbank.entity.User;
import ru.sgu.springbank.repository.UserRepository;
import ru.sgu.springbank.security.JwtUserFactory;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLoginOrPhone(login, login);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("Не найден пользователь с логином или телефоном '%s'.", login));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}