package com.beyond3.yyGang.nsupplement;

import com.beyond3.yyGang.cart.Cart;
import com.beyond3.yyGang.cart.CartOption;
import com.beyond3.yyGang.nutrientQuestion.NQuestion;
import com.beyond3.yyGang.order.OrderOption;
import com.beyond3.yyGang.review.Review;
import com.beyond3.yyGang.ageCategory.ACategory;
import com.beyond3.yyGang.hfunction.HFunctionalCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@Table(name = "n_supplements")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NSupplements {

    // 영양제
    @Id
    @GeneratedValue
    @Column(name = "products_id")
    private Long productsId;  // 리뷰 아이디

    private String productName; // 상품 이름

    @Column(columnDefinition = "TEXT")
    private String caution; // 주의 사항

    private String brand;  // 브랜드

    private int price;  // 상품 가격

    @OneToMany(mappedBy = "nSupplements")
    private List<Review> reviews;

    @OneToMany(mappedBy = "nSupplements")
    private List<OrderOption> orderOptions;

    @OneToMany(mappedBy = "supplements")
    private List<NQuestion> nQuestions;

    @OneToMany(mappedBy = "nSupplements")
    private List<HFunctionalCategory> hFunctionalCategories;

    @OneToMany(mappedBy = "nSupplements")
    private List<ACategory> aCategories;

    @OneToMany(mappedBy = "nSupplements")
    private List<CartOption> cartOptions;


    // 생성 메소드
    public static NSupplements createNSupplements(String productName, String caution, String brand, int price) {
        return NSupplements.builder()
                .productName(productName)
                .caution(caution)
                .brand(brand)
                .price(price)
                .build();
    }

}
