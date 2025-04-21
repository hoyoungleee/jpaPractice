package com.study.jpa.chap04.repository;

import com.study.jpa.chap04.entity.Department;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class DepartmentRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    @DisplayName("부서정보를 조회하면 해당 부서원들도 함께 조회되어야 한다.")
    void testFindDept() {
        // given: 준비 -> 테스트에 사용할 변수, 입력값 등을 정의하는 곳.
        Long id = 2L;
        // when: 실행 -> 테스트를 실행하는 메인 로직
        Department department = departmentRepository.findById(id).orElseThrow();
        // then: 검증 -> 예상한 값, 실제 실행한 값을 확인하는 부분.
        System.out.println("\n\n\n");
        System.out.println("department = " + department);
        System.out.println(department.getEmployees());
        System.out.println("\n\n\n");
    }
}