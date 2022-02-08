package com.playtomic.wallet.api;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.playtomic.wallet.entities.Wallet;
import com.playtomic.wallet.service.WalletService;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(WalletController.class)
class WalletControllerTest {

  private static final String API_WALLET = "/wallets";
  private static final String PLAYER_ID = "player1";

  @MockBean
  private WalletService service;

  @Autowired
  private MockMvc mockMvc;

  private Wallet wallet;

  @BeforeEach
  void setUp() {
    wallet = new Wallet(PLAYER_ID, new BigDecimal(0));
    wallet.setId(1L);
  }

  @Test
  void createWallet() throws Exception {
    final String request = "{" +
        "\"playerId\": \"" + PLAYER_ID + "\"}";

    final RequestBuilder postRequest = post(API_WALLET)
        .content(request)
        .contentType(MediaType.APPLICATION_JSON);

    given(service.createWallet(PLAYER_ID)).willReturn(wallet);

    final ResultActions result = mockMvc.perform(postRequest);
    result.andExpect(status().isCreated());
  }

  @Test
  void getWalletUsingWalletId() throws Exception {
    given(service.getWalletById(wallet.getId())).willReturn(wallet);

    final ResultActions result = mockMvc.perform(get(API_WALLET + "/" + wallet.getId())
                                                     .contentType(MediaType.APPLICATION_JSON));
    result.andExpect(status().isOk());
  }

  @Test
  void getWalletUsingPlayerId() throws Exception {
    given(service.findByPlayerId(PLAYER_ID)).willReturn(wallet);

    final ResultActions result = mockMvc.perform(get(API_WALLET + "/players")
                                                     .param("playerId", PLAYER_ID)
                                                     .contentType(MediaType.APPLICATION_JSON));
    result.andExpect(status().isOk());
  }

}
