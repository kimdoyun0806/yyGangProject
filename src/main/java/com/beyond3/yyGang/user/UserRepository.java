package com.beyond3.yyGang.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(Users user) {
        em.persist(user);
    }

    public Users findOne(Long id) {
        return em.find(Users.class, id);
    }

    public List<Users> findAll() {
        return em.createQuery("select  m from Users m", Users.class)
                .getResultList();
    }

    public List<Users> findByName(String name) {
        return em.createQuery("select m from Users m where m.name = :name",Users.class)
                .setParameter("name",name)
                .getResultList();
    }

    // email로 장바구니를 소유하고 있는지 확인해야하기에 필요함
    public Users findByEmail(String email) {
        return em.createQuery("SELECT u FROM Users u WHERE u.email = :email", Users.class)
                .setParameter("email", email)
                .getSingleResult();
    }

}
