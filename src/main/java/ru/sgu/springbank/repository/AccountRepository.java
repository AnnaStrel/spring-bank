package ru.sgu.springbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgu.springbank.entity.Account;
import ru.sgu.springbank.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findById(UUID id);

    Optional<Account> findByUserAndId(User user, UUID id);

    List<Account> findAllByUser(User user);

    boolean existsByUser(User user);

}
