package com.playtomic.wallet.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DepositTransactionDTO {

  @NotBlank(message = "Field walletId")
  @NotNull(message = "Field walletId")
  private String creditCardNumber;

  @NotBlank(message = "Field walletId")
  @NotNull(message = "Field walletId")
  private String walletId;

  @NotBlank(message = "Field amount")
  @NotNull(message = "Field amount")
  private String amount;

  private DepositTransactionDTO() {
  }

  public DepositTransactionDTO(String creditCardNumber, String walletId, String amount) {
    this.creditCardNumber = creditCardNumber;
    this.walletId = walletId;
    this.amount = amount;
  }

  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  public String getWalletId() {
    return walletId;
  }

  public void setWalletId(String walletId) {
    this.walletId = walletId;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

}
