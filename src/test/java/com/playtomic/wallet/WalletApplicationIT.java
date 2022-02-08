package com.playtomic.wallet;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.WireMockSpring;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class WalletApplicationIT {

	private static final WireMockServer wiremock = new WireMockServer(WireMockSpring.options().port(9999));

	@BeforeAll
	static void setupClass() {
		wiremock.start();
	}

	@AfterEach
	void after() {
		wiremock.resetAll();
	}

	@AfterAll
	static void clean() {
		wiremock.shutdown();
	}

	@Test
	public void emptyTest() {
		wiremock.stubFor(post(urlEqualTo("http://localhost:9999")).willReturn(aResponse()));
	}

}
