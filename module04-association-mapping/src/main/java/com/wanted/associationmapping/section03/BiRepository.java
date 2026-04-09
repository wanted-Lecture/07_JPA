package com.wanted.associationmapping.section03;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class BiRepository {

    @PersistenceContext
    private EntityManager em;

    public Menu findMenu(int menuCode) {

        return em.find(Menu.class, menuCode);
    }

    public Category findCategory(int categoryCode) {

        return em.find(Category.class, categoryCode);
    }

    public void saveMenu(Menu newMenu) {

        em.persist(newMenu);
    }

    public void saveCategory(Category category) {

        em.persist(category);
    }
}
