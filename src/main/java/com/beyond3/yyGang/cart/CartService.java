package com.beyond3.yyGang.cart;

import com.beyond3.yyGang.nsupplement.NSupplements;
import com.beyond3.yyGang.nsupplement.NSupplementsRepository;
import com.beyond3.yyGang.user.UserRepository;
import com.beyond3.yyGang.user.Users;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartOptionRepository cartOptionRepository;
    private final NSupplementsRepository nSupplementsRepository;
    private final UserRepository userRepository;

    // 장바구니 담기
    @Transactional
    public Long addCart(CartOptionDto cartOptionDto, String email) {

        Users user = userRepository.findByEmail(email);
        Cart cart = cartRepository.findCartByUserId(user.getUserId());

        // 장바구니가 존재하지 않을 때 (회원가입을 하면 카트 하나를 바로 생성해야 하는데 이 부분은 확인할 것)
        if (cart == null) {
            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }

        NSupplements nSupplements = nSupplementsRepository.findById(cartOptionDto.getProductId()).orElseThrow(EntityNotFoundException::new);
        CartOption cartOption = cartOptionRepository.findByCartIdAndProductId(cart.getCartId(), nSupplements.getProductsId());


        //해당 상품이 장바구니에 없으면 생성 후 추가
        if (cartOption == null) {
            cartOption = CartOption.createCartSupplements(cart, nSupplements, cartOptionDto.getCount());
            cartOptionRepository.save(cartOption);
        }

        //상품이 있으면 수량 업데이트
        else {
            cartOption.updateQuantity(cartOptionDto.getCount());
        }

        return cartOption.getCartOptionID();
    }

    // 장바구니 조회 기능
    public List<CartListDto> getCartList(String email) {

        List<CartListDto> cartListDtos = new ArrayList<>();

        Users users = userRepository.findByEmail(email);
        Cart cart = cartRepository.findCartByUserId(users.getUserId());

        // 장바구니가 없다면 빈 리스트를 반환 회원과 장바구니는 동시에 생성되니 필요는 없겠지만
        // 혹시 모를 예외처리(NullPointException)를 위함
        if (cart == null) {
            return cartListDtos;
        }

        // CartOptionRepository 확인할 것
        cartListDtos = cartOptionRepository.findCartListDto(cart.getCartId());
        return cartListDtos;
    }

    @Transactional
    // 장바구니 상품 수량 업데이트
    public void updateCartProduce(Long cartOptionId, int count) {
        CartOption cartOption = cartOptionRepository.findById(cartOptionId).orElseThrow(EntityNotFoundException::new);
        cartOption.updateQuantity(count);
    }

    @Transactional
    // 장바구니 상품 삭제
    public void deleteCartProduct(Long cartOptionId) {
        CartOption cartOption = cartOptionRepository.findById(cartOptionId).orElseThrow(EntityNotFoundException::new);
        cartOptionRepository.delete(cartOption);
    }

//    @Transactional
//    //장바구니 상품 주문
//    public Long orderCartProduct(List<CartOrderDto> cartOrderDtoList, String email) {
//
//        List<OrderDto> orderDtoList = new ArrayList<>();
//
//        /*
//            1. OrderDto 갹체 생성
//            2. CartOrderDto에 있는 상품Id를 사용해서 CartOption 조회
//            3. OrderDto에서 상품Id, 수량 설정
//            4. OrderDto를 CartOrderList에 추가
//         */
//        for(CartOrderDto cartOrderDto : cartOrderDtoList) {
//            OrderDto orderDto = new OrderDto();
//            CartOption cartOption = cartOptionRepository.findById(cartOrderDto.getCartProductId()).orElseThrow(EntityNotFoundException::new);
//            orderDto.setProductId(cartOption.getNSupplements().getProductsId());
//            orderDto.setCount(cartOption.getQuantity());
//            orderDtoList.add(orderDto);
//        }
//
//        Long orderId = orderService.orders(orderDtoList, email);
//
//        // 주문한 장바구니 상품 제거
//        for(CartOrderDto cartOrderDto  : cartOrderDtoList) {
//            CartOption cartOption = cartOptionRepository.findById(cartOrderDto.getCartProductId()).orElseThrow(EntityNotFoundException::new);
//            cartOptionRepository.delete(cartOption);
//        }
//
//        // 주문 아이디 반환
//        return orderId;
//    }

}
