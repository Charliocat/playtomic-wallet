package com.playtomic.wallet.service;

import com.playtomic.wallet.entities.Transaction;
import com.playtomic.wallet.entities.TransactionType;
import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

  Transaction depositAmount(String creditCardNumber, Long walletId, BigDecimal amount) throws WalletException;

  Transaction refundAmount(String transactionId) throws WalletException;

  Transaction createTransaction(TransactionType transactionType, Long walletId, BigDecimal amount) throws WalletException;

  List<Transaction> listTransaction(Long walletId) throws WalletException;

}
