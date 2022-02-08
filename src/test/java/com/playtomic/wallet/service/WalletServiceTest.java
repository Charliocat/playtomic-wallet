package com.playtomic.wallet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.playtomic.wallet.entities.Wallet;
import com.playtomic.wallet.repository.WalletRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {


  public static final String LAST_UPDATED_BY = "player";
  public static final String PLAYER = "player";


  private WalletService walletService;

  @Mock
  private WalletRepository walletRepository;

  @Captor
  private ArgumentCaptor<Wallet> walletCaptor;

  @BeforeEach
  public void setUp() {
    walletService = new WalletServiceImpl(walletRepository);
  }

  @Test
  public void createWallet() throws WalletException {
    walletService.createWallet(PLAYER);

    verify(walletRepository).save(walletCaptor.capture());

    assertEquals(walletCaptor.getValue().getPlayerId(), PLAYER);
  }

  @Test
  public void createWalletForPlayerAlreadyHavingAWallet() throws WalletException {
    when(walletRepository.findByPlayerId(PLAYER)).thenReturn(new Wallet(PLAYER, new BigDecimal(0)));

    assertThrows(WalletException.class, () -> walletService.createWallet(PLAYER));
  }

  @Test
  public void createWalletFailed() throws WalletException {
    when(walletRepository.save(any(Wallet.class))).thenThrow(new RuntimeException());

    assertThrows(WalletException.class, () -> walletService.createWallet(PLAYER));
  }

}
