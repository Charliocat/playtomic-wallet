package com.playtomic.wallet.api.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class WalletDTO {

  @NotNull
  @NotEmpty
  private String playerId;

  private WalletDTO() {
  }

  public WalletDTO(String playerId) {
    this.playerId = playerId;
  }

  public String getPlayerId() {
    return playerId;
  }

  public void setPlayerId(String playerId) {
    this.playerId = playerId;
  }

}
