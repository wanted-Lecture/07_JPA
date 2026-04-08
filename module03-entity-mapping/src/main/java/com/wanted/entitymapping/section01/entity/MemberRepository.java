package com.wanted.entitymapping.section01.entity;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;


/* COMMENT.
*   JDBC 프로젝트에서 DB 와 접근하는 객체를 DAO 라고 표현했다.
*   Spring 환경에서는 JPA 를 사용할 때 DAO 게층을
*   @Repository 로 표현한다.
*   - Mybatis 기술을 사용하게 되면 @Mapper 라고 표현한다.
* */
@Repository
public class MemberRepository {

    /* COMMENT.
    *   해당 클래스는 엔티티 매니저 주입 받아
    *   영속성 컨텍스트가 Entity 클래스 관리할 수 있도록 한다.
    * */
    @PersistenceContext
    private EntityManager manager;


    public void save(Member member) {
        manager.persist(member);
    }
}
