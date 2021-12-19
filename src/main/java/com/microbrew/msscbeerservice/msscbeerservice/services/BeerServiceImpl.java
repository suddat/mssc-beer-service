package com.microbrew.msscbeerservice.msscbeerservice.services;

import com.microbrew.msscbeerservice.msscbeerservice.domain.Beer;
import com.microbrew.msscbeerservice.msscbeerservice.respositories.BeerRepository;
import com.microbrew.msscbeerservice.msscbeerservice.web.controller.NotFoundException;
import com.microbrew.msscbeerservice.msscbeerservice.web.mapper.BeerMapper;
import com.microbrew.msscbeerservice.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

  @Autowired private BeerRepository beerRepository;

  private BeerMapper beerMapper;

  @Override
  public BeerDto getById(UUID beerId) {
    return beerMapper.beerToBeerDto(
        beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
  }

  @Override
  public BeerDto saveNewBeer(BeerDto beerDto) {
    return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
  }

  @Override
  public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
    Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

    beer.setBeerName(beerDto.getBeerName());
    beer.setBeerStyle(beerDto.getBeerStyle().name());
    beer.setPrice(beerDto.getPrice());
    beer.setUpc(beerDto.getUpc());

    return beerMapper.beerToBeerDto(beerRepository.save(beer));
  }
}
