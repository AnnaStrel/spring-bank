package ru.sgu.springbank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sgu.springbank.entity.Account;
import ru.sgu.springbank.entity.Transfer;
import ru.sgu.springbank.repository.TransferRepository;

import java.util.List;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    public AccountService accountService;

    public List<Transfer> getAll() {
        return transferRepository.findAll();
    }

    public List<Transfer> getAllByAccount(List<Account> accounts) {
        return transferRepository.findAllByFromInOrToIn(accounts, accounts);
    }

    public Transfer add(Transfer transfer) {
        return transferRepository.save(transfer);
    }

}
