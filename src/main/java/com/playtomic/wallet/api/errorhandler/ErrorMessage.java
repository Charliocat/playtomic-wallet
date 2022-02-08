package com.playtomic.wallet.api.errorhandler;

public class ErrorMessage {

  public static final String WALLET_NOT_FOUND = "No wallet with id %s exists in the system.";
  public static final String PLAYER_ALREADY_HAS_A_WALLET = "Player: %s already has a wallet id: %d";
  public static final String WALLET_NOT_CREATED = "Could not create a wallet";
  public static final String NOT_ENOUGH_FUNDS = "Not enough funds in wallet %d";
  public static final String UPDATE_WALLET_AMOUNT_FAILED = "Could not update wallet: %d with amount: %s";
  public static final String FAILED_CALL_TO_STRIPE = "Failure calling stripe service";
  public static final String TRANSACTION_NOT_FOUND = "Transaction %s not found";

}
