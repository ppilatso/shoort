package com.bafana.shorturl.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "link", indexes = {
        @Index(name = "idx_short_code", columnList = "short_code")
})
@Getter
@Setter
public class Link {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "short_code", unique = true)
  private String shortCode;

  @Column(name = "original_url", nullable = false, columnDefinition = "TEXT")
  private String originalUrl;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "click_count", nullable = true)
  private Integer clickCount;

  public Link() {}

  public Link(String originalUrl) {
    this.originalUrl = originalUrl;
    this.createdAt = LocalDateTime.now();
    this.clickCount = 0;
  }

  public void incrementClickCount() {
      this.clickCount++;
  }

  @PrePersist
  public void prePersist() {
    if (this.createdAt == null) {
      this.createdAt = LocalDateTime.now();
    }
  }
}