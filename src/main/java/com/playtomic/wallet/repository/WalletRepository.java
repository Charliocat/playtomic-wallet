package com.playtomic.wallet.repository;

import com.playtomic.wallet.entities.Wallet;
import com.playtomic.wallet.service.WalletException;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional(rollbackOn = WalletException.class)
public interface WalletRepository extends JpaRepository<Wallet, Long> {

  Optional<Wallet> findById(Long id);
  Wallet findByPlayerId(String playerId);

}
