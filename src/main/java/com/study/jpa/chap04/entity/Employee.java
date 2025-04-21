package com.study.jpa.chap04.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString(exclude = "department")
@EqualsAndHashCode(of="id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="tbl_emp")
public class Employee  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="emp_id")
    private Long id;

    @Column(nullable=false, name="emp_name")
    private String name;

    //EAGER : 해당 필드를 쓰던가 말던가 무조건 조인
    //LAZY : 해당 필드를 사용하지 않으면 실행하지 않음.
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "dept_id") //FK 컬럼명 (연관테이블의 컬럼명과 일치하게)
    private Department department;

}
