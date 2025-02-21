package com.beyond3.yyGang.cart;

import com.beyond3.yyGang.user.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Cart cart) {
        em.persist(cart);
    }

    public Cart findOne(Long id) {
        return em.find(Cart.class, id);
    }

//    public Optional<Cart> findCartByUserId(Long userId) {
//        return em.createQuery("SELECT c FROM Cart c WHERE c.user.id = :userId", Cart.class)
//                .setParameter("userId", userId)
//                .getResultStream()
//                .findFirst();


    public Cart findCartByUserId(Long userId) {
        List<Cart> cartList = em.createQuery("SELECT c FROM Cart c WHERE c.user.id = :userId", Cart.class)
                .setParameter("userId", userId)
                .getResultList();
        return cartList.getFirst();
    }



}
