package com.in28minutes.microservices.currencyconversionservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.in28minutes.microservices.currencyconversionservice.entity.CurrencyConversion;

// O Feign com o Eureka implementam o esquema de client-side load balancer
// Logo, se houver mais de uma instância rodando o microserviço currency-exchange,
// as requisições serão balanceadas entre estas instâncias
@FeignClient(name="currency-exchange")
public interface CurrencyExchangeProxy {

  @GetMapping("/currency-exchange/from/{from}/to/{to}")
  public CurrencyConversion retrieveExchangeValue(@PathVariable String from, @PathVariable String to);
}
