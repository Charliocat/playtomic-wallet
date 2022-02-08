package com.playtomic.wallet.repository;

import com.playtomic.wallet.entities.Transaction;
import com.playtomic.wallet.entities.Wallet;
import com.playtomic.wallet.service.WalletException;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional(rollbackOn = WalletException.class)
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  Transaction getById(Long id);
  List<Transaction> findByWallet(Wallet wallet);

}
