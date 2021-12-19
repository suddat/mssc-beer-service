package com.microbrew.msscbeerservice.msscbeerservice.web.controller;

import com.microbrew.msscbeerservice.msscbeerservice.services.BeerService;
import com.microbrew.msscbeerservice.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/beer")
@RequiredArgsConstructor
@RestController
public class BeerController {

  private final BeerService beerService;

  @GetMapping({"/{beerId}"})
  public ResponseEntity<BeerDto> getBeerById(@PathVariable UUID beerId) {
    return new ResponseEntity<>(beerService.getById(beerId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity saveBeer(@Validated @RequestBody BeerDto beerDto) {
    return new ResponseEntity<>(beerService.saveNewBeer(beerDto), HttpStatus.CREATED);
  }

  @PutMapping({"/{beerId}"})
  public ResponseEntity updateBeerById(
      @PathVariable UUID beerId, @RequestBody @Validated BeerDto beerDto) {
    return new ResponseEntity<>(beerService.updateBeer(beerId, beerDto), HttpStatus.NO_CONTENT);
  }
}
