package com.playtomic.wallet.service;

import com.playtomic.wallet.entities.Wallet;
import java.math.BigDecimal;

public interface WalletService {

  Wallet createWallet(String playerId) throws WalletException;

  Wallet getWalletById(Long id) throws WalletException;

  void updateWalletAmount(Wallet wallet, BigDecimal amount, Boolean isWithdrawal) throws WalletException;

  Wallet findByPlayerId(String playerId) throws WalletException;

}
