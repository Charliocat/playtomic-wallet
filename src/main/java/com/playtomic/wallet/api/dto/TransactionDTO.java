package com.playtomic.wallet.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TransactionDTO {

  @NotBlank(message = "Field walletId")
  @NotNull(message = "Field walletId")
  private String walletId;

  @NotBlank(message = "Field transactionTypeId")
  @NotNull(message = "Field transactionTypeId")
  private String transactionType;

  @NotBlank(message = "Field amount")
  @NotNull(message = "Field amount")
  private String amount;

  private TransactionDTO(){}

  public TransactionDTO(String walletId, String transactionType, String amount) {
    this.walletId = walletId;
    this.transactionType = transactionType;
    this.amount = amount;
  }

  public String getWalletId() {
    return walletId;
  }

  public void setWalletId(String walletId) {
    this.walletId = walletId;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

}
