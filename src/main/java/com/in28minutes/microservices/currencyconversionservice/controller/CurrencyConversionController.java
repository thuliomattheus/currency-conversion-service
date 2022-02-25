package com.in28minutes.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.in28minutes.microservices.currencyconversionservice.entity.CurrencyConversion;
import com.in28minutes.microservices.currencyconversionservice.proxy.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionController {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private CurrencyExchangeProxy exchangeProxy;

  @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable int quantity) {

    Map<String, String> uriVariables = new HashMap<>();
    uriVariables.put("from", from);
    uriVariables.put("to", to);

    ResponseEntity<CurrencyConversion> response = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
        CurrencyConversion.class,
        uriVariables);

    CurrencyConversion currencyConversion = response.getBody();

    if(currencyConversion != null) {
      currencyConversion.setQuantity(quantity);
      currencyConversion.setTotalCalculatedAmount(currencyConversion.getConversionMultiple().multiply(BigDecimal.valueOf(quantity)));
      currencyConversion.setEnvironment(currencyConversion.getEnvironment() + " rest template");
    }

    return currencyConversion;
  }

  @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to, @PathVariable int quantity) {

    CurrencyConversion currencyConversion = exchangeProxy.retrieveExchangeValue(from, to);

    if(currencyConversion != null) {
      currencyConversion.setQuantity(quantity);
      currencyConversion.setTotalCalculatedAmount(currencyConversion.getConversionMultiple().multiply(BigDecimal.valueOf(quantity)));
      currencyConversion.setEnvironment(currencyConversion.getEnvironment() + " feign");
    }

    return currencyConversion;
  }

}
