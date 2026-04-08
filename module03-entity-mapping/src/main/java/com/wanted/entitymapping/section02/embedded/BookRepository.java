package com.wanted.entitymapping.section02.embedded;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Book book) {
        em.persist(book);
    }
}
