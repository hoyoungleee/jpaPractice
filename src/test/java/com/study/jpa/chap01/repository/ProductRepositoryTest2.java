package com.study.jpa.chap01.repository;

import com.study.jpa.chap01.entity.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class ProductRepositoryTest2 {

    @Autowired
    ProductRepository productRepository;

    @Test
    @DisplayName("상품을 데이터 베이스에 저장한다")
    void saveTest() {
        // given: 준비 -> 테스트에 사용할 변수, 입력값 등을 정의하는 곳.

        //Builder 패턴을 이용한 객체 생성. (Lombok 사용)
        Product p = Product.builder()
                .name("떡볶이")
                .price(3000)
                .category(Product.Category.FOOD)
                .build();

        // when: 실행 -> 테스트를 실행하는 메인 로직
        //INSERT 후 저장된 데이터의 반환
        Product save = productRepository.save(p);

        // then: 검증 -> 예상한 값, 실제 실행한 값을 확인하는 부분.
    }

    @Test
    @DisplayName("1번 상품을 삭제한다.")
    void deleteTest() {
        // given: 준비 -> 테스트에 사용할 변수, 입력값 등을 정의하는 곳.
        Long id = 1L;
        // when: 실행 -> 테스트를 실행하는 메인 로직
        productRepository.deleteById(id);
        // then: 검증 -> 예상한 값, 실제 실행한 값을 확인하는 부분.
        Optional<Product> product = productRepository.findById(id);
        System.out.println(product.isPresent());
        product.ifPresent(System.out::println);
    }

    @Test
    @DisplayName("상품 전체 조회하면 개수는 4개여야 한다.")
    void selectAllTest() {
        // given: 준비 -> 테스트에 사용할 변수, 입력값 등을 정의하는 곳.

        // when: 실행 -> 테스트를 실행하는 메인 로직
        List<Product> all = productRepository.findAll();
        // then: 검증 -> 예상한 값, 실제 실행한 값을 확인하는 부분.
        all.forEach(System.out::println);
        assertEquals(3, all.size());
    }

    @Test
    @DisplayName("3번 상품의 이름과 가격을 변경해야 한다.")
    void updateTest() {
        // given: 준비 -> 테스트에 사용할 변수, 입력값 등을 정의하는 곳.
        Long id = 3L;
        String newName = "삼겹살";
        int newPrice = 14000;

        // when: 실행 -> 테스트를 실행하는 메인 로직
        Optional<Product> optional = productRepository.findById(id);

        // optional이 감싸고 있는 객체가 비어있을 경우 예외가 발생.
        // 존재한다면 해당 객체를 리턴.
        Product product =
                optional.orElseThrow(() -> new RuntimeException("조회된 객체가 없음!"));
        product.setName(newName);
        product.setPrice(newPrice);

        // jpa는 따로 update 메서드를 제공하지 않음.
        // 조회한 객체의 필드를 setter로 변경하면 자동으로 update가 나갑니다. (Dirty check)
        productRepository.save(product);

        // then: 검증 -> 예상한 값, 실제 실행한 값을 확인하는 부분.
    }
}