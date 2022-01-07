package com.microbrew.msscbeerservice.msscbeerservice.web.controller;

import com.microbrew.msscbeerservice.msscbeerservice.services.BeerService;
import com.microbrew.msscbeerservice.msscbeerservice.web.model.BeerDto;
import com.microbrew.msscbeerservice.msscbeerservice.web.model.BeerPagedList;
import com.microbrew.msscbeerservice.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@RestController
public class BeerController {

  private static final Integer DEFAULT_PAGE_NUMBER = 0;
  private static final Integer DEFAULT_PAGE_SIZE = 25;
  private final BeerService beerService;

  @GetMapping(produces = {"application/json"}, path = "beer")
  public ResponseEntity<BeerPagedList> listBeers(
      @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
      @RequestParam(value = "pageSize", required = false) Integer pageSize,
      @RequestParam(value = "beerName", required = false) String beerName,
      @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
      @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {

    if (showInventoryOnHand == null) {
      showInventoryOnHand = false;
    }
    if (pageNumber == null || pageNumber < 0) {
      pageNumber = DEFAULT_PAGE_NUMBER;
    }
    if (pageSize == null || pageSize < 1) {
      pageSize = DEFAULT_PAGE_SIZE;
    }

    BeerPagedList beerList =
        beerService.listBeers(
            beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);

    return new ResponseEntity<>(beerList, HttpStatus.OK);
  }

  @GetMapping({"beer/{beerId}"})
  public ResponseEntity<BeerDto> getBeerById(
      @PathVariable UUID beerId,
      @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
    if (showInventoryOnHand == null) {
      showInventoryOnHand = false;
    }
    return new ResponseEntity<>(beerService.getById(beerId, showInventoryOnHand), HttpStatus.OK);
  }

  @GetMapping({"beerUpc/{upc}"})
  public ResponseEntity<BeerDto> getBeerByUpc(
          @PathVariable String upc) {
    return new ResponseEntity<>(beerService.getByUpc(upc), HttpStatus.OK);
  }

  @PostMapping(path = "beer")
  public ResponseEntity saveBeer(@Validated @RequestBody BeerDto beerDto) {
    return new ResponseEntity<>(beerService.saveNewBeer(beerDto), HttpStatus.CREATED);
  }

  @PutMapping({"beer/{beerId}"})
  public ResponseEntity updateBeerById(
      @PathVariable UUID beerId, @RequestBody @Validated BeerDto beerDto) {
    return new ResponseEntity<>(beerService.updateBeer(beerId, beerDto), HttpStatus.NO_CONTENT);
  }
}
