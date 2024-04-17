package com.shop4e.shop.repository.impl;

import com.shop4e.shop.domain.Book;
import com.shop4e.shop.repository.CustomBookRepository;
import com.shop4e.shop.web.request.BookSearchRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class CustomBookRepositoryImpl implements CustomBookRepository {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public Page<Book> searchBooks(BookSearchRequest searchRequest, Pageable pageable) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Book> cq = cb.createQuery(Book.class);

    Root<Book> book = cq.from(Book.class);

    List<Predicate> predicates = new ArrayList<>();

    Predicate notDeleted = cb.isNull(book.get("deletedAt"));

    predicates.add(notDeleted);

    if (searchRequest.getISBN() != null && !searchRequest.getISBN().trim().isEmpty()) {
      Predicate isbn = cb.equal(book.get("ISBN"), searchRequest.getISBN());
      predicates.add(isbn);
    }

    if (searchRequest.getAuthor() != null && !searchRequest.getAuthor().trim().isEmpty()) {
      Predicate author = cb.like(book.get("author"), "%" + searchRequest.getAuthor() + "%");
      predicates.add(author);
    }

    if (searchRequest.getCategory() != null && !searchRequest.getCategory().trim().isEmpty()) {
      Predicate bookCategory = cb.equal(book.get("bookCategory").get("id"), UUID.fromString(searchRequest.getCategory()));
      predicates.add(bookCategory);
    }

    if (searchRequest.getPublishedFrom() != null && searchRequest.getPublishedTo() != null) {
      Predicate publish = cb.between(book.get("publishedAt"), searchRequest.getPublishedFrom(),
          searchRequest.getPublishedTo());
      predicates.add(publish);
    } else if (searchRequest.getPublishedFrom() != null) {
      Predicate publish = cb.greaterThanOrEqualTo(book.get("publishedAt"),
          searchRequest.getPublishedFrom());
      predicates.add(publish);
    } else if (searchRequest.getPublishedTo() != null) {
      Predicate publish = cb.lessThanOrEqualTo(book.get("publishedAt"),
          searchRequest.getPublishedTo());
      predicates.add(publish);
    }

    cq.where(predicates.toArray(new Predicate[0]));

    if (pageable.getSort().isSorted()) {
      List<Order> orders = createOrders(cb, book, pageable.getSort());
      cq.orderBy(orders);
    }

    //TODO add pageable
    List<Book> result = entityManager.createQuery(cq)
        .setFirstResult((int) pageable.getOffset())
        .setMaxResults(pageable.getPageSize())
        .getResultList();

    CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
    countQuery.select(cb.count(countQuery.from(Book.class)));

    Long total = entityManager.createQuery(countQuery).getSingleResult();

    //TODO add return value
    return new PageImpl<>(result, pageable, total);
  }

  private List<Order> createOrders(CriteriaBuilder cb, Root<Book> root, Sort sort) {
    List<Order> orders = new ArrayList<>();
    for (Sort.Order order : sort) {
      if (order.isAscending()) {
        orders.add(cb.asc(root.get(order.getProperty())));
      } else {
        orders.add(cb.desc(root.get(order.getProperty())));
      }
    }

    return orders;
  }
}
