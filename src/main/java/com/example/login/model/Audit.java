package com.example.login.model;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Audit {

  @CreatedBy
  @Column(name = "created_by", nullable = false, updatable = false)
  protected String createdBy;

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  protected long createdAt;

  @LastModifiedBy
  @Column(name = "modified_by", nullable = false)
  protected String updatedBy;

  @LastModifiedDate
  @Column(name = "modified_at", nullable = false)
  protected long updatedAt;
}
