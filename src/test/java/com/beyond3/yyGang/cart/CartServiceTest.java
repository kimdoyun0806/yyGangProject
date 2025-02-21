package com.beyond3.yyGang.cart;

import com.beyond3.yyGang.nsupplement.NSupplements;
import com.beyond3.yyGang.nsupplement.NSupplementsRepository;
import com.beyond3.yyGang.user.UserRepository;
import com.beyond3.yyGang.user.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.apache.catalina.User;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
class CartServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    CartService cartService;

    @Autowired
    NSupplementsRepository nSupplementsRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    private CartOptionRepository cartOptionRepository;

    @Test
    @DisplayName("장바구니 담기 테스트")
    public void addCart(){
        NSupplements product = saveProduct();
        Users user = createUser();

        CartOptionDto cartOptionDto = new CartOptionDto();
        cartOptionDto.setCount(5);
        cartOptionDto.setProductId(product.getProductsId());

        Long cartItemId = cartService.addCart(cartOptionDto, user.getEmail());

        System.out.println("✅ cartItemId: " + cartItemId);
        if (cartItemId == null) {
            throw new RuntimeException("cartItemId가 null입니다! cartService.addCart()가 올바르게 실행되지 않았습니다.");
        }

        CartOption cartOption = cartOptionRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("CartOption not found!"));
        em.flush();
        em.clear();


        assertEquals(product.getProductsId(), cartOption.getNSupplements().getProductsId());
        assertEquals(cartOptionDto.getCount(),cartOption.getQuantity());
    }

    public Users createUser() {
        Users user = new Users();
        user.setEmail("naver.com");
        em.persist(user);
        em.flush();
        return user;
    }

    public NSupplements saveProduct() {
        NSupplements supplement = NSupplements.builder()
                .productName("비타민D")
                .caution("과다 섭취 시 신장 문제 발생 가능")
                .brand("영양제브랜드")
                .price(30000)
                .build();
        em.persist(supplement);
        em.flush();
        return supplement; // 객체 반환
    }




//    @Test
//    public Long addCart(CartOptionDto cartOptionDto, String userEmail) {
//        Users user = userRepository.findByEmail(userEmail).orElseThrow(EntityNotFoundException::new))
//
//        Cart cart = cartRepository.findCartByUserId(user.getUserId())
//                .orElseGet(() -> {
//                    Cart newCart = new Cart();
//                    newCart.setUser(user);
//                    em.persist(newCart);
//                    return newCart;
//                });
//
//        NSupplements supplement = nSupplementsRepository.findById(cartOptionDto.getProductId())
//                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + cartOptionDto.getProductId()));
//
//        CartOption cartOption = new CartOption();
//        cartOption.setCart(cart);
//        cartOption.setNSupplements(supplement);
//        cartOption.setQuantity(cartOptionDto.getCount());
//
//        em.persist(cartOption);  // ✅ 이 코드가 실행되는지 확인해야 합니다.
//        em.flush();  // ✅ 데이터베이스에 즉시 반영하도록 강제.
//
//        System.out.println("✅ 저장된 CartOption ID: " + cartOption.getId()); // 로그 출력
//
//        return cartOption.getId();
//    }


}