package com.shop4e.shop.domain;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@MappedSuperclass
public abstract class Audit {

  @Id
  @GeneratedValue(generator = "uuid-hibernate-generator")
  @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID id;
  private LocalDateTime created;
  private LocalDateTime modified;

  public Audit() {
  }

  public UUID getId() {
    return id;
  }

  public Audit setId(UUID id) {
    this.id = id;
    return this;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public Audit setCreated(LocalDateTime created) {
    this.created = created;
    return this;
  }

  public LocalDateTime getModified() {
    return modified;
  }

  public Audit setModified(LocalDateTime modified) {
    this.modified = modified;
    return this;
  }

  @PrePersist
  private void beforeCreate() {
    this.created = LocalDateTime.now(ZoneOffset.UTC);
  }

  @PreUpdate
  private void beforeUpdate() {
    this.modified = LocalDateTime.now(ZoneOffset.UTC);
  }
}
