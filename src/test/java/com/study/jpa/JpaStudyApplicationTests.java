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
        product.setName("탕수육");
        product.setPrice(18000);
        product.setCategory(Product.Category.FOOD);
        // when: 실행 -> 테스트를 실행하는 메인 로직
        em.persist(product);
        Product foundProd = em.find(Product.class, 1L);
        // then: 검증 -> 예상한 값, 실제 실행한 값을 확인하는 부분.
        assertEquals(90000, foundProd.getPrice());
    }

    @Test
    @DisplayName("특정 상품의 가격을 수정한다.")
    void updateTest() {
        // given: 준비 -> 테스트에 사용할 변수, 입력값 등을 정의하는 곳.
        Product noodle = em.find(Product.class, 2L);
        // when: 실행 -> 테스트를 실행하는 메인 로직
        noodle.setPrice(5000);
        noodle.setName("미니짜장면");
        // 엔터티를 변경 후에 영속성 컨텍스트에 넣어놓으면
        // 변경사항을 감지하여 update를 반영합니다.
        em.persist(noodle);

        em.flush(); //지금까지 반영된 영속성 컨텍스트를 DB에 바로 적용.
        em.clear(); //영속성 컨텍스트 비우기

        Product foundProd = em.find(Product.class, 2L);
        // then: 검증 -> 예상한 값, 실제 실행한 값을 확인하는 부분.
    }
}
