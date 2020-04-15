package ru.sgu.springbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sgu.springbank.entity.AccCodeType;
import ru.sgu.springbank.entity.Account;
import ru.sgu.springbank.entity.Transfer;
import ru.sgu.springbank.entity.User;
import ru.sgu.springbank.exception.NotEnoughBalanceException;
import ru.sgu.springbank.exception.PermissionDenied;
import ru.sgu.springbank.exception.SelfTransferException;
import ru.sgu.springbank.repository.AccountRepository;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AccountService {

    private final Map<AccCodeType, Double> currency = Stream.of(
            new AbstractMap.SimpleEntry<>(AccCodeType.RUB, 1.),
            new AbstractMap.SimpleEntry<>(AccCodeType.USD, 60.),
            new AbstractMap.SimpleEntry<>(AccCodeType.EUR, 70.))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransferService transferService;

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public List<Account> getAllByUser(User user) {
        return accountRepository.findAllByUser(user);
    }

    public Account getById(UUID id) throws AccountNotFoundException {
        return accountRepository.findById(id).orElseThrow(() ->
                new AccountNotFoundException("Аккаунт с таким ID не найден"));
    }

    public Account getByUserAndId(User user, UUID id) throws AccountNotFoundException {
        return accountRepository.findByUserAndId(user, id).orElseThrow(() ->
                new AccountNotFoundException("Аккаунт с таким ID у вашего пользователя не найден"));
    }

    public boolean existsByUser(User user) {
        return accountRepository.existsByUser(user);
    }

    public Account add(Account account) {
        return accountRepository.save(account);
    }

    public void replenish(Account account, BigDecimal amount) {
        BigDecimal balance = account.getAmount();
        BigDecimal newBalance = balance.add(amount);
        account.setAmount(newBalance);
        accountRepository.save(account);
    }

    public void transfer(User fromUser, Account from, Account to, BigDecimal amount) {
        if (!fromUser.equals(from.getUser())) {
            throw new PermissionDenied("Вы не можете перевести средства с чужого счета");
        }

        if (to.equals(from)) {
            throw new SelfTransferException("Нельзя осуществлять перевод между одинаковыми аккаунтами");
        }
        BigDecimal fromBalance = from.getAmount();
        if (fromBalance.subtract(amount).doubleValue() < 0) {
            throw new NotEnoughBalanceException("На вашем счету недостаточно средств");
        }
        BigDecimal newFromBalance = fromBalance.subtract(amount);
        BigDecimal toBalance = to.getAmount();
        BigDecimal transferSum = BigDecimal
                        .valueOf(currency.get(from.getAccCodeType()) / currency.get(to.getAccCodeType()))
                        .multiply(amount);
        BigDecimal newToBalance = toBalance.add(transferSum);
        from.setAmount(newFromBalance);
        to.setAmount(newToBalance);
        accountRepository.save(from);
        accountRepository.save(to);
        Transfer transfer = new Transfer();
        transfer.setFrom(from);
        transfer.setTo(to);
        transfer.setAmount(amount);
        transfer.setDate(new Date());
        transfer.setAccCodeType(to.getAccCodeType());
        transfer.setBalanceBefore(fromBalance);
        transfer.setBalanceAfter(newFromBalance);
        transferService.add(transfer);
    }

}
