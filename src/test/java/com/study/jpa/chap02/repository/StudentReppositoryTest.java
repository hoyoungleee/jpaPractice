package com.study.jpa.chap02.repository;

import com.study.jpa.chap02.entity.Student;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class StudentReppositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach // 테스트 메서드 이전에 실행되는 메서드. 결과 저장 안됨.
    void insertData() {
        Student s1 = Student.builder()
                .name("쿠로미")
                .city("청양군")
                .major("경제학")
                .build();
        Student s2 = Student.builder()
                .name("춘식이")
                .city("서울시")
                .major("컴퓨터공학")
                .build();
        Student s3 = Student.builder()
                .name("어피치")
                .city("제주도")
                .major("화학공학")
                .build();
        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);
    }

    @Test
    @DisplayName("이름이 춘식이인 학생의 모든 정보를 조회한다.")
    void findByName() {
        // given: 준비 -> 테스트에 사용할 변수, 입력값 등을 정의하는 곳.
        String name = "춘식이";
        // when: 실행 -> 테스트를 실행하는 메인 로직
        List<Student> list = studentRepository.findByName(name);
        // then: 검증 -> 예상한 값, 실제 실행한 값을 확인하는 부분.
        assertEquals(list.size(), 1);
        System.out.println(list.get(0));
    }

    @Test
    @DisplayName("여러가지 조건을 쿼리메서드로 조회")
    void queryMethodTest() {
        // given: 준비 -> 테스트에 사용할 변수, 입력값 등을 정의하는 곳.
        String city = "제주도";
        String major = "공학";
        // when: 실행 -> 테스트를 실행하는 메인 로직
        List<Student> students = studentRepository.findByMajorEndingWith(major);
        // then: 검증 -> 예상한 값, 실제 실행한 값을 확인하는 부분.
        System.out.println("\n\n\n");
        System.out.println(students.get(0));
        System.out.println("\n\n\n");

    }
}
