package ru.sgu.springbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.sgu.springbank.auth.JwtAuthenticationRequest;
import ru.sgu.springbank.auth.JwtAuthenticationResponse;
import ru.sgu.springbank.dto.UserDto;
import ru.sgu.springbank.entity.User;
import ru.sgu.springbank.exception.AuthenticationException;
import ru.sgu.springbank.exception.RegistrationException;
import ru.sgu.springbank.mapper.MapperConfig;
import ru.sgu.springbank.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class AuthenticationController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private UserService userService;

    @PostMapping(value = "${jwt.route.registration.path}")
    public ResponseEntity<?> registerAndCreateAuthenticationToken(@Valid @RequestBody UserDto userDto) {
        try {
            User user = MapperConfig.INSTANCE.toEntity(userDto);
            User registeredUser = userService.register(user);
            return ResponseEntity.ok(MapperConfig.INSTANCE.toDto(registeredUser));
        } catch (RegistrationException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @PostMapping(value = "${jwt.route.authentication.path}")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) {
        try {
            String token = userService.login(authenticationRequest.getLogin(), authenticationRequest.getPassword());
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        } catch (AuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @GetMapping(value = "${jwt.route.authentication.refresh}")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        String refreshedToken = userService.refresh(authToken);
        if (refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
    }

}