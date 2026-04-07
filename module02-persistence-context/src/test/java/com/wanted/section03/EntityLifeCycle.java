package com.wanted.section03;

import com.wanted.section02.Menu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

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
}
