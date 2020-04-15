package ru.sgu.springbank.controller;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.sgu.springbank.dto.AccountDto;
import ru.sgu.springbank.entity.Account;
import ru.sgu.springbank.entity.Transfer;
import ru.sgu.springbank.entity.User;
import ru.sgu.springbank.mapper.MapperConfig;
import ru.sgu.springbank.security.JwtUser;
import ru.sgu.springbank.service.AccountService;
import ru.sgu.springbank.service.TransferService;
import ru.sgu.springbank.service.UserService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransferService transferService;

    @PostMapping("accounts")
    public ResponseEntity<?> create(@AuthenticationPrincipal JwtUser jwtUser, @Valid @RequestBody AccountDto accountDto) {
        try {
            User loggedUser = userService.getById(jwtUser.getId());
            Account account = MapperConfig.INSTANCE.toEntity(accountDto);
            account.setUser(loggedUser);
            Account createdAccount = accountService.add(account);
            return ResponseEntity.ok(MapperConfig.INSTANCE.toDto(createdAccount));
        } catch (ExpiredJwtException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ваша сессия истекла", ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @PutMapping("accounts/{accountId}/replenish")
    public ResponseEntity<?> replenish(
            @AuthenticationPrincipal JwtUser jwtUser,
            @PathVariable String accountId,
            @RequestParam BigDecimal amount
    ) {
        try {
            User loggedUser = userService.getById(jwtUser.getId());
            Account account = accountService.getByUserAndId(loggedUser, UUID.fromString(accountId));
            accountService.replenish(account, amount);
            return ResponseEntity.ok().body(null);
        } catch (ExpiredJwtException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ваша сессия истекла", ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @PostMapping("accounts/{senderId}/transfer")
    public ResponseEntity<?> transfer(
            @AuthenticationPrincipal JwtUser jwtUser,
            @PathVariable String senderId,
            @RequestParam String receiverId,
            @RequestParam BigDecimal amount
    ) {
        try {
            User loggedUser = userService.getById(jwtUser.getId());
            Account from = accountService.getByUserAndId(loggedUser, UUID.fromString(senderId));
            Account to = accountService.getById(UUID.fromString(receiverId));
            accountService.transfer(loggedUser, from, to, amount);
            return ResponseEntity.ok().body(null);
        } catch (ExpiredJwtException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Ваша сессия истекла", ex);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @GetMapping("{userId}/accounts/transfers")
    public ResponseEntity<?> getTransfers(@PathVariable long userId) {
        try {
            User loggedUser = userService.getById(userId);
            List<Transfer> transfers = transferService.getAllByAccount(loggedUser.getAccounts());
            return ResponseEntity.ok().body(MapperConfig.INSTANCE.toDtoList(transfers));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

}