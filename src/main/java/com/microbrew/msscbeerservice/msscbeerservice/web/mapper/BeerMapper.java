package com.microbrew.msscbeerservice.msscbeerservice.web.mapper;

import com.microbrew.msscbeerservice.msscbeerservice.domain.Beer;
import com.microbrew.msscbeerservice.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
  Beer beerDtoToBeer(BeerDto beerDto);

  BeerDto beerToBeerDto(Beer beer);
}
