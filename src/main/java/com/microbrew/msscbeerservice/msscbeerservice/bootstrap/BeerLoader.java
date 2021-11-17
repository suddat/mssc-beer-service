package com.microbrew.msscbeerservice.msscbeerservice.bootstrap;

import com.microbrew.msscbeerservice.msscbeerservice.domain.Beer;
import com.microbrew.msscbeerservice.msscbeerservice.respositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

  private final BeerRepository beerRepository;

  public BeerLoader(BeerRepository beerRepository) {
    this.beerRepository = beerRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    if (beerRepository.count() == 0) {
      beerRepository.save(
          Beer.builder()
              .beerName("The Mango bob")
              .beerStyle("ALE")
              .minOnHand(12)
              .price(new BigDecimal("29.045"))
              .quantityToBrew(200)
              .upc(800903083983L)
              .build());

      beerRepository.save(
              Beer.builder()
                      .beerName("Falafal")
                      .beerStyle("STOUT")
                      .minOnHand(12)
                      .price(new BigDecimal("39.045"))
                      .quantityToBrew(200)
                      .upc(800903083982L)
                      .build());
    }
    System.out.println("beer count : "+beerRepository.count());
  }
}
