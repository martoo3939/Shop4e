package com.shop4e.shop.repository.util;

import com.shop4e.shop.web.response.ProductFilterResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import org.hibernate.annotations.Immutable;

@SqlResultSetMapping(
    name = "ProductProjectionMapping",
    classes = @ConstructorResult(
        targetClass = ProductFilterResponse.class,
        columns = {
            @ColumnResult(name = "id", type = String.class),
            @ColumnResult(name = "product_type", type = String.class),
            @ColumnResult(name = "created", type = LocalDateTime.class),
            @ColumnResult(name = "modified", type = LocalDateTime.class),
            @ColumnResult(name = "currency", type = Integer.class),
            @ColumnResult(name = "description", type = String.class),
            @ColumnResult(name = "price", type = BigDecimal.class),
            @ColumnResult(name = "title", type = String.class),
            @ColumnResult(name = "category_id", type = String.class),
            @ColumnResult(name = "seller_id", type = String.class),
            @ColumnResult(name = "isbn", type = String.class),
            @ColumnResult(name = "author", type = String.class),
            @ColumnResult(name = "pages", type = String.class),
            @ColumnResult(name = "published_at", type = LocalDate.class),
            @ColumnResult(name = "brand", type = String.class),
            @ColumnResult(name = "hard_drive", type = String.class),
            @ColumnResult(name = "memory", type = String.class),
            @ColumnResult(name = "processor", type = String.class),
            @ColumnResult(name = "type", type = Integer.class),
            @ColumnResult(name = "video_card", type = String.class),
            @ColumnResult(name = "book_category_id", type = String.class),
            @ColumnResult(name = "favourite", type = Boolean.class),
            @ColumnResult(name = "images", type = String.class)
        }
    )
)
@Entity
@Immutable
public class ProductProjectionMapping {

  @Id
  private String id;
  private String product_type;
  private LocalDateTime created;
  private LocalDateTime modified;
  private Integer currency;
  private String description;
  private BigDecimal price;
  private String title;
  private String categoryId;
  private String seller_id;
  private String isbn;
  private String author;
  private String pages;
  private Date published_at;
  private String brand;
  private String hard_drive;
  private String memory;
  private String processor;
  private Integer type;
  private String video_card;
  private String book_category_id;
  private Boolean favourite;
  @Column(columnDefinition = "TEXT")
  private String images;
}
