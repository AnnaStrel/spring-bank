package ru.sgu.springbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgu.springbank.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

    User findByPhone(String phone);

    User findByLoginOrPhone(String login, String phone);

    boolean existsByLogin(String login);

    boolean existsByLoginOrPhone(String login, String phone);

}
