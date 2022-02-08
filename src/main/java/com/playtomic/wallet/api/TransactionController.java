package com.playtomic.wallet.api;

import com.playtomic.wallet.api.dto.DepositTransactionDTO;
import com.playtomic.wallet.api.dto.TransactionDTO;
import com.playtomic.wallet.entities.Transaction;
import com.playtomic.wallet.entities.TransactionType;
import com.playtomic.wallet.service.TransactionService;
import com.playtomic.wallet.service.WalletException;
import java.math.BigDecimal;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

  private final Logger log = LoggerFactory.getLogger(TransactionController.class);

  private final TransactionService transactionService;

  public TransactionController(final TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @GetMapping("wallets/{id}/transactions")
  public ResponseEntity<List<Transaction>> getWalletTransactionsById(@PathVariable("id") int id) throws WalletException, ClassNotFoundException {
    log.info("Called TransactionController.getWalletTransactionsById with parameter walletId={}", id);
    final List<Transaction> transactionList = transactionService.listTransaction((long) id);
    return new ResponseEntity<>(transactionList, HttpStatus.OK);
  }

  @PostMapping(value = "/transactions/deposit", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Transaction> depositToWallet(@Valid @RequestBody DepositTransactionDTO depositTransactionDTO)
      throws WalletException, ClassNotFoundException {
    log.debug("Called TransactionController.createWalletTransaction");

    final Transaction transaction = transactionService.depositAmount(depositTransactionDTO.getCreditCardNumber(),
                                                                     Long.valueOf(depositTransactionDTO.getWalletId()),
                                                                     new BigDecimal(depositTransactionDTO.getAmount()));
    log.info("Deposit transaction with id=" + transaction.getId());
    return new ResponseEntity<>(transaction, HttpStatus.CREATED);
  }

  @PostMapping(value = "/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Transaction> createWalletTransaction(@Valid @RequestBody TransactionDTO transactionDTO)
      throws WalletException, ClassNotFoundException {
    log.debug("Called TransactionController.createWalletTransaction");

    final Transaction transaction = transactionService.createTransaction(TransactionType.valueOf(transactionDTO.getTransactionType()),
                                                                         Long.valueOf(transactionDTO.getWalletId()),
                                                                         new BigDecimal(transactionDTO.getAmount()));
    log.info("Transaction created with id=" + transaction.getId());
    return new ResponseEntity<>(transaction, HttpStatus.CREATED);
  }

  @PostMapping(value = "/transactions/refund", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Transaction> refundWalletTransaction(@RequestParam("transactionId") String transactionId)
      throws WalletException, ClassNotFoundException {
    log.debug("Called TransactionController.createWalletTransaction");

    final Transaction transaction = transactionService.refundAmount(transactionId);
    log.info("Refunded transaction with id=" + transaction.getId());

    return new ResponseEntity<>(transaction, HttpStatus.CREATED);
  }

}
