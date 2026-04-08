package com.wanted.section03;

import com.wanted.section02.Menu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityLifeCycle {
    private static EntityManagerFactory factory;
    private EntityManager manager;

    @BeforeAll
    static void initFactory(){
        factory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
        // 요청 당 1개씩만 만들기 위해서
    void initManager(){
        manager = factory.createEntityManager();
    }

    @AfterEach
    void closeManager() {
        if (manager != null) {
            manager.close();
        }
    }

    @AfterAll
    static void closeFactory() {
        if (factory != null) {
            factory.close();
        }
    }

    @Test
    void 비영속_테스트_메서드() {

        /* COMMENT.
        *   객체를 생성하면(new), 영속성 컨텍스트와는 전혀 관련 없는 비영속 상태이다.
        * */

        // given
        Menu foundMenu = manager.find(Menu.class, 1);
        Menu newMenu = new Menu();
        newMenu.setMenuCode(foundMenu.getMenuCode());
        newMenu.setMenuName(foundMenu.getMenuName());
        newMenu.setMenuPrice(foundMenu.getMenuPrice());
        newMenu.setCategoryCode(foundMenu.getCategoryCode());
        newMenu.setOrderableStatus(foundMenu.getOrderableStatus());
        // 자료형 같고 값도 똑같음.

        boolean isTrue;
        isTrue = foundMenu.equals(newMenu);

        Assertions.assertFalse(isTrue);

    }

    @Test
    void 영속성_테스트_메서드() {

        // given
        Menu foundMenu = manager.find(Menu.class, 1);
        Menu newMenu = manager.find(Menu.class, 1);

        // when
        boolean isTrue = foundMenu.equals(newMenu);

        Assertions.assertTrue(isTrue);
    }

    @Test
    void 준영속_detach_테스트() {

        //given
        Menu foundMenu1 = manager.find(Menu.class, 11);
        Menu foundMenu2 = manager.find(Menu.class, 12);

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        //when
        try {
            manager.detach(foundMenu2);
            foundMenu1.setMenuPrice(5000);
//            foundMenu2.setMenuPrice(5000);
        } catch (Exception e) {
            transaction.rollback();
        }


        //then
        assertEquals(5000, manager.find(Menu.class, 11).getMenuPrice());
//        assertEquals(5000, manager.find(Menu.class, 12).getMenuPrice());

    }

    @Test
    void 삭제_remove_테스트(){
        /* comment.
         *   remove() : 엔티티를 영속성 컨택스트 및 DB 에서 삭제한다.
         *   단, 트랜잭션을 제어하지 않으면 영구 반영되지 않는다. */

        // DB 조회로 영속성 컨텍스트에 적재된 것
        Menu foundMenu = manager.find(Menu.class,2);

        manager.remove(foundMenu);

        // 영속성 컨텍스트를 조회했지만, 삭제 상태라 null을 반환
        Menu refoundMenu = manager.find(Menu.class, 2);

        assertEquals(2, foundMenu.getMenuCode());
        assertEquals(null, refoundMenu);
    }

    @Test
    void 병합_merger_수정_테스트(){
        // 1. 2번 menuCode DB 에서 찾기 주소값 : x001
        Menu detachMenu = manager.find(Menu.class, 2);
        System.out.println("처음 찾은, detachMenu.hashCode() = " + detachMenu.hashCode());

        // 2. 2번 menuCode 임시 제외하면서 주소값이 변경 : x001
        manager.detach(detachMenu);
        System.out.println("detach한, detachMenu.hashCode() = " + detachMenu.hashCode());

        // x002의 menuName을 보쌈으로 수정
        detachMenu.setMenuName("보쌈");

        // 다시 영속성 컨텍스트에서 menuCode 2번 찾기 but, detach해서 찾지 못함
        // menuCode는 2번이나 주소값 : x002인 값을 DB 새로 찾기
        Menu refoundMenu = manager.find(Menu.class, 2);
        System.out.println("refoundMenu.hashCode() = " + refoundMenu.hashCode());

        manager.merge(detachMenu);

        /* COMMENT.
         *   1. Hash 값의 변화 : 우선, JPA는 영속성 컨텍스트 내에 있는 관리 중인 엔티티 x002을 찾는다.
         *   이때도 detachMenu(x001)의 주소값은 바뀌지 않고, 준영속 상태로 남아있다. (유령 상태)
         *   대신 merge의 결과로 반환되는 객체가 영속성 컨텍스트 내에 있는 x002일 뿐이다.
         *   2. 실제 값 변화 : 준영속 상태의 x001의 name을 영속 상태 x002 name으로 복사 한다.
         *   -> 추후 트랜잭션이 commit될 때, 영속성 컨텍스트는 x002의 변경된 값을 감지하고 DB에 Update쿼리 실행
         * */
        Menu mergeMenu = manager.find(Menu.class, 2);
        System.out.println("mergeMenu.hasCode() = " + mergeMenu.hashCode());

        assertEquals("보쌈", mergeMenu.getMenuName());

    }


}
