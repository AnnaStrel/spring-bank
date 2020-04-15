package ru.sgu.springbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgu.springbank.entity.Account;
import ru.sgu.springbank.entity.Transfer;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    List<Transfer> findAllByFromInOrToIn(List<Account> accountsFrom, List<Account> accountsTo);

}
