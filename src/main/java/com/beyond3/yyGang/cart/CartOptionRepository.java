package com.beyond3.yyGang.cart;

import com.beyond3.yyGang.nsupplement.NSupplements;
import com.beyond3.yyGang.user.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartOptionRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(CartOption cartOption) {
        em.persist(cartOption);
    }

    public Optional<CartOption> findById(Long Id) {
        CartOption cartOption= em.find(CartOption.class, Id);
        return Optional.ofNullable(cartOption);
    }

    public void delete(CartOption cartOption) {
        em.remove(cartOption);
    }

    // cartId와 productId로 CartOption 검색
    public CartOption findByCartIdAndProductId(Long cartId, Long productId) {
        List<CartOption> cartOptionList = em.createQuery("SELECT co FROM CartOption co WHERE co.cart.id = :cartId AND co.nSupplements.id = :productId", CartOption.class)
                .setParameter("cartId", cartId)
                .setParameter("productId", productId)
                .getResultList();
        return cartOptionList.getFirst();
    }

    // 회원 하나의 장바구니 리스트 JPQL 쿼리문(다시 확인할 것)
    public List<CartListDto> findCartListDto(Long cartId) {
        String jpql = "SELECT new com.beyond3.yyGang.cart.CartListDto(ci.id, ci.item.name, ci.price, ci.quantity) " +
                "FROM CartOption ci " +
                "WHERE ci.cart.id = :cartId";
        TypedQuery<CartListDto> query = em.createQuery(jpql, CartListDto.class);
        query.setParameter("cartId", cartId);
        return query.getResultList();
    }




    public List<CartOption> findCartOptionByProductId(Long productId) {
        return em.createQuery("SELECT co FROM CartOption co WHERE co.nSupplements.id = :productId", CartOption.class)
                .setParameter("productId", productId)
                .getResultList();
    }



}
