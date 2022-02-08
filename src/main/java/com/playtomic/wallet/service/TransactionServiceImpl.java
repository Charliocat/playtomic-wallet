package com.playtomic.wallet.service;

import com.playtomic.wallet.api.errorhandler.ErrorMessage;
import com.playtomic.wallet.entities.Transaction;
import com.playtomic.wallet.entities.TransactionType;
import com.playtomic.wallet.entities.Wallet;
import com.playtomic.wallet.repository.TransactionRepository;
import java.math.BigDecimal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

  private final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);
  private final TransactionRepository transactionRepository;
  private final StripeService stripeService;
  private final WalletService walletService;

  public TransactionServiceImpl(final TransactionRepository transactionRepository,
                                final StripeService stripeService,
                                final WalletService walletService) {
    this.transactionRepository = transactionRepository;
    this.stripeService = stripeService;
    this.walletService = walletService;
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = WalletException.class)
  public Transaction createTransaction(TransactionType transactionType, Long walletId, BigDecimal amount) throws WalletException {
    final Wallet wallet = walletService.getWalletById(walletId);
    walletService.updateWalletAmount(wallet, amount, transactionType == TransactionType.WITHDRAWAL);
    final Transaction transaction = new Transaction(amount, transactionType, wallet);
    transactionRepository.save(transaction);
    return transaction;
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = WalletException.class)
  @Override
  public Transaction depositAmount(String creditCardNumber, Long walletId, BigDecimal amount) throws WalletException {
    try {
      final Wallet wallet = walletService.getWalletById(walletId);
      stripeService.charge(creditCardNumber, amount);
      walletService.updateWalletAmount(wallet, amount, false);
      return createTransaction(TransactionType.DEPOSIT, walletId, amount);
    } catch (StripeServiceException exception) {
      log.error(ErrorMessage.FAILED_CALL_TO_STRIPE, exception);
      throw new WalletException(ErrorMessage.FAILED_CALL_TO_STRIPE, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = WalletException.class)
  @Override
  public Transaction refundAmount(String transactionId) throws WalletException {
    try {
      final Transaction transactionToRefund = transactionRepository.getById(Long.valueOf(transactionId));
      if (transactionToRefund == null)
        throw new WalletException(String.format(ErrorMessage.TRANSACTION_NOT_FOUND, transactionId), HttpStatus.NOT_FOUND.value());

      final Wallet wallet = transactionToRefund.getWallet();
      stripeService.refund(transactionId);
      walletService.updateWalletAmount(wallet, transactionToRefund.getAmount(), false);
      return createTransaction(TransactionType.REFUND, wallet.getId(), transactionToRefund.getAmount());

    } catch (StripeServiceException exception) {
      log.error(ErrorMessage.FAILED_CALL_TO_STRIPE, exception);
      throw new WalletException(ErrorMessage.FAILED_CALL_TO_STRIPE, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

  }

  @Transactional(rollbackFor = WalletException.class)
  @Override
  public List<Transaction> listTransaction(Long walletId) throws WalletException {
    final Wallet wallet = walletService.getWalletById(walletId);
    if (wallet != null) {
      return transactionRepository.findByWallet(wallet);
    }
    throw new WalletException(String.format(ErrorMessage.WALLET_NOT_FOUND, walletId.toString()), HttpStatus.BAD_REQUEST.value());
  }

}