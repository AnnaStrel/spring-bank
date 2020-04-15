package ru.sgu.springbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sgu.springbank.entity.Authority;
import ru.sgu.springbank.entity.AuthorityType;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByName(AuthorityType authorityName);

}
