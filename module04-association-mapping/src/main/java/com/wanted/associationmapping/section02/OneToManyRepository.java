package com.wanted.associationmapping.section02;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class OneToManyRepository {

    @PersistenceContext
    private EntityManager em;

    public Category find(int categoryCode) {

         return em.find(Category.class, categoryCode);
    }
}
