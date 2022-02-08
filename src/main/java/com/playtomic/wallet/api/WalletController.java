package com.playtomic.wallet.api;

import com.playtomic.wallet.api.dto.WalletDTO;
import com.playtomic.wallet.entities.Wallet;
import com.playtomic.wallet.service.WalletException;
import com.playtomic.wallet.service.WalletService;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("wallets")
public class WalletController {

  private Logger log = LoggerFactory.getLogger(WalletController.class);
  private final WalletService walletService;

  public WalletController(final WalletService walletService) {
    this.walletService = walletService;
  }

  @PostMapping
  public ResponseEntity<Wallet> createWallet(@Valid @RequestBody WalletDTO walletDTO) throws WalletException {
    log.info("Create wallet {}", walletDTO);
    return new ResponseEntity<>(walletService.createWallet(walletDTO.getPlayerId()), HttpStatus.CREATED);
  }

  @GetMapping("/{walletId}")
  public ResponseEntity<Wallet> getWallet(@PathVariable Long walletId) throws WalletException {
    log.info("Get wallet {}", walletId);
    return new ResponseEntity<>(walletService.getWalletById(walletId), HttpStatus.OK);
  }

  @GetMapping("/players")
  public ResponseEntity<Wallet> getWalletsByPlayerId(@RequestParam("playerId") String playerId) throws WalletException {
    log.info("Get wallet for player {}", playerId);
    return new ResponseEntity<>(walletService.findByPlayerId(playerId), HttpStatus.OK);
  }

}
