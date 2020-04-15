package ru.sgu.springbank.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "authority_seq")
    @SequenceGenerator(name = "authority_seq", sequenceName = "authority_seq")
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private AuthorityType name;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private List<User> users;

    public Authority() {
    }

    public Authority(AuthorityType typeName) {
        this.name = typeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AuthorityType getName() {
        return name;
    }

    public void setName(AuthorityType name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

}