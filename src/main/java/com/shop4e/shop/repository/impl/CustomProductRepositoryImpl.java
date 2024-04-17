package com.shop4e.shop.repository.impl;

import com.shop4e.shop.domain.Product;
import com.shop4e.shop.domain.ProductProjection;
import com.shop4e.shop.domain.enumeration.CurrencyType;
import com.shop4e.shop.repository.CustomProductRepository;
import com.shop4e.shop.repository.util.ProductProjectionMapping;
import com.shop4e.shop.web.request.ProductFilter;
import com.shop4e.shop.web.response.ProductCombinedResponse;
import com.shop4e.shop.web.response.ProductFilterResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class CustomProductRepositoryImpl implements CustomProductRepository {

  @PersistenceContext
  private EntityManager entityManager;

  //TODO extract all field names as constants

  @Override
  public Page<Product> findAllByCategory(String categoryId, Pageable pageable) {
    StringBuilder query = new StringBuilder(
        "SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.deletedAt IS null");
    String countQuery = "SELECT COUNT(p) FROM Product p WHERE p.category.id = :categoryId AND p.deletedAt IS null";

    Sort sort = pageable.getSort();
    if (sort != null && sort.isSorted()) {
      query.append(" ORDER BY");
      for (Sort.Order order : sort) {
        query.append(" ").append(order.getProperty()).append(" ").append(order.getDirection());
      }
    }

    List<Product> products = entityManager.createQuery(query.toString(), Product.class)
        .setParameter("categoryId", UUID.fromString(categoryId))
        .setFirstResult((int) pageable.getOffset())
        .setMaxResults(pageable.getPageSize())
        .getResultList();

    Long total = entityManager.createQuery(countQuery, Long.class)
        .setParameter("categoryId", UUID.fromString(categoryId))
        .getSingleResult();

    return new PageImpl<>(products, pageable, total);
  }

  @Override
  public Page<ProductFilterResponse> findAllProducts(String userId, ProductFilter productFilter,
      Pageable pageable) {
    StringBuilder query = new StringBuilder(
        "SELECT p.*,\n"
        + "GROUP_CONCAT(ph.location) AS images,\n"
        + "(SELECT f.favourite FROM favourite f WHERE f.user_id = :user_id AND f.product_id = p.id) AS favourite,\n"
        + "CASE\n"
        + "    WHEN p.currency = 0 THEN p.price * 0.5\n"
        + "    ELSE p.price\n"
        + "END AS adjusted_price\n"
        + "FROM product p\n"
        + "LEFT JOIN photo ph\n"
        + "ON p.id = ph.product_id\n"
        + "WHERE p.deleted_at IS NULL"
    );

    String countQuery = "SELECT COUNT(*) FROM product p WHERE p.deleted_at IS NULL";

    if (productFilter.getTitle() != null) {
      query.append(" AND p.title LIKE :title");
    }

    if (productFilter.getCurrency() != null) {
      query.append(" AND p.currency = :currency");
    }

    if (productFilter.getCategoryId() != null) {
      query.append(" AND p.category_id = :category");
    }

    if (productFilter.getSellerId() != null) {
      query.append(" AND p.seller_id = :seller");
    }

    if (productFilter.getPriceFrom() != null && productFilter.getPriceTo() != null) {
      query.append(" AND p.price BETWEEN :price_from AND :price_to");
    } else if (productFilter.getPriceFrom() != null && productFilter.getPriceTo() == null) {
      query.append(" AND p.price >= :price_from");
    } else if (productFilter.getPriceTo() != null && productFilter.getPriceFrom() == null) {
      query.append(" AND p.price <= :price_to");
    }

    query.append(" GROUP BY p.id");

    Sort sort = pageable.getSort();
    if (sort != null && sort.isSorted()) {
      query.append(" ORDER BY");
      ArrayList<String> orderBy = new ArrayList<>();
      for (Sort.Order order : sort) {
        StringBuilder orderClause = new StringBuilder();
        if (order.getProperty().equals("price")) {
          orderClause.append(" ").append("adjusted_price").append(" ").append(order.getDirection());
        } else {
          orderClause.append(" ").append("p." + order.getProperty()).append(" ").append(order.getDirection());
        }

        orderBy.add(orderClause.toString());
      }
      query.append(String.join(",", orderBy));
    }

    Query nativeQuery = entityManager.createNativeQuery(query.toString(), "ProductProjectionMapping");
    Query countNativeQuery = entityManager.createNativeQuery(countQuery.toString());

    nativeQuery.setParameter("user_id", userId);

    if (productFilter.getTitle() != null) {
      nativeQuery.setParameter("title", "%" + productFilter.getTitle() + "%");
    }

    if (productFilter.getCurrency() != null) {
      nativeQuery.setParameter("currency",
          CurrencyType.valueOf(productFilter.getCurrency()).ordinal());
    }

    if (productFilter.getCategoryId() != null) {
      nativeQuery.setParameter("category", productFilter.getCategoryId());
    }

    if (productFilter.getSellerId() != null) {
      nativeQuery.setParameter("seller", productFilter.getSellerId());
    }

    if (productFilter.getPriceFrom() != null && productFilter.getPriceTo() != null) {
      nativeQuery.setParameter("price_from", productFilter.getPriceFrom());
      nativeQuery.setParameter("price_to", productFilter.getPriceTo());
    } else if (productFilter.getPriceFrom() != null && productFilter.getPriceTo() == null) {
      nativeQuery.setParameter("price_from", productFilter.getPriceFrom());
    } else if (productFilter.getPriceTo() != null && productFilter.getPriceFrom() == null) {
      nativeQuery.setParameter("price_to", productFilter.getPriceTo());
    }

    List<ProductFilterResponse> result = nativeQuery.setFirstResult((int) pageable.getOffset())
        .setMaxResults(pageable.getPageSize())
        .getResultList();

    Long count = ((Number) countNativeQuery.getSingleResult()).longValue();

    return new PageImpl<>(result, pageable, count);
  }

  @Override
  public Page<ProductFilterResponse> findAllProductsByGuest(ProductFilter productFilter,
      Pageable pageable) {
    StringBuilder query = new StringBuilder(
        "SELECT p.*,\n"
            + "GROUP_CONCAT(ph.location) AS images,\n"
            + "CASE\n"
            + "    WHEN p.currency = 0 THEN p.price * 0.5\n"
            + "    ELSE p.price\n"
            + "END AS adjusted_price\n"
            + "FROM product p\n"
            + "LEFT JOIN photo ph\n"
            + "ON p.id = ph.product_id\n"
            + "WHERE p.deleted_at IS NULL"
    );

    String countQuery = "SELECT COUNT(*) FROM product p WHERE p.deleted_at IS NULL";

    if (productFilter.getTitle() != null) {
      query.append(" AND p.title LIKE :title");
    }

    if (productFilter.getCurrency() != null) {
      query.append(" AND p.currency = :currency");
    }

    if (productFilter.getCategoryId() != null) {
      query.append(" AND p.category_id = :category");
    }

    if (productFilter.getSellerId() != null) {
      query.append(" AND p.seller_id = :seller");
    }

    if (productFilter.getPriceFrom() != null && productFilter.getPriceTo() != null) {
      query.append(" AND p.price BETWEEN :price_from AND :price_to");
    } else if (productFilter.getPriceFrom() != null && productFilter.getPriceTo() == null) {
      query.append(" AND p.price >= :price_from");
    } else if (productFilter.getPriceTo() != null && productFilter.getPriceFrom() == null) {
      query.append(" AND p.price <= :price_to");
    }

    query.append(" GROUP BY p.id");

    Sort sort = pageable.getSort();
    if (sort != null && sort.isSorted()) {
      query.append(" ORDER BY");
      ArrayList<String> orderBy = new ArrayList<>();
      for (Sort.Order order : sort) {
        StringBuilder orderClause = new StringBuilder();
        if (order.getProperty().equals("price")) {
          orderClause.append(" ").append("adjusted_price").append(" ").append(order.getDirection());
        } else {
          orderClause.append(" ").append("p." + order.getProperty()).append(" ").append(order.getDirection());
        }

        orderBy.add(orderClause.toString());
      }
      query.append(String.join(",", orderBy));
    }

    Query nativeQuery = entityManager.createNativeQuery(query.toString(), "ProductProjectionGuestMapping");
    Query countNativeQuery = entityManager.createNativeQuery(countQuery.toString());

    if (productFilter.getTitle() != null) {
      nativeQuery.setParameter("title", "%" + productFilter.getTitle() + "%");
    }

    if (productFilter.getCurrency() != null) {
      nativeQuery.setParameter("currency",
          CurrencyType.valueOf(productFilter.getCurrency()).ordinal());
    }

    if (productFilter.getCategoryId() != null) {
      nativeQuery.setParameter("category", productFilter.getCategoryId());
    }

    if (productFilter.getSellerId() != null) {
      nativeQuery.setParameter("seller", productFilter.getSellerId());
    }

    if (productFilter.getPriceFrom() != null && productFilter.getPriceTo() != null) {
      nativeQuery.setParameter("price_from", productFilter.getPriceFrom());
      nativeQuery.setParameter("price_to", productFilter.getPriceTo());
    } else if (productFilter.getPriceFrom() != null && productFilter.getPriceTo() == null) {
      nativeQuery.setParameter("price_from", productFilter.getPriceFrom());
    } else if (productFilter.getPriceTo() != null && productFilter.getPriceFrom() == null) {
      nativeQuery.setParameter("price_to", productFilter.getPriceTo());
    }

    List<ProductFilterResponse> result = nativeQuery.setFirstResult((int) pageable.getOffset())
        .setMaxResults(pageable.getPageSize())
        .getResultList();

    Long count = ((Number) countNativeQuery.getSingleResult()).longValue();

    return new PageImpl<>(result, pageable, count);
  }
}
