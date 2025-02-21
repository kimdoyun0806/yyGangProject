package com.beyond3.yyGang.nsupplement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NSupplementsRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(NSupplements nSupplements) {
        if (nSupplements.getProductsId() == null) {
            em.persist(nSupplements);
        } else {
            em.merge(nSupplements);
        }
    }

    public Optional<NSupplements> findById(Long productId) {
        NSupplements nSupplements = em.find(NSupplements.class, productId);
        return Optional.ofNullable(nSupplements);
    }


    public NSupplements findItemById(Long id) {
        return em.find(NSupplements.class, id);
    }

    public List<NSupplements> findAll() {
        return em.createQuery("select i from NSupplements i", NSupplements.class)
                .getResultList();
    }

}
