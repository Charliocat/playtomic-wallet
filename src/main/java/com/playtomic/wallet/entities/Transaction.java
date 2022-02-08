package com.playtomic.wallet.entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Transaction entity.
 *
 * @author Elena Medvedeva
 */
@Entity
@Table(name = "transaction")
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "Transaction typeId must be provided")
  @Column(name = "type_id")
  @Enumerated(EnumType.STRING)
  private TransactionType type;

  @NotNull
  @Column(name = "amount", nullable = false)
  private BigDecimal amount;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "wallet_id")
  private Wallet wallet;

  @Column(name = "last_updated")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastUpdated;

  @Column(name = "last_updated_by")
  private String lastUpdatedBy;

  public Transaction() {
  }

  public Transaction(BigDecimal amount, TransactionType type, Wallet wallet) {
    this.amount = amount;
    this.type = type;
    this.wallet = wallet;
    lastUpdated = new Date();
  }

  public Transaction(BigDecimal amount, TransactionType type, Wallet wallet, String lastUpdatedBy) {
    this(amount, type, wallet);
    this.lastUpdatedBy = lastUpdatedBy;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Wallet getWallet() {
    return wallet;
  }

  public void setWallet(Wallet wallet) {
    this.wallet = wallet;
  }

  public Date getLastUpdated() {
    return lastUpdated;
  }

  public void setLastUpdated(Date lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  public String getLastUpdatedBy() {
    return lastUpdatedBy;
  }

  public void setLastUpdatedBy(String lastUpdatedBy) {
    this.lastUpdatedBy = lastUpdatedBy;
  }

  public TransactionType getType() {
    return type;
  }

  public void setType(TransactionType type) {
    this.type = type;
  }

}
