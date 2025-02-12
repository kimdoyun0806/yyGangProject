package com.beyond3.yyGang.review;

import com.beyond3.yyGang.nsupplement.NSupplements;
import com.beyond3.yyGang.user.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "review")
public class Review {
    // 상품 리뷰 테이블

    @Id
    @GeneratedValue
    private Long reviewId;  // 리뷰 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users; // 회원 - 외래키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "products_id")
    private NSupplements nSupplements;  // 상품 ID - 외래키

    @Column(columnDefinition = "TEXT")
    private String contents; // 리뷰 내용

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime date; // 작성 날짜 -> 수정 시 작성 날짜 변경되게

}
