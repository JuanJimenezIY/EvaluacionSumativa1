package com.distribuida.servicios;

import com.distribuida.db.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


import java.util.List;
@ApplicationScoped
public class ServicioBookImpl implements ServicioBook{


    @Inject
    private EntityManager em;
    @Override
    public Book findById(Integer id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> findAll() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @Override
    public void insert(Book book) {
        em.persist(book);

    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    public void delete(Integer id) {
        Book book = em.find(Book.class, id);
        if (book != null) {
            em.remove(book);
        }
    }
}
