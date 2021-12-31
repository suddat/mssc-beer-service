package com.microbrew.msscbeerservice.msscbeerservice.services;

import com.microbrew.msscbeerservice.msscbeerservice.domain.Beer;
import com.microbrew.msscbeerservice.msscbeerservice.respositories.BeerRepository;
import com.microbrew.msscbeerservice.msscbeerservice.web.controller.NotFoundException;
import com.microbrew.msscbeerservice.msscbeerservice.web.mapper.BeerMapper;
import com.microbrew.msscbeerservice.msscbeerservice.web.model.BeerDto;
import com.microbrew.msscbeerservice.msscbeerservice.web.model.BeerPagedList;
import com.microbrew.msscbeerservice.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

  @Autowired private BeerRepository beerRepository;

  private BeerMapper beerMapper;

  @Override
  public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {
    if (showInventoryOnHand) {
      return beerMapper.beerToBeerDtoWithInventory(
          beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
    } else {

      return beerMapper.beerToBeerDto(
          beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
    }
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

  @Override
  public BeerPagedList listBeers(
      String beerName,
      BeerStyleEnum beerStyle,
      PageRequest pageRequest,
      Boolean showInventoryOnHand) {
    BeerPagedList beerPagedList;
    Page<Beer> beerPage;

    if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
      beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
    } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
      beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
    } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
      beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
    } else {
      beerPage = beerRepository.findAll(pageRequest);
    }

    if (showInventoryOnHand) {
      beerPagedList =
          new BeerPagedList(
              beerPage.getContent().stream()
                  .map(beerMapper::beerToBeerDtoWithInventory)
                  .collect(Collectors.toList()),
              PageRequest.of(
                  beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
              beerPage.getTotalElements());

    } else {
      beerPagedList =
          new BeerPagedList(
              beerPage.getContent().stream()
                  .map(beerMapper::beerToBeerDto)
                  .collect(Collectors.toList()),
              PageRequest.of(
                  beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
              beerPage.getTotalElements());
    }

    return beerPagedList;
  }
}
