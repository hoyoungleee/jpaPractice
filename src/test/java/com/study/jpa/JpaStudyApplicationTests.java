package com.study.jpa;

import com.study.jpa.chap01.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional // 테스트 이후 rollback이 진행되면서 테스트 이전의 DB 환경으로 돌아갑니다.
@Rollback(false) // 롤백 방지 -> 테스트 결과가 DB에 그대로 반영.
class JpaStudyApplicationTests {

    @Autowired
    EntityManager em; //JPA에서 엔터티를 다룰 수 있게 해주는 객체.


    @Test
    @DisplayName("상품을 데이터베이스에 저장한다.") //테스트 목표
    void saveTest() {
        // given: 준비 -> 테스트에 사용할 변수, 입력값 등을 정의하는 곳.
        Product product = new Product();
        product.setName("신발");
        product.setPrice(90000);
        product.setCategory(Product.Category.FASHION);
        // when: 실행 -> 테스트를 실행하는 메인 로직
        em.persist(product);

        // then: 검증 -> 예상한 값, 실제 실행한 값을 확인하는 부분.
    }

    @Test
    @DisplayName("아이디에 맞는 상품가져오기")
    void findByIdTest() {
        // given: 준비 -> 테스트에 사용할 변수, 입력값 등을 정의하는 곳.
        Long id = 1L;
        // when: 실행 -> 테스트를 실행하는 메인 로직
        Product product = em.find(Product.class, id);
        // then: 검증 -> 예상한 값, 실제 실행한 값을 확인하는 부분.
        assertEquals(product.getName(),"신발");
    }

    @Test
    @DisplayName("영속성 컨텍스트의 1차 캐시 사용")
    void persistTest() {
        // given: 준비 -> 테스트에 사용할 변수, 입력값 등을 정의하는 곳.
        Product product = new Product();
        product.setName("짜장면");
        product.setPrice(8000);
        product.setCategory(Product.Category.FOOD);
        // when: 실행 -> 테스트를 실행하는 메인 로직
        em.persist(product);
        Product foundProd = em.find(Product.class, 2L);
        // then: 검증 -> 예상한 값, 실제 실행한 값을 확인하는 부분.
        assertEquals(8000, foundProd.getPrice());
    }
}
