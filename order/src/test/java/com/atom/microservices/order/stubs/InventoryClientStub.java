package com.atom.microservices.order.stubs;


import lombok.extern.slf4j.Slf4j;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
@Slf4j
public class InventoryClientStub {

    public static void stubInventoryCall(String skuCode, Integer quantity) {
        log.info("Stubbing InventoryClient for skuCode: " + skuCode + " and quantity: " + quantity);
        stubFor(get(urlEqualTo("/api/v1/inventory?skuCode=" + skuCode + "&quantity=" + quantity))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("true")));
    }

    public static void stubInventoryUpdate(String skuCode, Integer quantity) {
        log.info("Stubbing InventoryClient for skuCode: " + skuCode + " and quantity: " + quantity);
        stubFor(put(urlEqualTo("/api/v1/inventory?skuCode=" + skuCode + "&quantity=" + quantity))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                ));
    }
}
