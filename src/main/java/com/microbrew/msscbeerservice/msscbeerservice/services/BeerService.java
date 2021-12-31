package com.microbrew.msscbeerservice.msscbeerservice.services;

import com.microbrew.msscbeerservice.msscbeerservice.web.model.BeerDto;
import com.microbrew.msscbeerservice.msscbeerservice.web.model.BeerPagedList;
import com.microbrew.msscbeerservice.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {
  BeerDto getById(UUID beerId, Boolean showInventoryOnHand);

  BeerDto saveNewBeer(BeerDto beerDto);

  BeerDto updateBeer(UUID beerId, BeerDto beerDto);

  BeerPagedList listBeers(
      String beerName,
      BeerStyleEnum beerStyle,
      PageRequest pageRequest,
      Boolean showInventoryOnHand);
}
