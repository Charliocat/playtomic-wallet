package com.playtomic.wallet.service;

import com.playtomic.wallet.api.errorhandler.ErrorMessage;
import com.playtomic.wallet.entities.Wallet;
import com.playtomic.wallet.repository.WalletRepository;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletServiceImpl implements WalletService {

  private final WalletRepository walletRepository;

  public WalletServiceImpl(@Autowired final WalletRepository walletRepository) {
    this.walletRepository = walletRepository;
  }

  @Override
  public Wallet createWallet(String playerId) throws WalletException {
    final Wallet playerWallet = walletRepository.findByPlayerId(playerId);
    if (playerWallet != null)
      throw new WalletException(String.format(ErrorMessage.PLAYER_ALREADY_HAS_A_WALLET, playerWallet.getPlayerId(), playerWallet.getId()),
                                HttpStatus.BAD_REQUEST.value());

    try {
      return walletRepository.save(new Wallet(playerId, new BigDecimal(0)));
    } catch (Exception e) {
      throw new WalletException(ErrorMessage.WALLET_NOT_CREATED, HttpStatus.BAD_REQUEST.value());
    }
  }

  @Override
  public Wallet getWalletById(Long id) throws WalletException {
    Optional<Wallet> wallet = walletRepository.findById(id);
    if (wallet.isEmpty())
      throw new WalletException(String.format(ErrorMessage.WALLET_NOT_FOUND, id), HttpStatus.NOT_FOUND.value());
    return wallet.get();
  }

  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = WalletException.class)
  @Override
  public void updateWalletAmount(Wallet wallet, BigDecimal amount, Boolean isWithdrawal) throws WalletException {
    if (isWithdrawal && wallet.getBalance().compareTo(amount.abs()) < 0)
      throw new WalletException(String.format(ErrorMessage.NOT_ENOUGH_FUNDS, wallet.getId()), HttpStatus.BAD_REQUEST.value());
    try {
      //update wallet
      wallet.setBalance(wallet.getBalance().add(isWithdrawal ? amount.abs().negate() : amount.abs()));
      wallet.setLastUpdated(new Date());
    } catch (Exception e) {
      throw new WalletException(String.format(ErrorMessage.UPDATE_WALLET_AMOUNT_FAILED, wallet.getId(), amount.toString()),
                                HttpStatus.BAD_REQUEST.value());
    }
  }

  @Transactional(rollbackFor = WalletException.class)
  @Override
  public Wallet findByPlayerId(String playerId) throws WalletException {
    return walletRepository.findByPlayerId(playerId);
  }

}
